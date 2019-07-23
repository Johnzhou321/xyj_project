package com.xyj.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayTypeEnum {
    //静态二维码
    WX_PUB("wxPub", "微信公众号支付", "weixin"),
    //主扫
    WX_PUB_QR("wxPubQR", "微信公众账号扫码支付", "weixin"),
    //不做
    WX_APP("wxApp", "微信app支付", "weixin"),
    //动态二维码
    WX_MICRO("wxMicro", "微信付款码支付", "weixin"),
    //静态二维码，被扫。
    ALIPAY_QR("alipayQR", "支付宝扫码支付", "alipay"),
    //主扫
    ALIPAY_H5("alipayH5", "支付宝扫码H5支付", "alipay"),
    //不做
    ALIPAY_APP("alipayApp", "支付宝APP支付", "alipay"),
    //动态二维码
    ALIPAY_MICRO("alipayMicro", "支付宝付款码支付", "alipay"),

    // 银联静态二维码
    UNIONPAY_STATIC("unionpayStatic", "银联静态二维码支付", "unionpay"),
    // 银联动态二维码
    UNIONPAY_DYNAMIC("unionpayDynamic", "银联动态二维码支付", "unionpay"),
    // 银联被扫
    UNIONPAY_MICRO("unionpayMicro", "银联付款码支付", "unionpay");

    private String code;
    private String codeDes;

    private String wayCode;

    public static Map<String, Map<String, Object>> toMap(String wayCode) {
        PayTypeEnum[] ary = PayTypeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            if (ary[num].getWayCode().equals(wayCode)) {
                Map<String, Object> map = new HashMap<String, Object>();
                String key = ary[num].name();
                map.put("code", ary[num].getCode());
                map.put("codeDes", ary[num].getCodeDes());
                enumMap.put(key, map);
            }
        }
        return enumMap;
    }

    public static Map<String, Map<String, Object>> toMap() {
        PayTypeEnum[] ary = PayTypeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].getCode();
            map.put("code", ary[num].getCode());
            map.put("codeDes", ary[num].getCodeDes());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    public static List toList(String wayCode) {
        PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            if (ary[i].getWayCode().equals(wayCode)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("code", ary[i].getCode());
                map.put("codeDes", ary[i].getCodeDes());
                list.add(map);
            }
        }
        return list;
    }
    
    public static List toList() {
        PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("code", ary[i].getCode());
                map.put("codeDes", ary[i].getCodeDes());
                list.add(map);
        }
        return list;
    }

    private PayTypeEnum(String code, String codeDes, String wayCode) {
        this.code = code;
        this.codeDes = codeDes;
        this.wayCode = wayCode;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getCodeDes() {
        return codeDes;
    }


    public void setCodeDes(String codeDes) {
        this.codeDes = codeDes;
    }

    public String getWayCode() {
        return wayCode;
    }

    public void setWayCode(String wayCode) {
        this.wayCode = wayCode;
    }

}
