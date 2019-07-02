package com.xyj.modules.pay.vo;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnifiedOrderVo extends BaseVo{
	private String version;
	private String agentNo = "1";
	private String payTypeCode;
	private String terminalType;
	private String cashierNo;
	private String merchantNo;
	private String sign;
	private String body;
	private String outTradeNo;
	private BigDecimal amount;
	private String description;
	private String currency;
	private String timePaid;
	private String timeExpire;
	private String subject;
	private String limitPay;
	private String callbackUrl;
	private String notifyUrl;
	private String isRaw;
	private String subAppid;
	private String subOpenid;
	private String wxSubMchId;
	private String mobileAppId;
	private String authCode;
	private String accessType;
	private String orderId;
	private String systemCode;

	private BigDecimal orderAmount;
	private BigDecimal realAmount;
	private BigDecimal realIncome;
	private BigDecimal merPocketAmt;
	private BigDecimal changeAmt;

	private String userPocketId;
	private BigDecimal discountAmt;

    private String merLoginId;

    /** 口袋零钱分配的收银员编号,与merLoginId字段一样，新统一下单接收该参数，所以这里增加该字段接收 */
    private String merchantLoginNo;

	private String spbillCreateIp;
	private String requestStr;
	private String responseStr;
	private String remark;
	
	private String systemNum;

	private String paychannelUserId;
	/** 订单确认码 */
	private String orderPayCode;
	
	private String snNo;
	
    private String downstreamSystemCode;

    /** 订单确认码 */
    private BigDecimal discountAmount;
    /** 订单确认码 */
    private BigDecimal userPocketAmount;
	/** 消费者红包编号 */
	private String userPocketNo;

	//兼容新字段
	public void setUserPocketNo(String userPocketNo){
		this.userPocketNo = userPocketNo;
		this.userPocketId = userPocketNo;
	}

    public void setDiscountAmount(BigDecimal discountAmount){
        this.discountAmt = discountAmount;
        this.discountAmount = discountAmount;
    }

    public void setUserPocketAmount(BigDecimal userPocketAmount){
        this.userPocketAmount = userPocketAmount;
        this.merPocketAmt = userPocketAmount;
    }
}
