<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="java.lang.Throwable"%>
<%@ page import="java.lang.Exception"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.slf4j.Logger"%>
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page import="org.codehaus.jackson.JsonProcessingException"%>
<%@ page import="com.github.common.exception.ErrorCode"%>
<%@ page import="com.github.common.exception.RAMPAPIException"%>
<%!private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOG = LoggerFactory
			.getLogger("com.github.exception");%>
<%
	Exception e = (Exception) request
			.getAttribute("javax.servlet.error.exception");
	Map<String, String> errorMap = new HashMap<String, String>();
	errorMap.put("result", "failed");
	if (e == null) {
		e = new RAMPAPIException(ErrorCode.COMMON_UNDEFINED_ERROR, "");
	}
	Throwable actualException = e.getCause() == null ? e : e.getCause();
	if (actualException instanceof JsonProcessingException) {
		actualException = new RAMPAPIException(
				ErrorCode.COMMON_UNDEFINED_ERROR, actualException);
	}
	String message = actualException.getMessage();
	if (message == null) {
		message = "unknown exception";
	}
	if (!(actualException instanceof RAMPAPIException)) {
		actualException = new RAMPAPIException(
				ErrorCode.COMMON_UNDEFINED_ERROR, e);
	}
	errorMap.put("errorCode",
			((RAMPAPIException) actualException).getErrorCode());
	String detailMessage = actualException.getCause() == null ? message
			: actualException.getCause().getMessage();
	// errorMap.put("detailMessage", detailMessage.trim());
	LOG.error(detailMessage, actualException);

	errorMap.put("reason", message.trim());
	response.addHeader("content-type", "text/plain");
	out.print(mapper.writeValueAsString(errorMap));
%>