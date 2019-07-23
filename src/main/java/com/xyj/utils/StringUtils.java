package com.xyj.utils;

import com.xyj.enums.PayTypeEnum;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

public class StringUtils extends org.apache.commons.lang3.StringUtils{

	public static String concatStr(String separator, String... array){
		return org.apache.commons.lang.StringUtils.join(array, separator);
	}

	/**
	 * 手机号脱敏
	 * @param mobile
	 * @return
	 */
	public static String mobileReplace(String mobile){
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 身份证号脱敏
	 * @param idNum
	 * @return
	 */
	public static String idNumReplace(String idNum){
		return idNum.replaceAll("(\\d{6})\\d{8}(\\d{4})","$1********$2");
	}

	/**
	 * 银行卡号脱敏
	 * @param accNum
	 * @return
	 */
	public static String accNumReplace(String accNum){
		if(accNum.length()>=8){
			return accNum.substring(0, 4) + "********" + accNum.substring(accNum.length()-4);
		}else{
			return accNum.replaceAll("(\\d{4})\\d{8}(\\d{4})","$1********$2"); 
		}
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return obj == null || ("".equals(obj));
		} else if (obj instanceof Collection) {
			return obj == null || ((Collection) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			if (obj == null || ((Object[]) obj).length == 0) {
				return true;
			} else {
				boolean isEmpty = true;
				Object[] objArr = (Object[]) obj;
				for (Object objItem : objArr) {
					if (!isEmpty(objItem)) {
						isEmpty = false;
						break;
					}
				}
				return isEmpty;
			}
		} else
			return obj == null;
	}
	
	/**
	 * 判断传入字符串是否为选择字符串中的一个
	 *
	 * @param str
	 *            传入字符串
	 * @param str
	 *            选择的字符串
	 * @return true:是;false:否
	 */
	public static boolean isSelectedStr(String str, String... selectedStrs) {
		if (str != null && selectedStrs != null && selectedStrs.length > 0) {
			for (String selectedStr : selectedStrs) {
				if (str.equals(selectedStr)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 去空格领宽度&
	 */
	public static String replaceNull(String input) {
		return input.replaceAll("\\s*", "").replace("\u200D", "").replaceAll("&", "and");
	}
	
	public static String getLeftPadding(String suffix, char fill, int totalLength) {
		return getFillString("", suffix, fill, totalLength);
	}

	public static String getRightPadding(String prefix, char fill, int totalLength) {
		return getFillString(prefix, "", fill, totalLength);
	}
	
	public static String getFillString(String prefix, String suffix, char fill, int totalLength) {
		int c = 0;
		int needLength = totalLength - (prefix.length() + suffix.length());
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		while (c < needLength) {
			sb.append(fill);
			c++;
		}
		sb.append(suffix);
		return sb.toString();
	}
	
	public static String getChailTokenPayment(String accessToken) {
		
		String[] str = accessToken==null?null:accessToken.split("_");
		if(str==null || str.length==1){
			return null;
		}else{
			 for(String temp : str){
				 return temp;
			 }
		}
		return null;
	}

	/**
	 * 生成订单id
	 * @param paymentCode
	 * @return
	 */
	public static String generateNumPayId(String paymentCode){
		String dateStr = DateUtils.toString(new Date(),"yyyyMMddHHmmssSSS");
		Random random = new Random();
		int randomNum = random.nextInt(900000)+100000;
		return paymentCode.substring(3,paymentCode.length()) + dateStr + randomNum;
	}

	/**
	 * 生成订单id,末尾两位用于识别支付方式
	 * @param paymentCode
	 * @return
	 */
	public static String generateNumPayId(String paymentCode,String payTypeCode){
		StringBuilder preOrderId = new StringBuilder(generateNumPayId(paymentCode));
		if(PayTypeEnum.WX_PUB.getCode().equals(payTypeCode)){
			preOrderId.append("00");
		}else if(PayTypeEnum.WX_PUB_QR.getCode().equals(payTypeCode)){
			preOrderId.append("01");
		}else if(PayTypeEnum.WX_MICRO.getCode().equals(payTypeCode)){
			preOrderId.append("02");
		}else if(PayTypeEnum.ALIPAY_H5.getCode().equals(payTypeCode)){
			preOrderId.append("10");
		}else if(PayTypeEnum.ALIPAY_QR.getCode().equals(payTypeCode)){
			preOrderId.append("11");
		}else if(PayTypeEnum.ALIPAY_MICRO.getCode().equals(payTypeCode)){
			preOrderId.append("12");
		} else if(PayTypeEnum.UNIONPAY_STATIC.getCode().equals(payTypeCode)){
			preOrderId.append("20");
		} else if(PayTypeEnum.UNIONPAY_DYNAMIC.getCode().equals(payTypeCode)){
			preOrderId.append("21");
		} else if(PayTypeEnum.UNIONPAY_MICRO.getCode().equals(payTypeCode)){
			preOrderId.append("22");
		}
		return preOrderId.toString();
	}

	public static String generateOutTradeId(String paymentCode){
		String dateStr = DateUtils.toString(new Date(),"yyyyMMddHHmmssSSS");
		Long randomNum = (long)((Math.random()*9+1)*10000L);
		return paymentCode+dateStr+randomNum;
	}
	/**
	 * 生成订单id,末尾两位用于识别支付方式
	 * @param paymentCode
	 * @return
	 */
	public static String generateNumPayId(String paymentCode,String payTypeCode,String subType){	
		if(StringUtils.isEmpty(subType)){
			return generateNumPayId(paymentCode,payTypeCode);
		}
		return generateNumPayIdNXY(paymentCode,payTypeCode,subType);
	}
	public static String generateNumPayIdNXY(String paymentCode,String payTypeCode,String subType){
		String dateStr = DateUtils.toString(new Date(),"yyyyMMddHHmmssSSS");
		Random random = new Random();
		int randomNum = random.nextInt(900000)+100000;
		StringBuilder preOrderId = new StringBuilder();
		preOrderId.append(dateStr.substring(0, 8)).append(subType);
		preOrderId.append(paymentCode.substring(3,paymentCode.length()));		
		preOrderId.append(dateStr.substring(8, dateStr.length()));
		preOrderId.append(randomNum);		
		if(PayTypeEnum.WX_PUB.getCode().equals(payTypeCode)){
			preOrderId.append("00");
		}else if(PayTypeEnum.WX_PUB_QR.getCode().equals(payTypeCode)){
			preOrderId.append("01");
		}else if(PayTypeEnum.WX_MICRO.getCode().equals(payTypeCode)){
			preOrderId.append("02");
		}else if(PayTypeEnum.ALIPAY_H5.getCode().equals(payTypeCode)){
			preOrderId.append("10");
		}else if(PayTypeEnum.ALIPAY_QR.getCode().equals(payTypeCode)){
			preOrderId.append("11");
		}else if(PayTypeEnum.ALIPAY_MICRO.getCode().equals(payTypeCode)){
			preOrderId.append("12");
		}
		return preOrderId.toString();
	}

}
