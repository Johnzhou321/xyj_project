package com.xyj.utils;

import com.xyj.enums.PayTypeEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;
import com.xyj.utils.WxPayConstants.SignType;


public class WxPayUtil {
	
	private static Logger log = LoggerFactory.getLogger(WxPayUtil.class);
	
	public static final String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            WxPayUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }
    
	/**
	 * 将普通map转为sortmap便于进行签名
	 * 
	 * @param requestMap
	 * @return
	 */
	public static SortedMap<String, String> mapToSortMap(Map<String, String> requestMap) {
		SortedMap< String,  String> map = new TreeMap< String,  String>();
		for (Entry<String, String> entry : requestMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
    
    

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
    
    //将微信返回报文解析为map
    public static HashMap<String, String> parseRequest2Map(HttpServletRequest request) throws Exception {
		
		HashMap<String, String> map = new HashMap<String, String>();
		int length = request.getContentLength();
		log.info("====================length="+length+"getContentType="+request.getContentType()+"requestMethod="+request.getMethod());
		if(length<=0){
			return null;
		}
		String requestStr = "";
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			requestStr = parameterNames.nextElement();
		}	
		log.info("====================requestStr="+requestStr);
		 try {
			 HashMap<String, String> data = new HashMap<String, String>();
	            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	            InputStream stream = new ByteArrayInputStream(requestStr.getBytes("UTF-8"));
	            org.w3c.dom.Document doc = documentBuilder.parse(stream);
	            doc.getDocumentElement().normalize();
	            NodeList nodeList = doc.getDocumentElement().getChildNodes();
	            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
	                Node node = nodeList.item(idx);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
	                    data.put(element.getNodeName(), element.getTextContent());
	                }
	            }
	            try {
	                stream.close();
	            } catch (Exception ex) {
	                // do nothing
	            }
	            return data;
	        } catch (Exception ex) {
	        	log.error("微信回掉解析XML失败="+ex.getMessage());
	            throw ex;
	        }
		
		/*
		InputStream   in   =   new   ByteArrayInputStream(requestStr.getBytes("UTF-8"));   
		// 读取输入流
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		Document document = reader.read(in);

		// 获取root
		Element root = document.getRootElement();

		// 获取所有子节点
		// List<Element> elementList = root.elements();

		// 遍历节点
		listChild(map, root);
		in.close();
		return map;*/
		
	}
    
  //将微信返回报文解析为map
    public static HashMap<String, String> parseXML2Map(String requestStr) throws Exception {
				
		log.info("====================requestStr="+requestStr);
		 try {
			 HashMap<String, String> data = new HashMap<String, String>();
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			    dbf.setExpandEntityReferences(false);
			    String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			    dbf.setFeature(FEATURE, true);
			    dbf.setXIncludeAware(false);
	            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	            InputStream stream = new ByteArrayInputStream(requestStr.getBytes("UTF-8"));
	            org.w3c.dom.Document doc = documentBuilder.parse(stream);
	            doc.getDocumentElement().normalize();
	            NodeList nodeList = doc.getDocumentElement().getChildNodes();
	            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
	                Node node = nodeList.item(idx);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
	                    data.put(element.getNodeName(), element.getTextContent());
	                }
	            }
	            try {
	                stream.close();
	            } catch (Exception ex) {
	                // do nothing
	            }
	            return data;
	        } catch (Exception ex) {
	        	log.error("微信回掉解析XML失败="+ex.getMessage());
	            throw ex;
	        }
		
	}
    
	public static void listChild(Map<String, String> map, Element e) {
		if (!"xml".equals(e.getName())) {
			map.put(e.getName(), e.getText());
		}
		List<Element> list = e.elements();
		for (Element child : list) {
			listChild(map, child);
		}
	}


    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, SignType.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put(WxPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = xmlToMap(xmlStr);
        if (!data.containsKey(WxPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WxPayConstants.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, SignType.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, SignType signType) throws Exception {
        if (!data.containsKey(WxPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WxPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }
    
	/**
	 * 对参数进行md5加密
	 * 
	 * @param
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static String createSignForKey(SortedMap<Object, Object> parameters,
			String key) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equalsIgnoreCase(k)
					&& !"key".equalsIgnoreCase(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5(sb.toString()).toUpperCase();
		return sign;
	}
    

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
    	StringBuffer sb = new StringBuffer();
		Set es = data.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equalsIgnoreCase(k)
					&& !"key".equalsIgnoreCase(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }


    /**
	 * 创建随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		Random rd = new Random();
		for (int i = 0; i < length; i++) {
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 创建随机数，16位
	 * 
	 * @return
	 */
	public static String createNoncestr() {
		return createNoncestr(16);
	}


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 日志
     * @return
     */
    public static Logger getLogger() {
        Logger logger = LoggerFactory.getLogger("wxpay java sdk");
        return logger;
    }

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
    
    
    //===========================新增20171214============================
    /**
	 * 获取请求的ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1%0".equals(ip)) {
			ip = "127.0.0.1";
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		if(ip.contains(",")){
			return ip.split(",")[0];
		}
		
		return ip;
	}
	
	
	/**
	 * 获取证书加密链接 <br/>
	 * 证书名称为 mchid.p12 ,密钥为商户号mchid
	 * 
	 * @param mchid
	 *            商户号
	 * @return
	 * @throws Exception
	 */
	public static CloseableHttpClient getSSLHttpClient(String mchid)throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String classPath = WxPayUtil.class.getResource("/").getPath();
		classPath = classPath.substring(0, classPath.indexOf("classes") + 7)
				+ "/weixin/";
		FileInputStream instream = new FileInputStream(new File(classPath
				+ mchid + ".p12"));
		try {
			keyStore.load(instream, mchid.toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, mchid.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		return httpclient;
	}
	
	
	
	/**
	 * 
	 * 
	 * @param url
	 * @param data
	 * @param mchid
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String data, String mchid)
			throws Exception {
		String result = null;
		log.info("weixin ssl request data = " + data);
		CloseableHttpClient httpClient = WxPayUtil.getSSLHttpClient(mchid);

		HttpPost httpPost = new HttpPost(url);

		if (null != data) {
			StringEntity stringEntity = new StringEntity(data,"UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("text/xml");
			httpPost.setEntity(stringEntity);
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(5000).setConnectTimeout(5000).build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			if (entity != null)
				result = EntityUtils.toString(entity, "UTF-8");
		}
		log.info("weixin ssl response data = " + result);
		return result;

	}



	/**
	 * 获取本机的ip地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLoacalIP() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();// 获得本机IP
	}

	/**
	 * 是否微信浏览器访问
	 * 
	 * @date 2015年12月10日
	 * @auther 王侣为
	 * @param request
	 * @return
	 */
	public static boolean isWxExplore(HttpServletRequest request) {
		if (request.getHeader("user-agent") != null) {
			String ua = request.getHeader("user-agent").toLowerCase();
			if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取交易类型
	 * @param wxPayTypeCode
	 * @return
	 */
	public static String getTradeType(String wxPayTypeCode){
		if(PayTypeEnum.WX_PUB.getCode().equalsIgnoreCase(wxPayTypeCode)){//公众号支付
			return "JSAPI";
		}else if(PayTypeEnum.WX_PUB_QR.getCode().equalsIgnoreCase(wxPayTypeCode)){//公众号扫码支付
			return "NATIVE";
		}else if(PayTypeEnum.WX_APP.getCode().equalsIgnoreCase(wxPayTypeCode)){
			return "APP";
		}else{
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
        /*SortedMap<String,String> map = new TreeMap<>();

        map.put("agentNo","1");
        map.put("amount","0.01");
        map.put("body","测试清远商户1");
        map.put("payTypeCode","wxPub");
        map.put("subAppid","wxe8a3ec89cb75eea4");
        map.put("subOpenid","ol1caxPh7N0RpTDRmTGKVH728Syk");
        map.put("terminalType","WEB");
        map.put("version","1.0.0");
        String signUtil = SignUtil.createSignStr(map,"1234567890");

        System.out.println(signUtil);

        SortedMap<Object,Object> mapObject = new TreeMap<>();
        mapObject.put("agentNo","1");
        mapObject.put("amount","0.01");
        mapObject.put("body","测试清远商户1");
        mapObject.put("cashierNo","");
        mapObject.put("outTrdeNo","");
        mapObject.put("payTypeCode","wxPub");
        mapObject.put("subAppid","wxe8a3ec89cb75eea4");
        mapObject.put("subOpenid","ol1caxLMe_6q_PzG3SsgUd4S2zmE");
        mapObject.put("subject","");
        mapObject.put("terminalType","WEB");
        mapObject.put("timeExpire","");
        mapObject.put("timePaid","");
        mapObject.put("version","1.0.0");
        System.out.println(createSignForKey(mapObject,"1234567890"));*/

		String str = "agentNo=1&amount=0.01&body=测试清远商户1&payTypeCode=wxPub&subAppid=wxe8a3ec89cb75eea4&subOpenid=ol1caxPh7N0RpTDRmTGKVH728Syk&terminalType=WEB&verson=1.0.0&key=1234567890";
		System.out.println(MD5(str));


	}

}
