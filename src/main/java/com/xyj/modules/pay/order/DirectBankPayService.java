package com.xyj.modules.pay.order;

import com.xyj.modules.pay.entity.PayOrderToday;
import com.xyj.modules.pay.vo.PayParamsVo;
import com.xyj.modules.pay.vo.UnifiedOrderResultVo;
import com.xyj.modules.pay.vo.UnifiedOrderVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 支付抽象类
 * @author zhouguangming
 * @date 7/2/19
 * @since
 */
@Component
public abstract class DirectBankPayService implements PayService{

    @Override
    public UnifiedOrderResultVo unifiedOrder(UnifiedOrderVo unifiedOrderVo) throws Exception {
        BigDecimal realAmount = queryPayRealAmount(unifiedOrderVo);
        // 检查支付金额限额
        //checkPayAmountLimit(unifiedOrderVo.getPayTypeCode(), realAmount);
        PayParamsVo payParamsVo = getPayParamsVo(unifiedOrderVo.getMerchantNo(), unifiedOrderVo.getPayTypeCode());
        //初始化订单
        PayOrderToday payOrderToday= initOrder(unifiedOrderVo,payParamsVo);

        //订单-机具关系表
//        if(StringUtils.isNotBlank(unifiedOrderVo.getSnNo())){
//            initOrderManchineRelation(payOrderToday, unifiedOrderVo.getSnNo());
//        }
        //统一支付下单
        UnifiedOrderResultVo unifiedOrderResultVo = doUnifiedOrder(unifiedOrderVo,payParamsVo);
        // 返回结果增加支付确认码数据
        unifiedOrderResultVo.setOrderPayCode(unifiedOrderVo.getOrderPayCode());
        unifiedOrderResultVo.setRealAmount(realAmount.setScale(2).toString());
        //保存订单日志
       // saveOrderLog(unifiedOrderVo,payParamsVo);
        return unifiedOrderResultVo;
    }

    /**
     * 下单
     * @param unifiedOrderVo
     * @return
     */
    protected abstract UnifiedOrderResultVo doUnifiedOrder(UnifiedOrderVo unifiedOrderVo, PayParamsVo payParamsVo) throws Exception;

    /**
     * 获取用户实际支付金额
     * 为了不改动和影响{@link #initOrder}(方法里有计算该值)方法，所以这里写了一个新的方法获取用户实际支付金额
     * @param unifiedOrderVo    统一下单VO
     * @return  用户实际支付金额
     */
    private BigDecimal queryPayRealAmount(UnifiedOrderVo unifiedOrderVo) {
        BigDecimal orderAmount = unifiedOrderVo.getAmount();
        BigDecimal realAmount = unifiedOrderVo.getRealAmount();
        //商家红包金额
        BigDecimal merPocketAmt = unifiedOrderVo.getMerPocketAmt();
        if (merPocketAmt == null) {
            merPocketAmt = BigDecimal.ZERO;
        }
        //用户使用零钱金额
        BigDecimal changeAmt = unifiedOrderVo.getChangeAmt();
        if (changeAmt == null) {
            changeAmt = BigDecimal.ZERO;
        }
        if (realAmount == null) {
            realAmount = orderAmount.subtract(merPocketAmt).subtract(changeAmt);
        }
        return realAmount;
    }

    /**
     * 初始化订单
     * @return
     * @author zhouguangming
     * @date 7/3/19
     * @since V1.3
     */
    @Override
    public PayOrderToday initOrder(UnifiedOrderVo unifiedOrderVo, PayParamsVo payParamsVo) throws Exception {
        return null;
    }

    /**
     * 获取支付参数
     * @param merchantId    商户编号
     * @param payTypeCode   支付类型编码
     * @return
     */
    @Override
    public PayParamsVo getPayParamsVo(String merchantId, String payTypeCode) {
        return null;
    }
}

