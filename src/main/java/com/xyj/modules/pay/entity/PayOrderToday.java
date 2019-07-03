package com.xyj.modules.pay.entity;


import java.math.BigDecimal;
import java.util.Date;

public class PayOrderToday extends BaseEntity{

    private String merchantId;

    private String merchantName;

    private String bankPaymentServiceId;

    private Date orderTime;

    private Date paySuccessTime;

    private String payStatus;

    private BigDecimal orderAmount;

    private String merchantPayProductItemRelationId;

    private String payWayCode;

    private String payTypeCode;

    private String payPipeCode;

    private String settleCycleCode;

    private String userPaymentType;

    private BigDecimal payPipeCost;

    private BigDecimal bankProfit;

    private BigDecimal agentProfit;

    private BigDecimal merchantTradeFee;

    private BigDecimal settleAmount;

    private String payChannelUserid;

    private String transactionId;

    private String merOutTradeNo;

    private String mchid;

    private String verifyStatus;

    private String verifyResult;

    private Date verifyTime;

    private String reconStatus;

    private Date reconTime;

    private String settleStatus;

    private Date settleTime;

    private String notifyStatus;

    private Date notifyTime;

    private String notifyUrl;

    private String merLoginId;
    
    private String payCode;
    
    private BigDecimal realAmount;

    private BigDecimal realIncome;

    private BigDecimal merPocketAmt;

    private BigDecimal changeAmt;

    private String userPocketId;

    private String remark;
    private String ext1;
    private String ext2;
    private String ext3;

    private BigDecimal compensationFee;//平台补偿手续费
    private BigDecimal discountAmt;//商家折扣金额
    

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getBankPaymentServiceId() {
        return bankPaymentServiceId;
    }

    public void setBankPaymentServiceId(String bankPaymentServiceId) {
        this.bankPaymentServiceId = bankPaymentServiceId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMerchantPayProductItemRelationId() {
        return merchantPayProductItemRelationId;
    }

    public void setMerchantPayProductItemRelationId(String merchantPayProductItemRelationId) {
        this.merchantPayProductItemRelationId = merchantPayProductItemRelationId == null ? null : merchantPayProductItemRelationId.trim();
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
    }

    public String getPayPipeCode() {
        return payPipeCode;
    }

    public void setPayPipeCode(String payPipeCode) {
        this.payPipeCode = payPipeCode == null ? null : payPipeCode.trim();
    }

    public String getSettleCycleCode() {
        return settleCycleCode;
    }

    public void setSettleCycleCode(String settleCycleCode) {
        this.settleCycleCode = settleCycleCode == null ? null : settleCycleCode.trim();
    }

    public String getUserPaymentType() {
        return userPaymentType;
    }

    public void setUserPaymentType(String userPaymentType) {
        this.userPaymentType = userPaymentType == null ? null : userPaymentType.trim();
    }

    public BigDecimal getPayPipeCost() {
        return payPipeCost;
    }

    public void setPayPipeCost(BigDecimal payPipeCost) {
        this.payPipeCost = payPipeCost;
    }

    public BigDecimal getBankProfit() {
        return bankProfit;
    }

    public void setBankProfit(BigDecimal bankProfit) {
        this.bankProfit = bankProfit;
    }

    public BigDecimal getAgentProfit() {
        return agentProfit;
    }

    public void setAgentProfit(BigDecimal agentProfit) {
        this.agentProfit = agentProfit;
    }

    public BigDecimal getMerchantTradeFee() {
        return merchantTradeFee;
    }

    public void setMerchantTradeFee(BigDecimal merchantTradeFee) {
        this.merchantTradeFee = merchantTradeFee;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getPayChannelUserid() {
        return payChannelUserid;
    }

    public void setPayChannelUserid(String payChannelUserid) {
        this.payChannelUserid = payChannelUserid == null ? null : payChannelUserid.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getMerOutTradeNo() {
        return merOutTradeNo;
    }

    public void setMerOutTradeNo(String merOutTradeNo) {
        this.merOutTradeNo = merOutTradeNo == null ? null : merOutTradeNo.trim();
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid == null ? null : mchid.trim();
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus == null ? null : verifyStatus.trim();
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult == null ? null : verifyResult.trim();
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getReconStatus() {
        return reconStatus;
    }

    public void setReconStatus(String reconStatus) {
        this.reconStatus = reconStatus == null ? null : reconStatus.trim();
    }

    public Date getReconTime() {
        return reconTime;
    }

    public void setReconTime(Date reconTime) {
        this.reconTime = reconTime;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public String getMerLoginId() {
        return merLoginId;
    }

    public void setMerLoginId(String merLoginId) {
        this.merLoginId = merLoginId;
    }

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}
		

    public BigDecimal getRealIncome() {
        return realIncome;
    }

    public void setRealIncome(BigDecimal realIncome) {
        this.realIncome = realIncome;
    }

    public BigDecimal getMerPocketAmt() {
        return merPocketAmt;
    }

    public void setMerPocketAmt(BigDecimal merPocketAmt) {
        this.merPocketAmt = merPocketAmt;
    }

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
    }

    public String getUserPocketId() {
        return userPocketId;
    }

    public void setUserPocketId(String userPocketId) {
        this.userPocketId = userPocketId;
    }

    public BigDecimal getCompensationFee() {
        return compensationFee;
    }

    public void setCompensationFee(BigDecimal compensationFee) {
        this.compensationFee = compensationFee;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
}