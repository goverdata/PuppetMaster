/**
 * 
 */
package com.github.puppet.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KaiJian Ding
 * @since Oct 26, 2010
 */
public class ConfigurationManager {
	private static final String DEFAULT_PROPERTY_URL = "default.properties";
	private static Map<String, RAMPAPIConfiguration> cache = new HashMap<String, RAMPAPIConfiguration>();

	public static RAMPAPIConfiguration getDefaultConfig() {
		return getConfigFrom(DEFAULT_PROPERTY_URL);
	}

	public static RAMPAPIConfiguration getConfigFrom(String path) {
		if (cache.containsKey(path)) {
			return cache.get(path);
		}
		synchronized (cache) {
			if (cache.containsKey(path)) {
				return cache.get(path);
			}
			final RAMPAPIConfiguration conf = new RAMPAPIConfiguration(path);
			cache.put(path, conf);
			return cache.get(path);
		}
	}
}
