package com.xyj.modules.pay.vo;


@Deprecated
public class UnifiedOrderResultVo extends BaseVo{
	private String codeUrl;
	private String payCode;
	private String sign;
	private String outTradeNo;
	private String outChannelNo;
	private String amount;
	private String transTime;
	private String resultCode;
	private String payWayCode;
	private String thirdTradeNo;
	private String timeEnd;
	/** 订单确认码 */
	private String orderPayCode;
	/** 实际支付金额 */
	private String realAmount;


	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getOutChannelNo() {
		return outChannelNo;
	}
	public void setOutChannelNo(String outChannelNo) {
		this.outChannelNo = outChannelNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getPayWayCode() {
		return payWayCode;
	}
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}
	public String getThirdTradeNo() {
		return thirdTradeNo;
	}
	public void setThirdTradeNo(String thirdTradeNo) {
		this.thirdTradeNo = thirdTradeNo;
	}

	public String toString() {
		return "UnifiedOrderResultVo{" +
				"codeUrl='" + codeUrl + '\'' +
				", payCode='" + payCode + '\'' +
				", sign='" + sign + '\'' +
				", outTradeNo='" + outTradeNo + '\'' +
				", outChannelNo='" + outChannelNo + '\'' +
				", amount='" + amount + '\'' +
				", transTime='" + transTime + '\'' +
				", resultCode='" + resultCode + '\'' +
				", payWayCode='" + payWayCode + '\'' +
				", thirdTradeNo='" + thirdTradeNo + '\'' +
				'}';
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getOrderPayCode() {
		return orderPayCode;
	}
	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
}
