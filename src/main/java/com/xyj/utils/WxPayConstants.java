package com.xyj.utils;

import com.xyj.conf.SysConfig;

/**
 * 常量
 */
public class WxPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL_SUFFIX     = SysConfig.getConfig("wx.request.url.micropay");//"/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = SysConfig.getConfig("wx.request.url.unifiedorder");//"/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = SysConfig.getConfig("wx.request.url.orderquery");//"/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = SysConfig.getConfig("wx.request.url.reverse");//"/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = SysConfig.getConfig("wx.request.url.closeorder");//"/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = SysConfig.getConfig("wx.request.url.refund");//"/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = SysConfig.getConfig("wx.request.url.refundquery");//"/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = SysConfig.getConfig("wx.request.url.downloadbill");//"/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = SysConfig.getConfig("wx.request.url.report");//"/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = SysConfig.getConfig("wx.request.url.shorturl");//"/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = SysConfig.getConfig("wx.request.url.authcodetoopenid");//"/tools/authcodetoopenid";
    public static final String SUBDEVCONFIG_URL_SUFFIX = SysConfig.getConfig("wx.request.url.addsubdevconfig");//"/secapi/mch/addsubdevconfig";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    
    //微信进件
    public static final String MERCHANTINTOPIECES = "https://" + DOMAIN_API +SysConfig.getConfig("wx.request.url.submchmanage.add");//"/secapi/mch/submchmanage?action=add";
    //微信开发者配置
    public static final String MERCHANTCONFIG = "https://" + WxPayConstants.DOMAIN_API + SysConfig.getConfig("wx.request.url.addsubdevconfig");//WxPayConstants.SUBDEVCONFIG_URL_SUFFIX;
    //微信关注配置
    public static final String MERCHANTRECOMMENDCONFIG = "https://" + WxPayConstants.DOMAIN_API +SysConfig.getConfig("wx.request.url.addrecommendconf");//"/secapi/mkt/addrecommendconf";
    //微信修改商户进件信息
    public static final String MERCHANTMODIFY = "https://" + WxPayConstants.DOMAIN_API + SysConfig.getConfig("wx.request.url.submchmanage.modify");//"/secapi/mch/submchmanage?action=modify";


    // 统一支付下单
    public static final String UNIFIEDORDER_URL = "https://" + DOMAIN_API + SysConfig.getConfig("wx.request.url.unifiedorder");//UNIFIEDORDER_URL_SUFFIX;
    // 微信退款
    public static final String UNIFIEDREFUND_URL = "https://" + DOMAIN_API +SysConfig.getConfig("wx.request.url.refund");//"https://api.mch.weixin.qq.com/secapi/pay/refund";
    //被扫下单
    public static final String MICRO_UNIFIEDORDER_URL = "https://" + DOMAIN_API + SysConfig.getConfig("wx.request.url.micropay");//MICROPAY_URL_SUFFIX;

    //网页授权获取code
    public static final String SNSAPI_BASE = "snsapi_base";
    public static final String STATE = "STATE";
    
    //微信撤销订单
    public static final String REVERSE_URL = "https://" + DOMAIN_API + SysConfig.getConfig("wx.request.url.reverse");//"https://api.mch.weixin.qq.com/secapi/pay/reverse";

}

