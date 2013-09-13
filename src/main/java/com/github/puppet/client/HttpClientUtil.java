package com.github.puppet.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.github.common.exception.RAMPAPIException;
import com.github.common.exception.StatusCode;
import com.github.common.security.StringValidator;
import com.github.puppet.filter.UserSessionUtil;
import com.webex.webapp.common.exception.WbxAppTokenException;
import com.webex.webapp.common.util.security.AppTokenUtil;

public class HttpClientUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static final String USER_TOKEN = "user_token";
	private static boolean apptoken_enabled = "true"
			.equals(ConfigurationManager.getDefaultConfig().getValue(
					"apptoken.enabled", "true"));
	private static final String appName = ConfigurationManager
			.getDefaultConfig().getValue("app.name");

	public static String getStringFromStream(InputStream input) {
		String body = null;
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream(512);
			byte[] bb = new byte[512];
			int len = 0;
			while ((len = input.read(bb)) > 0) {
				bao.write(bb, 0, len);
			}
			body = bao.toString();
		} catch (Exception e) {
			body = "";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
				}
			}
		}

		return body;
	}

	private static HttpClient singleHttpClient = null;

	protected static HttpClient getHttpClient() {
		if (singleHttpClient == null) {
			synchronized (HttpClientUtil.class) {
				if (singleHttpClient == null) {
					PoolingClientConnectionManager conMan = new PoolingClientConnectionManager(
							SchemeRegistryFactory.createDefault());
					conMan.setMaxTotal(200);
					conMan.setDefaultMaxPerRoute(200);

					singleHttpClient = new DefaultHttpClient(conMan);
				}
			}
		}
		return singleHttpClient;
	}

	public static void setHttpClient(HttpClient httpClient) {
		singleHttpClient = httpClient;
	}

	public static HttpResponse request(HttpRequestBase method,
			Map<String, String> headers, String content) throws IOException {
		if (headers != null) {
			for (Entry<String, String> e : headers.entrySet()) {
				method.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = getHttpClient().execute(method);
		if (response.getStatusLine().getStatusCode() < 300) {
			return response;
		}
		HttpEntity resEntity = response.getEntity();
		InputStream stream = resEntity.getContent();
		String resault = getStringFromStream(stream);
		StatusCode statusCode = null;
		try {
			statusCode = mapper.readValue(resault, StatusCode.class);
		} catch (Exception e) {
			RAMPAPIException exception = new RAMPAPIException("0999", resault);
			throw exception;
		}
		RAMPAPIException exception = new RAMPAPIException(statusCode);
		throw exception;
	}

	public static String convertResponseToString(HttpResponse response)
			throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			String str = getStringFromStream(instreams);
			return str;
		}
		return "";
	}

	public static void setAPPToken(Map<String, String> headers) {
		if (apptoken_enabled) {
			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			try {
				headers.put("appName", appName);
				headers.put("appToken", AppTokenUtil.makeTicket2(appName));
			} catch (WbxAppTokenException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String request(HttpRequestBase method) throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		String user_token = UserSessionUtil.getUser().getToken();
		StringValidator.verifyEmpty("mail", user_token);
		headers.put(USER_TOKEN, user_token);
		setAPPToken(headers);
		String responseStr = HttpClientUtil
				.convertResponseToString(HttpClientUtil.request(method,
						headers, null));
		return responseStr;
	}

	public static <T> T get(String url, Class<T> clazz) throws IOException {
		return mapper.readValue(request(new HttpGet(url)), clazz);
	}

	public static <T> T get(String url, TypeReference<T> tr) throws IOException {
		return mapper.readValue(request(new HttpGet(url)), tr);
	}

	public static String put(String url, Object data) throws IOException {
		HttpPut httpPut = new HttpPut(url);
		if (data != null) {
			String jsonStr = mapper.writeValueAsString(data);
			AbstractHttpEntity reqEntity = new StringEntity(jsonStr, "utf-8");
			reqEntity.setContentType("text/json");
			httpPut.setEntity(reqEntity);
		}
		return request(httpPut);
	}

	public static <T> T put(String url, Class<T> clazz, Object data)
			throws IOException {
		return mapper.readValue(put(url, data), clazz);
	}
}
