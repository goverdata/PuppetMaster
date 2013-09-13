package com.github.puppet.ditailconfig;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.github.puppet.client.ConfigManagerAPIClient;
import com.github.puppet.client.Pagination;
import com.github.puppet.domain.DITailConfig;

@Service("serverTypeService")
public class ServerTypeServiceImpl implements ServerTypeService {

	@Override
	public DITailConfig get(String id) throws IOException {
		 return ConfigManagerAPIClient.getServerType(id);
	}

	@Override
	public Pagination<DITailConfig> listAll() throws IOException {
		 return ConfigManagerAPIClient.listServerType();
	}

	@Override
	public DITailConfig update(DITailConfig serverType) throws IOException {
		 return ConfigManagerAPIClient.editServerType(serverType);
	}

}
