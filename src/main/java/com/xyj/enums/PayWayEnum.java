package com.xyj.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayWayEnum {
	WEIXIN("weixin","微信支付"),

	ALIPAY("alipay","支付宝支付"),

	JD("jd","京东支付"),

	UNIONPAY("unionpay","银联支付")
	;
	
	private String code;
	private String codeDes;

	public static boolean containsCode(String code) {
		PayWayEnum[] values = values();
		for (PayWayEnum value : values) {
			if (value.getCode().equals(code)) {
				return  true;
			}
		}
		return false;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PayWayEnum[] ary = PayWayEnum.values();
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

	public static List toList() {
		PayWayEnum[] ary = PayWayEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", ary[i].getCode());
			map.put("codeDes", ary[i].getCodeDes());
			list.add(map);
		}
		return list;
	}
	
	private PayWayEnum(String code, String codeDes){
		this.code = code;
		this.codeDes = codeDes;
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
	
	
	
	
	

}
