package com.github.puppet.client;


public class RAMPAPIClient {
	private static String rampAPIUrl = ConfigurationManager.getDefaultConfig()
			.getValue("rampapi.url");

	static {
		if (rampAPIUrl.endsWith("/")) {
			rampAPIUrl = rampAPIUrl.substring(0, rampAPIUrl.length() - 1);
		}
	}
}
