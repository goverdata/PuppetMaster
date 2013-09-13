<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.codehaus.jackson.map.ObjectMapper"%>
<%!
	private static ObjectMapper mapper = new ObjectMapper();
%>
<%
	Map<String, String> errorMap = new HashMap<String, String>();
	errorMap.put("result", "failed");
	errorMap.put("errorCode", "404");
	errorMap.put("detailMessage", "path not found");
	errorMap.put("reason", "path not found");
	response.addHeader("content-type","text/plain");
	out.print(mapper.writeValueAsString(errorMap));
%>