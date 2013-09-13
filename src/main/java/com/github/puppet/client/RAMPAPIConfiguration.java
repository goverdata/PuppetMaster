/**
 * 
 */
package com.github.puppet.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author KaiJian Ding
 * @since May 28, 2012
 */
public class RAMPAPIConfiguration {
	/** The properties config. */
	private Properties propertiesConfig = new Properties();

	public RAMPAPIConfiguration(String url) {
		parseConfig(url);
	}

	// we should include not change and change - two parts in one configuration
	private void parseConfig(String url){
		if (url != null) {

			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream(url); // parasoft-suppress BD.SECURITY.TDFNAMES "not an issue"
			try {
				// it is an absolute URL, then we goes into file
				if (in == null) {
					File f = new File(url);
					in = new FileInputStream(f);
				}
				if (in != null) {
					propertiesConfig.clear();
					propertiesConfig.load(in);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

	public String getValue(String configName) {
		return getValue(configName, null);
	}

	public String getValue(String configName, String defaultValue) {
		if (null == configName) {
			return defaultValue;
		}
		return propertiesConfig.getProperty(configName, defaultValue);
	}

	public Properties getProperties() {
		return propertiesConfig;
	}
}
