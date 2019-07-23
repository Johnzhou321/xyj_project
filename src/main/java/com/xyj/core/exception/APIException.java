package com.xyj.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIException extends BizException{
    private static final Logger logger = LoggerFactory.getLogger(APIException.class);


    /**
     * 无此接口权限
     */
    public static final APIException API_NOAUTH = new APIException(
            "noauth", "无此接口权限");

    public static final APIException API_PAY_USERPAYING = new APIException(
            "userPaying", "等待付款");

    public static final APIException API_PAY_NOTENOUGH = new APIException(
            "notEnough", "余额不足");

    public static final APIException API_PAY_ORDERPAID = new APIException(
            "orderPaid", "商户订单已支付");

    public static final APIException API_PAY_ORDERCLOSED = new APIException(
            "orderClosed", "订单已关闭");

    public static final APIException API_PAY_MCHINVALID = new APIException(
            "mchInvalid", "非法商户");

    public static final APIException API_PAY_SYSTEMERROR = new APIException(
            "systemError", "系统错误");

    public static final APIException API_PAY_SYSTEMTIMEOUT = new APIException(
            "systemTimeout", "系统超时");

    public static final APIException API_PAY_PARAMERROR = new APIException(
            "paramError", "参数无效");

    public static final APIException API_PAY_SIGNERROR = new APIException(
            "signError", "签名错误");

    public static final APIException API_PAY_OUTTRADENOUSED = new APIException(
            "outTradeNoUsed", "商户订单号重复");

    public static final APIException API_PAY_OUTREFUNDNOUSED = new APIException(
            "outRefundNoUsed", "商户退款单号重复");

    public static final APIException API_PAY_POSTDATAEMPTY = new APIException(
            "postDataEmpty", "post数据为空");

    public static final APIException API_PAY_NOTUTF8 = new APIException(
            "notUtf8", "编码格式错误");

    public static final APIException API_PAY_LACKPARAM = new APIException(
            "lackParam", "缺少参数");

    public static final APIException API_PAY_PARAMETERTYPEERROR = new APIException(
            "parameterTypeError", "参数类型错误");

    public static final APIException API_APPROVE_REPEAT = new APIException(
            "repeatApproveError", "商户重复审核！");

    public static final APIException API_PAY_ERRORCODE = new APIException(
            "errCode", "");
    
    public static final APIException REQUEST_BLOCKED = new APIException(
            "paramError", "当前商户需补齐相关资料后，才可进行相应的支付交易");




    public APIException(String code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public APIException() {
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message) {
        super(message);
    }

    public APIException print() {
        logger.info("==>APIException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }

    @Override
    public APIException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
