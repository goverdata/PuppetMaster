package com.github.common.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.common.security.EncryptDecrypt;

public class ClientSession {
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOG = LoggerFactory
			.getLogger(ClientSession.class);
	private static final String COOKIE_NAME = "rampweb_session";
	private Map<String, Object> sessionMap = new HashMap<String, Object>();

	private HttpServletRequest request;
	private HttpServletResponse response;

	public ClientSession(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		readFromCookie();
	}

	@SuppressWarnings("unchecked")
	private void readFromCookie() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				for (Cookie cookie : request.getCookies()) {
					if (COOKIE_NAME.equals(cookie.getName())) {
						sessionMap = mapper.readValue(
								EncryptDecrypt.decrypt(cookie.getValue()),
								Map.class);
						break;
					}
				}
			} catch (IOException e) {
				LOG.error("readFromCookie error", e);
			}
		}
		if (sessionMap == null) {
			sessionMap = new HashMap<String, Object>();
		}
		saveToSession();
	}

	private static final int HOUR = 3600;
	private static final int SECOND = 1000;
	private void saveToSession() {
		int MaxInactiveInterval = HOUR;
		if (sessionMap.get("MaxInactiveInterval") != null) {
			MaxInactiveInterval = (Integer) sessionMap.get("MaxInactiveInterval");
		}
		long lastAccessedTime = 0;
		long now = System.currentTimeMillis();
		if (sessionMap.get("lastAccessedTime") == null) {
			lastAccessedTime = now;
		} else {
			lastAccessedTime = (Long) sessionMap.get("lastAccessedTime");
		}
		if (now - lastAccessedTime > (long) MaxInactiveInterval * SECOND) {
			expire();
			return;
		}
		for (Entry<String, Object> kv : sessionMap.entrySet()) {
			request.setAttribute(kv.getKey(), kv.getValue());
		}
	}

	private void storeToCookie() {
		int MaxInactiveInterval = -1;
		if (sessionMap.containsKey("MaxInactiveInterval")) {
			MaxInactiveInterval = (Integer) sessionMap.get("MaxInactiveInterval");
		}
		try {
			Cookie cookie = new Cookie(
					COOKIE_NAME,
					EncryptDecrypt.encrypt(mapper.writeValueAsBytes(sessionMap)));
			cookie.setMaxAge(MaxInactiveInterval);
//			String contextPath = request.getContextPath().isEmpty() ? "/" : request.getContextPath();
			cookie.setPath("/");
			if (!"127.0.0.1".equalsIgnoreCase(request.getRemoteAddr())
					&& !"0:0:0:0:0:0:0:1".equalsIgnoreCase(request.getRemoteAddr())) {
				// FIXME remove it if you want to commit the codes
				//cookie.setSecure(true);
			}
			response.addCookie(cookie);
		} catch (IOException e) {
			LOG.error("storeToCookie error", e);
		}
	}

	public void saveNew() {
		sessionMap.put("user_token", request.getAttribute("user_token"));
		sessionMap.put("userMail", request.getAttribute("userMail"));
		sessionMap.put("userName", request.getAttribute("userName"));
		renewAccessTime();
	}

	public void renewAccessTime() {
		sessionMap.put("lastAccessedTime", System.currentTimeMillis());
		storeToCookie();
	}

	public void expire() {
		sessionMap.clear();
		storeToCookie();
	}
}
