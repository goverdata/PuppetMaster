package com.github.common.exception;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StatusCode {
	public class Error {
		private String key;
		private String[] message;

		public Error() {
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String[] getMessage() {
			return message;
		}

		public void setMessage(String[] message) {
			this.message = message;
		}
	}

	private static ThreadLocal<String> trackingIdLocal = new ThreadLocal<String>();

	private Error error;

	public StatusCode() {
	}

	public StatusCode(String key, String reason) {
		error = new Error();
		error.setKey(key);
		error.setMessage(new String[] { reason });
	}

	public Error getError() {
		return error;
	}

	@JsonProperty
	public static String getTrackingId() {
		return trackingIdLocal.get();
	}

	public static void setTrackingId(String trackingId) {
		trackingIdLocal.set(trackingId);
	}
}
