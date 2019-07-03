package com.xyj.modules.pay.vo;

public class PayParamsVo {
    protected String agentSecret;
    protected String payPipeCode;
    protected String settleCycleCode;
    protected String mchid; //银行商户号（注意不同通道对应的商户号不同，如0.2%费率通道与0%费率通道有不同的商户号）
    protected String tradeType; //交易类型：
    protected String subMchid; //商户的子商户号
    protected String payWayCode; //支付方式
    protected String bankPaymentServiceId; //支付服务商id
    protected String merchantPayProductItemRelationId;
    
    
    //支付宝微信共有参数
    private String appid; //支付宝应用APPID 微信应用APPID
    private String publicSecret; //应用公钥
	private String privateSecret; //支付宝私钥    微信应用秘钥
	
    //支付宝
	private String signType; //签名方法
	
	//weixin 
	private String subAppid; //关联的APPID
	private String channelNo; //渠道商号
	private String subscribeAppid; //默认关注公众号

	private String linkOrg;
	
	
    public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPublicSecret() {
		return publicSecret;
	}

	public void setPublicSecret(String publicSecret) {
		this.publicSecret = publicSecret;
	}

	public String getPrivateSecret() {
		return privateSecret;
	}

	public void setPrivateSecret(String privateSecret) {
		this.privateSecret = privateSecret;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getSubscribeAppid() {
		return subscribeAppid;
	}

	public void setSubscribeAppid(String subscribeAppid) {
		this.subscribeAppid = subscribeAppid;
	}

	public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSubMchid() {
        return subMchid;
    }

    public void setSubMchid(String subMchid) {
        this.subMchid = subMchid;
    }

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    public String getAgentSecret() {
        return agentSecret;
    }

    public void setAgentSecret(String agentSecret) {
        this.agentSecret = agentSecret;
    }

    public String getPayPipeCode() {
        return payPipeCode;
    }

    public void setPayPipeCode(String payPipeCode) {
        this.payPipeCode = payPipeCode;
    }

    public String getSettleCycleCode() {
        return settleCycleCode;
    }

    public void setSettleCycleCode(String settleCycleCode) {
        this.settleCycleCode = settleCycleCode;
    }

    public String getBankPaymentServiceId() {
        return bankPaymentServiceId;
    }

    public void setBankPaymentServiceId(String bankPaymentServiceId) {
        this.bankPaymentServiceId = bankPaymentServiceId;
    }

    public String getMerchantPayProductItemRelationId() {
        return merchantPayProductItemRelationId;
    }

    public void setMerchantPayProductItemRelationId(String merchantPayProductItemRelationId) {
        this.merchantPayProductItemRelationId = merchantPayProductItemRelationId;
    }

	public String getLinkOrg() {
		return linkOrg;
	}

	public void setLinkOrg(String linkOrg) {
		this.linkOrg = linkOrg;
	}

}
