package com.github.common.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class RAMPAPIException extends RuntimeException {
	private static final long serialVersionUID = 6198150057805620142L;

	private static ResourceBundle rb = ResourceBundle.getBundle("errorCode");

	private String errorCode;
	private String message;
	private Throwable cause = this;

	public RAMPAPIException(String errorCode) {
		this.errorCode = errorCode;
		init(null);
	}

	public RAMPAPIException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public RAMPAPIException(StatusCode statusCode) {
		this.errorCode = statusCode.getError().getKey();
		this.message = statusCode.getError().getMessage()[0];
	}

	public RAMPAPIException(String errorCode, Throwable cause) {
		this.errorCode = errorCode;
		this.cause = cause;
		init(null);
	}

	private void init(Object[] parameters) {
		if (errorCode == null) {
			message = "unknown exception";
			return;
		}
		message = rb.getString(errorCode);
		message = MessageFormat.format(message, parameters);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getCause() {
		return (cause == this ? null : cause);
	}
}
