package com.xyj.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:
 * </b>
 *
 */
public enum ReturnCodeEnum {

    SUCCESS("00","SUCCESS"),
    FAIL("01","FAIL"),
	NOT_PASS("1","请求成功,验证失败"),

    PARAM_NULL("50001","参数为空异常"),
    PARAM_ERROR("02","请求参数不合法"),
    SIGN_ERROR("03","签名错误"),
	NOT_DEFINE_ERROR("99","系统异常");
	
    /**
     * 编码
     */
    private String code;

    /** 描述 */
    private String desc;

    private ReturnCodeEnum(String code , String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        ReturnCodeEnum[] ary = ReturnCodeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            map.put("code", ary[num].getCode());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toList() {
        ReturnCodeEnum[] ary = ReturnCodeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            map.put("code", ary[i].getCode());
            list.add(map);
        }
        return list;
    }

    public static ReturnCodeEnum getEnum(String name) {
        ReturnCodeEnum[] arry = ReturnCodeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        ReturnCodeEnum[] enums = ReturnCodeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (ReturnCodeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
