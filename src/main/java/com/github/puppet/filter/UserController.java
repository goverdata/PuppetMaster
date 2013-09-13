package com.github.puppet.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.common.exception.ErrorCode;
import com.github.common.exception.RAMPAPIException;
import com.github.common.security.ClientSession;
import com.github.common.security.StringValidator;

@Path("/user")
@Produces(value = MediaType.APPLICATION_JSON)
public class UserController {
	@POST
	@Path("/login")
	public User login(@FormParam("userMail") String userMail,
			@FormParam("password") String password,
			@javax.ws.rs.core.Context HttpServletRequest request,
			@javax.ws.rs.core.Context HttpServletResponse response) {
		ClientSession clientSession = new ClientSession(request, response);
		StringValidator.verifyEmpty("userMail", userMail);
		StringValidator.verifyEmpty("password", password);
		User user = LDAPLogin.login(userMail, password);
		if (user != null) {
			request.setAttribute("user_token", user.getUserMail());
			request.setAttribute("userMail", user.getUserMail());
			request.setAttribute("userName", user.getUserName());
			clientSession.saveNew();
			return user;
		}
		throw new RAMPAPIException(ErrorCode.COMMON_NOT_LOGIN);
	}

	@POST
	@Path("/logout")
	public void logout(@javax.ws.rs.core.Context HttpServletRequest request,
			@javax.ws.rs.core.Context HttpServletResponse response) {
		new ClientSession(request, response).expire();
	}

	@GET
	@Path("/info")
	public User info() {
		return UserSessionUtil.getUser();
	}
}
