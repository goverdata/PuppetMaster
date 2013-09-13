package com.github.puppet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.github.common.exception.ErrorCode;
import com.github.common.exception.StatusCode;
import com.github.common.security.ClientSession;

public class AuthorizeFilter implements Filter {
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Pragma", "No-cache");

		String reUrl = req.getRequestURI();
		if ((req.getContextPath() + "/configmanager/user/login").equals(reUrl)
				|| (req.getContextPath() + "/configmanager/user/logout")
						.equals(reUrl)) {
			chain.doFilter(req, resp);
			return;
		}

		new ClientSession(req, resp);

		User user = new User();
		user.setUserMail((String) req.getAttribute("userMail"));
		user.setUserName((String) req.getAttribute("userName"));
		user.setToken((String) req.getAttribute("userMail"));

		UserSessionUtil.setUser(user);
		String userMail = user.getUserMail();
		if (userMail == null || userMail.isEmpty()) {
			if (!reUrl.endsWith("html")
					&& !reUrl.equals(req.getContextPath() + "/")) {
				StatusCode statusCode = new StatusCode(
						ErrorCode.COMMON_NOT_LOGIN, "Please login first");
				resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
				resp.getWriter().print(mapper.writeValueAsString(statusCode));
				return;
			}
			resp.sendRedirect("login.htm");
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
	}
}
