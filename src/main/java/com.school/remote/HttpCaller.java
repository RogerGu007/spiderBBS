package com.school.remote;

import com.school.Gson.PostMsgGson;
import com.school.utils.GsonUtils;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//CascadeParam暂时用不到
public class HttpCaller {
	private final static int connectTimeout = 3 * 1000;
	private final static int socketTimeout = 4 * 60 * 1000;
	private final static int maxConnTotalInt = 100;
	private final static int maxConnPerRouteInt = 40;
	private final static int connectionRequestTimeoutInt = 3 * 1000;
	public final static String DEFAULT_CHARSET = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(HttpCaller.class);

	private static CloseableHttpClient client;
	private static Object lock = new Object();

	public static CloseableHttpClient getClient() {
		if (client == null) {
			synchronized (lock) {
				if (client == null) {
					init();
				}
			}
		}
		return client;
	}

	private static void init() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeoutInt)
				.build();

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setMaxTotal(maxConnTotalInt);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxConnTotalInt);

		HttpClientBuilder builder = HttpClientBuilder.create();
		client = builder
				.setMaxConnTotal(maxConnTotalInt)
				.setMaxConnPerRoute(maxConnPerRouteInt)
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingHttpClientConnectionManager)
				.setRetryHandler(new HttpRequestRetryHandler() {
					public boolean retryRequest(IOException exception, int executionCount, org.apache.http.protocol.HttpContext context) {
						if (executionCount >= 3)
							return false;

						if (exception instanceof NoHttpResponseException)
						{
							return true;
						}
						return false;
					}
				})
				.build();
	}

	private static CloseableHttpResponse httpGet(String url, Map<String, String> params) {
		CloseableHttpResponse response = null;
		try {
			if (params != null) {
				url += "?";
				for (Map.Entry<String, String> kv : params.entrySet()) {
					url += String.format("%s=%s", kv.getKey(), kv.getValue());
				}
			}
			response = getClient().execute(new HttpGet(url));
		} catch (Exception ex) {
			Throwable ee = ExceptionUtils.getRootCause(ex);
			String msg = String.format("request to [URL: %s ] failed. [%s]", url, ex);
			logger.error(msg, ee);
		}
		return response;
	}

	public static String get(String url, Map<String, String> params) {
		CloseableHttpResponse result = httpGet(url, params);
		if (result == null || ClientResponse.Status.OK.getStatusCode() != result.getStatusLine().getStatusCode()) {
			throw new RuntimeException("invalid http response detected. ClientResponse info: " + result);
		}
		try {
			return EntityUtils.toString(result.getEntity(), DEFAULT_CHARSET);
		} catch (Exception ex) {
			logger.error(String.format("url=%s failed, params", url, params == null ? "" : params.toString()));
			return null;
		}
	}

	private static CloseableHttpResponse httpPost(String url, Map<String, String> params) {
		CloseableHttpResponse response = null;
		HttpPost httppost = new HttpPost(url);
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, DEFAULT_CHARSET);
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		entityBuilder.setCharset(Charset.forName(DEFAULT_CHARSET));
		entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
		if (params != null) {
			for (Map.Entry<String, String> kv : params.entrySet()) {
//				entityBuilder.addTextBody(kv.getKey(), kv.getValue());
				entityBuilder.addPart(kv.getKey(), new StringBody(kv.getValue(), contentType));
			}
		}
		try {
			HttpEntity entity = entityBuilder.build();
			httppost.setEntity(entity);
			response = getClient().execute(httppost);
		} catch (Exception ex) {
			Throwable ee = ExceptionUtils.getRootCause(ex);
			String msg = String.format("request to [URL: %s ] failed. [%s]", url, ex);
			logger.error(msg, ee);
			return null;
		}
		return response;
	}

	public static String post(String url, Map<String, String> params) {
		CloseableHttpResponse result = httpPost(url, params);
		if (result == null || ClientResponse.Status.OK.getStatusCode() != result.getStatusLine().getStatusCode()) {
			throw new RuntimeException("invalid http response detected. ClientResponse info: " + result);
		}
		try {
			return EntityUtils.toString(result.getEntity());
		} catch (Exception ex) {
			logger.error(String.format("url=%s failed, params=%s", url, params == null ? "" : params.toString()));
			return null;
		}
	}

	public static void main(String[] args) {  //测试方法
		String url = "http://47.100.197.44/news/rest/postmsg/9/postmessage";
		Map<String, String> map = new HashMap<String, String>();
		PostMsgGson postMsgGson = new PostMsgGson();
		postMsgGson.setPostDate(new Date());
		postMsgGson.setContent("test");
		postMsgGson.setDetailContent("testdetail");
		postMsgGson.setLocationCode(21);
		postMsgGson.setNewsSubType(1);
		postMsgGson.setNewsType(3);
		postMsgGson.setSourceArticleUrl("http://www.newsmth.net/nForum/article/Career_Upgrade/625337");
		map.put("dto", GsonUtils.toGsonString(postMsgGson));
		String resp = post(url, map);
		System.out.println(resp);
	}
}
