package com.github.puppet.client;

import java.io.IOException;

import org.codehaus.jackson.type.TypeReference;

import com.github.puppet.domain.DITailConfig;
import com.github.puppet.domain.DITailStatus;

public class ConfigManagerAPIClient {
	private static String configManagerAPIUrl = ConfigurationManager
			.getDefaultConfig().getValue("dimanager.url");

	private static String listServerTypesPath = ConfigurationManager
			.getDefaultConfig().getValue("list.servertype.url");
	private static String getServerTypesPath = ConfigurationManager
			.getDefaultConfig().getValue("get.servertype.url");
	private static String editServerTypesPath = ConfigurationManager
			.getDefaultConfig().getValue("edit.servertype.url");

	private static String listDITailPath = ConfigurationManager
			.getDefaultConfig().getValue("list.ditail.url");
	private static String updateDITailPath = ConfigurationManager
			.getDefaultConfig().getValue("update.ditail.url");

	static {
		if (configManagerAPIUrl.endsWith("/")) {
			configManagerAPIUrl = configManagerAPIUrl.substring(0,
					configManagerAPIUrl.length() - 1);
		}
	}

	public static Pagination<DITailConfig> listServerType() throws IOException {
		String url = configManagerAPIUrl + listServerTypesPath;
		return HttpClientUtil.get(url,
				new TypeReference<Pagination<DITailConfig>>() {
				});
	}

	public static DITailConfig getServerType(String serverTypeId)
			throws IOException {
		String url = configManagerAPIUrl
				+ String.format(getServerTypesPath, serverTypeId);
		return HttpClientUtil.get(url, DITailConfig.class);
	}

	public static DITailConfig editServerType(DITailConfig serverType)
			throws IOException {
		String url = configManagerAPIUrl
				+ String.format(editServerTypesPath, serverType.getName());
		return HttpClientUtil.put(url, DITailConfig.class, serverType);
	}

	public static Pagination<DITailStatus> listDITail(String serverTypeName)
			throws IOException {
		String url = configManagerAPIUrl
				+ String.format(listDITailPath, serverTypeName);
		return HttpClientUtil.get(url,
				new TypeReference<Pagination<DITailStatus>>() {
				});
	}

	public static DITailStatus updateDITail(String serverTypeName,
			String ditailId, DITailStatus ditail) throws IOException {
		String url = configManagerAPIUrl
				+ String.format(updateDITailPath, serverTypeName, ditailId);
		return HttpClientUtil.put(url, DITailStatus.class, ditail);
	}
}
