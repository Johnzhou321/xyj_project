package com.xyj.utils;

import com.xyj.core.exception.BizException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtils {
	private static final String GET  = "GET";
	private static final String POST = "POST";
	private static final String CHARSET = "UTF-8";
	
    /** 链接超时时间 **/
    private static final Integer CONNECT_TIME_OUT = 40 * 1000;

    /** 数据传输超时时间 **/
    private static final Integer SOCKET_TIME_OUT = 40 * 1000;

	/**
	 * https 域名校验
	 */
	private class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	/**
	 * https 证书管理
	 */
	private class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;  
		}
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}

	
	private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
	private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpClientUtils().new TrustAnyHostnameVerifier();
	
	private static SSLSocketFactory initSSLSocketFactory() {
		try {
			TrustManager[] tm = {new HttpClientUtils().new TrustAnyTrustManager() };  
			SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");  
			sslContext.init(null, tm, new java.security.SecureRandom());  
			return sslContext.getSocketFactory();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
		}
		
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		conn.setConnectTimeout(19000);
		conn.setReadTimeout(19000);
		
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		
		if (headers != null && !headers.isEmpty())
			for (Entry<String, String> entry : headers.entrySet())
				conn.setRequestProperty(entry.getKey(), entry.getValue());
		
		return conn;
	}
	
	/**
	 * Send GET request
	 */
	public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
			conn.connect();
			return readResponseString(conn);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public static String get(String url, Map<String, String> queryParas) {
		return get(url, queryParas, null);
	}
	
	public static String get(String url) {
		return get(url, null, null);
	}
	
	/**
	 * Send POST request
	 */
	public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes(CHARSET));
			out.flush();
			out.close();
			
			return readResponseString(conn);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public static String post(String url, Map<String, String> queryParas, String data) {
		return post(url, queryParas, data, null);
	}
	
	public static String post(String url, String data, Map<String, String> headers) {
		return post(url, null, data, headers);
	}
	
	public static String post(String url, String data) {
		return post(url, null, data, null);
	}
	
	private static String readResponseString(HttpURLConnection conn) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
			String line = null;
			while ((line = reader.readLine()) != null){
				sb.append(line).append("\n");
			}
			return sb.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Build queryString of the url
	 */
	private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty())
			return url;
		
		StringBuilder sb = new StringBuilder(url);
		boolean isFirst;
		if (url.indexOf("?") == -1) {
			isFirst = true;
			sb.append("?");
		}
		else {
			isFirst = false;
		}
		
		for (Entry<String, String> entry : queryParas.entrySet()) {
			if (isFirst) isFirst = false;	
			else sb.append("&");
			
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isNotEmpty(value))
				try {value = URLEncoder.encode(value, CHARSET);} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
			sb.append(key).append("=").append(value);
		}
		return sb.toString();
	}
	
	public static String readIncommingRequestData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line=null; (line=br.readLine())!=null;) {
				result.append(line).append("\n");
			}
			
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (br != null)
				try {br.close();} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	
	public static String uploadMultipartFile(String mchid,String url,Map<String,String> textMap,Map<String,String> fileMap){
		CloseableHttpClient httpClient = null;
/*        try {
    		httpClient = HttpKit.getSSLHttpClient(mchid);
    		HttpPost httpPost = new HttpPost(url);
    		// 对请求的表单域进行填充
    		@SuppressWarnings("deprecation")
			MultipartEntity reqEntity = new MultipartEntity();
    		
    		//填充文件
    		if(fileMap!=null){
    			Iterator<Entry<String,String>> it = fileMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String,String> entry = it.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					// 创建待处理的表单域内容文本
					FileBody file = new FileBody(new File(value));  WWW
					reqEntity.addPart(key, file);
				}
    		}
    		
    		//填充参数
			if (textMap != null) {
				Iterator<Entry<String,String>> it = textMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String,String> entry = it.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					// 创建待处理的表单域内容文本
					StringBody bodyValue = new StringBody(value);
					reqEntity.addPart(key, bodyValue);
				}
			}
	
			// 设置请求
			httpPost.setEntity(reqEntity);
			// 执行
			HttpResponse response = httpClient.execute(httpPost);
	
			HttpEntity httpEntity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"));
			StringBuffer backData = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				backData.append(line);
			}
			  return backData.toString();	
			
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return null;	
	}	
	
	
	/**
	 * 获取证书加密链接 <br/>
	 * 证书名称为 mchid.p12 ,密钥为商户号mchid
	 * 
	 * @param keyFileName
	 *            商户号
	 * @return
	 * @throws Exception
	 */
	public static CloseableHttpClient getSSLHttpClient(String keyFileName)throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String classPath = HttpClientUtils.class.getResource("/").getPath();
		classPath = classPath.substring(0, classPath.indexOf("classes") + 7)
				+ "/";
		FileInputStream instream = new FileInputStream(new File(classPath
				+ keyFileName + ".p12"));
		try {
			keyStore.load(instream, keyFileName.toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, keyFileName.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		return httpclient;
	}
	
	
	public static String postJson(String url, String param) {
		try {
			HttpPost httpPost = new HttpPost(url.trim());
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			StringEntity se = new StringEntity(param.toString(), CHARSET);
			se.setContentType("application/json");
			httpPost.setEntity(se);
			HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);
			return EntityUtils.toString(httpResponse.getEntity(), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    public static HttpClient getHttpClient(){
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(CONNECT_TIME_OUT);
        configBuilder.setSocketTimeout(SOCKET_TIME_OUT);

        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setDefaultRequestConfig(configBuilder.build());
        HttpClient httpClient = clientBuilder.build();
        return httpClient;
    }
    
    /**
     * 获取客户端的IP地址
     *
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                   
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

	public static String httpsReq(String reqUrl, String param) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException,
			KeyManagementException {
		URL url = new URL(reqUrl);
		HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
		httpsConn.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String paramString, SSLSession paramSSLSession) {
				return true;
			}
		});

		//创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate,
										   String paramString) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate,
										   String paramString) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, new TrustManager[] { tm }, new java.security.SecureRandom());

		//从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		//创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		httpsConn.setSSLSocketFactory(ssf);

		httpsConn.setDoOutput(true);
		httpsConn.setRequestMethod("POST");
		httpsConn.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
		httpsConn.setRequestProperty("Content-Length", String.valueOf(param.length()));

		OutputStreamWriter out = new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
		out.write(param);
		out.flush();
		out.close();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		InputStream inputStream = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			inputStream = httpsConn.getInputStream();
			byte[] buf = new byte[8096];

			int offset = -1;
			while ((offset = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, offset);
			}

			resultBuffer.append(new String(outputStream.toByteArray(), "UTF-8"));
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}
		//BufferedReader reader = new BufferedReader(new InputStreamReader(
		//    httpsConn.getInputStream(), "UTF-8"));
		//String tempLine = "";
		//while ((tempLine = reader.readLine()) != null) {
		//    resultBuffer.append(tempLine).append(System.getProperty("line.separator"));
		//}
		return resultBuffer.toString();
	}



	public static String httpReq(String reqUrl, String param) {
		String line = "";
		StringBuffer resultSting = new StringBuffer();
		try {

			URL url = new URL(reqUrl);

			URLConnection con = url.openConnection();

			byte[] xmlData = param.getBytes();

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "text/xml");
			con.setRequestProperty("Content-length",String.valueOf(xmlData.length));

			OutputStream out = con.getOutputStream();
			out.write(param.getBytes("GBK"));
			out.flush();
			out.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "gbk"));
			//对返回值报文进行打印
			for (line = br.readLine(); line != null; line = br.readLine()) {
				resultSting.append(line);
			}
			return resultSting.toString();
		} catch (MalformedURLException e) {
			throw new BizException("http请求异常：MalformedURLException");
		} catch (IOException e) {
			throw new BizException("http请求异常：IOException");
		}
	}

}
