package com.xyj.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用状态类型枚举类
 */
public enum CommonStatusEnum {
    ACTIVE("A","正常"),

	FREEZE("F","冻结"),

	INACTIVE("I","停用"),
    
    DELETED("D","已删除"),
    
    UNCONFIG("U","未配置"),

    RECORD("R", "仅作记录");
    /**状态码**/
    private String statusCode;
    /**状态描述**/
    private String statusDes;

    CommonStatusEnum(String statusCode, String statusDes) {
        this.statusCode = statusCode;
        this.statusDes = statusDes;
    }

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDes() {
		return statusDes;
	}

	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}


	public static Map<String, Map<String, Object>> toMap() {
		CommonStatusEnum[] ary = CommonStatusEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].getStatusCode();
			map.put("statusCode", ary[num].getStatusCode());
			map.put("statusDes", ary[num].getStatusDes());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		CommonStatusEnum[] ary = CommonStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("statusCode", ary[i].getStatusCode());
			map.put("statusDes", ary[i].getStatusDes());
			list.add(map);
		}
		return list;
	}

	public static CommonStatusEnum getEnum(String name) {
		CommonStatusEnum[] arry = CommonStatusEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}
	
	
}