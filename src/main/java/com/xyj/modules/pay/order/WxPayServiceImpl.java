package com.xyj.modules.pay.order;

import com.alibaba.fastjson.JSON;
import com.xyj.conf.SysConfig;
import com.xyj.core.exception.APIException;
import com.xyj.enums.PayTypeEnum;
import com.xyj.enums.PayWayEnum;
import com.xyj.modules.pay.vo.PayParamsVo;
import com.xyj.modules.pay.vo.UnifiedOrderResultVo;
import com.xyj.modules.pay.vo.UnifiedOrderVo;
import com.xyj.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微心支付
 * @author zhouguangming
 * @date 7/3/19
 * @since
 */
public class WxPayServiceImpl extends DirectBankPayService{

    private static final Log log = LogFactory.getLog(WxPayServiceImpl.class);

    @Override
    @Transactional
    public UnifiedOrderResultVo doUnifiedOrder(UnifiedOrderVo unifiedOrderVo,PayParamsVo payParamsVo) throws Exception {
        UnifiedOrderResultVo unifiedOrderResultVo;
        //初始化支付参数
        if(PayTypeEnum.WX_PUB.getCode().equalsIgnoreCase(unifiedOrderVo.getPayTypeCode())){//微信公众号支付（扫静态二维码）

            unifiedOrderResultVo =  wxPubUnifiedOrder(unifiedOrderVo,payParamsVo);
        }else{
            throw APIException.API_PAY_ERRORCODE.setMsg("暂不支持"+unifiedOrderVo.getPayTypeCode()+"支付方式");
        }
        return unifiedOrderResultVo;
    }

    /**
     *	微信公众号下单（扫静态二维码）
     * @param unifiedOrderVo
     * @return
     */
    private UnifiedOrderResultVo wxPubUnifiedOrder(UnifiedOrderVo unifiedOrderVo,PayParamsVo payParamsVo) throws Exception{
        log.info("wxPub微信公众号支付统一支付下单传入参数为："+System.getProperty("line.separator")+unifiedOrderVo.toString());
        //组织请求参数
        SortedMap<String, String> paramsMap = new TreeMap<String, String>();
        //必填项
        paramsMap.put("appid", payParamsVo.getAppid());
        paramsMap.put("mch_id", payParamsVo.getMchid());
        paramsMap.put("sub_mch_id", payParamsVo.getSubMchid());
        paramsMap.put("nonce_str", WxPayUtil.createNoncestr(16));
        paramsMap.put("body", unifiedOrderVo.getBody());
        paramsMap.put("out_trade_no", unifiedOrderVo.getOrderId());//此处为统一支付平台的订单编号
        paramsMap.put("total_fee", String.format("%.0f", unifiedOrderVo.getRealAmount().doubleValue() * 100));// 金额为以分为单位的正整数
        paramsMap.put("spbill_create_ip", unifiedOrderVo.getSpbillCreateIp());// 用户端ip
        paramsMap.put("notify_url", SysConfig.getConfig("wxNotifyUrl"));//此处为第三方支付机构回调平台的接口地址
        paramsMap.put("trade_type", payParamsVo.getTradeType());
        //选填项
        if(StringUtils.isNotEmpty(unifiedOrderVo.getSubAppid())&&StringUtils.isNotEmpty(unifiedOrderVo.getSubOpenid())){
            paramsMap.put("sub_appid", unifiedOrderVo.getSubAppid());
            paramsMap.put("sub_openid", unifiedOrderVo.getSubOpenid());
        }
        if(StringUtils.isNotEmpty(unifiedOrderVo.getTerminalType())){
            paramsMap.put("device_info", unifiedOrderVo.getTerminalType());
        }
        paramsMap.put("sign_type",  WxPayConstants.MD5);
        if(StringUtils.isNotEmpty(unifiedOrderVo.getBody())){
            paramsMap.put("detail", unifiedOrderVo.getBody());
        }
        if(StringUtils.isNotEmpty(unifiedOrderVo.getDescription())){
            paramsMap.put("attach", unifiedOrderVo.getDescription());
        }
        if(StringUtils.isNotEmpty(unifiedOrderVo.getCurrency())){
            paramsMap.put("fee_type", unifiedOrderVo.getCurrency());
        }
        if(StringUtils.isNotEmpty(unifiedOrderVo.getTimePaid())){
            paramsMap.put("time_start", unifiedOrderVo.getTimePaid());
        }
        if(StringUtils.isNotEmpty(unifiedOrderVo.getTimeExpire())
                && !"null".equalsIgnoreCase(unifiedOrderVo.getTimeExpire())){
            paramsMap.put("time_expire", unifiedOrderVo.getTimeExpire());
        }else{
            //默认有效期为30分钟
            paramsMap.put("time_expire", DateUtils.formatDate(DateUtils.addMinutes(new Date(), 1), "yyyyMMddHHmmss"));
        }

        if(StringUtils.isNotEmpty(unifiedOrderVo.getLimitPay())){
            paramsMap.put("limit_pay", unifiedOrderVo.getLimitPay());
        }
        //创建签名
        String sign = WxPayUtil.generateSignature(paramsMap, payParamsVo.getPrivateSecret());
        paramsMap.put("sign", sign);
        //组织请求报文
        String requestStr = WxPayUtil.mapToXml(paramsMap);
        unifiedOrderVo.setRequestStr(requestStr);
        log.info("wxPub微信公众号支付统一支付下单请求报文为："+System.getProperty("line.separator")+requestStr);

        //发送统一支付下单请求
        String responseStr = HttpClientUtils.post(WxPayConstants.UNIFIEDORDER_URL, requestStr);
        unifiedOrderVo.setResponseStr(responseStr);
        log.info("wxPub微信公众号支付统一支付下单返回报文为："+System.getProperty("line.separator")+responseStr);
        Map<String, String> returnMap = WxPayUtil.xmlToMap(responseStr);
        UnifiedOrderResultVo unifiedOrderResultVo = new UnifiedOrderResultVo();

        if("SUCCESS".equals(returnMap.get("return_code"))){
            if(WxPayUtil.isSignatureValid(WxPayUtil.mapToSortMap(returnMap), payParamsVo.getPrivateSecret())){
                if("SUCCESS".equals(returnMap.get("result_code"))){
                    unifiedOrderResultVo.setOutChannelNo(unifiedOrderVo.getOrderId());
                    unifiedOrderResultVo.setOutTradeNo(unifiedOrderVo.getOutTradeNo());
                    unifiedOrderResultVo.setTransTime(DateUtils.getReqDateyyyyMMddHHmmss(new Date()));
                    unifiedOrderResultVo.setAmount(unifiedOrderVo.getAmount().setScale(2).toString());

                    Map<String,String> payCodeMap = new TreeMap<String,String>();
                    payCodeMap.put("appId", returnMap.get("appid"));
                    payCodeMap.put("timeStamp", "" + System.currentTimeMillis());
                    payCodeMap.put("nonceStr", WxPayUtil.createNoncestr(16));
                    payCodeMap.put("package", "prepay_id="+returnMap.get("prepay_id"));
                    payCodeMap.put("signType", WxPayConstants.MD5);
                    payCodeMap.put("paySign", WxPayUtil.generateSignature(payCodeMap, payParamsVo.getPrivateSecret()));
                    unifiedOrderResultVo.setPayCode(JSON.toJSONString(payCodeMap));
                    unifiedOrderResultVo.setThirdTradeNo(returnMap.get("prepay_id"));
                    unifiedOrderResultVo.setPayWayCode(PayWayEnum.WEIXIN.getCode());
                    unifiedOrderResultVo.setResultCode("SUCCESS");

                    //设置返回参数签名
                    unifiedOrderResultVo.setSign(WxPayUtil.generateSignature(BeanUtils.objectToSortedMap(unifiedOrderResultVo)  , payParamsVo.getAgentSecret()));
                    return unifiedOrderResultVo;

                }else{//抛出业务错误异常
                    log.info(String.format("订单号为【%s】的wxPub微信公众号支付统一支付下单失败，原因为：【%s】",unifiedOrderVo.getOutTradeNo(),returnMap.get("err_code")));
                    throw getPayErrorException(returnMap.get("err_code"));
                }

            }else{//抛出签名错误异常
                log.info(String.format("订单号为【%s】wxPub微信公众号支付统一支付下单失败，原因为：微信返回验签失败",unifiedOrderVo.getOutTradeNo()));
                throw APIException.API_PAY_SIGNERROR;
            }

        }else{
            log.info(String.format("订单号为【%s】wxPub微信公众号支付统一支付下单微信接口返回失败,原因为【%s】",unifiedOrderVo.getOutTradeNo(),returnMap.get("return_msg")));
            throw APIException.API_PAY_PARAMERROR.setMsg(returnMap.get("return_msg"));
        }

    }

    /**
     * 根据微信支付返回的错误码映射平台业务异常
     * @param wxPayErrorCode
     * @return
     */
    private APIException getPayErrorException(String wxPayErrorCode) {
        APIException apiException = null;
        if ("USERPAYING".equals(wxPayErrorCode)) {//等待用户输入密码
            apiException = APIException.API_PAY_USERPAYING;
        } else if ("NOAUTH".equals(wxPayErrorCode)) {//没有接口权限
            apiException = APIException.API_NOAUTH;
        } else if ("AUTH_CODE_INVALID".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR.setMsg("付款码无效，请重新扫码");
        } else if ("NOTENOUGH".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_NOTENOUGH;
        } else if ("ORDERPAID".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_ORDERPAID;
        } else if ("ORDERCLOSED".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_ORDERCLOSED;
        } else if ("SYSTEMERROR".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_SYSTEMERROR;
        } else if ("APPID_NOT_EXIST".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR;
        } else if ("MCHID_NOT_EXIST".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR;
        } else if ("APPID_MCHID_NOT_MATCH".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR;
        } else if ("LACK_PARAMS".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_LACKPARAM;
        } else if ("OUT_TRADE_NO_USED".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_OUTTRADENOUSED;
        } else if ("XML_FORMAT_ERROR".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR;
        } else if ("REQUIRE_POST_METHOD".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_PARAMERROR;
        } else if ("POST_DATA_EMPTY".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_POSTDATAEMPTY;
        } else if ("NOT_UTF8".equals(wxPayErrorCode)) {
            apiException = APIException.API_PAY_NOTUTF8;
        } else {
            apiException = APIException.API_PAY_PARAMERROR;
        }
        return apiException;
    }
}
