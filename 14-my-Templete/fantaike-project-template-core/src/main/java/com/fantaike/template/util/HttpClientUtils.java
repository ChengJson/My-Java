package com.fantaike.template.util;

import com.fantaike.template.exception.BusinessException;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: HttpClientUtils
 * @Description: http请求工具类
 * @Author: wuguizhen
 * @Date: 2019/7/10 18:00
 * @Version: v1.0 文件初始创建
 */
public class HttpClientUtils {

	private static final CloseableHttpClient HTTP_CLIENT;

	private static final CloseableHttpClient HTTPS_CLIENT;
	
	public static final String CHARSET = "UTF-8";

	private static final String HTTPS = "https";
	
	//采用静态代码块，初始化超时时间配置，再根据配置生成默认httpClient对象
	static {
		RequestConfig config = RequestConfig
				.custom()
				//设置发起请求前的等待时间
				.setConnectTimeout(60000)
				//设置等待数据返回的超时时间
				.setSocketTimeout(60000).build();
		HTTP_CLIENT = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain,authType) -> true).build();
			sslsf = new SSLConnectionSocketFactory(sslContext);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		HTTPS_CLIENT = HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.setSSLSocketFactory(sslsf)
				.build();
	}

	/**
	 * @Description: http get请求 参数是map
	 * @param url
     * @param params
	 * @Date: 2019/7/10 18:03
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	public static String httpGet(String url, Map<String, String> params) {
		List<NameValuePair> pairs = setHttpParams(params);
		String param = URLEncodedUtils.format(pairs, CHARSET);
        return httpGet(url,param);
	}

	/**
	 * @Description:  http get请求 参数是String url + ? + param
	 * @param url
     * @param param
	 * @Date: 2019/7/10 18:03
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	public static String httpGet(String url, String param) {
		if(!ObjectIsNullUtil.isNullOrEmpty(param)) {
			url = url +"?" +param;
		}
		return doGet(url);
	}
	
	/**
	 * @Description: http get请求 没有参数
	 * @param url
	 * @Date: 2019/7/10 18:03
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	public static String httpGet(String url) {
		return doGet(url);
	}
	
	/**
	 * @Description: http post 请求 参数是map
	 * @param url
	 * @param params
	 * @Date: 2019/7/10 18:03
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	public static String httpPost(String url, Map<String,String> params) throws UnsupportedEncodingException{
		HttpPost httpPost = new HttpPost(url);
		if(!ObjectIsNullUtil.isNullOrEmpty(params)) {
			List<NameValuePair> formparams = setHttpParams(params);
			UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, CHARSET);
			//通过setEntity()设置参数给post
			httpPost.setEntity(param);
		}
		return httpExecute(httpPost);
	}
	
	/**
	 * @Description: http post请求 json交互（服务端需要添加@RequestBody）
	 * @param url 请求地址
	 * @param param 参数
	 * @Date: 2019/7/10 18:02
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	public static String httpPostJson(String url,String param) {
		HttpPost httpPost = new HttpPost(url);
		if(!ObjectIsNullUtil.isNullOrEmpty(param)) {
			 //标识出传递的参数是 application/json
            StringEntity stringEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
		}
		return httpExecute(httpPost);
	}
	

	/**
	 * @Description: 指定get请求
	 * @param url
	 * @Date: 2019/7/10 18:02
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	private static String doGet(String url) {
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		return httpExecute(httpGet);
	}

	/**
	 * @Description: http 執行封裝好的get/post方法
	 * @param request
	 * @Date: 2019/7/10 18:01
	 * @Author: wuguizhen
	 * @Return java.lang.String
	 * @Throws
	 */
	private static String httpExecute(HttpUriRequest request) throws BusinessException {
		String uriScheme = request.getURI().getScheme();
		if(HTTPS.toUpperCase().equals(uriScheme.toUpperCase())){
			try(CloseableHttpResponse response = HTTPS_CLIENT.execute(request)){
				int stat = 200;
				// 判断返回状态是否为200
				if (response.getStatusLine().getStatusCode() == stat) {
					String content = EntityUtils.toString(response.getEntity(), CHARSET);
					return content;
				}else {
					throw new BusinessException("HttpClient,error response :" + response.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("HttpClient,error:" + e.getMessage());
			}
		}else{
			try(CloseableHttpResponse response = HTTP_CLIENT.execute(request)){
				int stat = 200;
				// 判断返回状态是否为200
				if (response.getStatusLine().getStatusCode() == stat) {
					String content = EntityUtils.toString(response.getEntity(), CHARSET);
					return content;
				}else {
					throw new BusinessException("HttpClient,error response :" + response.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("HttpClient,error:" + e.getMessage());
			}
		}
	}

	/**
	 * @Description: 设置请求参数
	 * @param paramMap
	 * @Date: 2019/7/10 18:01
	 * @Author: wuguizhen
	 * @Return java.util.List<org.apache.http.NameValuePair>
	 * @Throws
	 */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formParams = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formParams;
    }
}