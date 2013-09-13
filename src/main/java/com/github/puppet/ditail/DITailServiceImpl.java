package com.github.puppet.ditail;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.github.puppet.client.ConfigManagerAPIClient;
import com.github.puppet.client.Pagination;
import com.github.puppet.domain.DITailStatus;

@Service("ditailService")
public class DITailServiceImpl implements DITailService {

	@Override
	public Pagination<DITailStatus> listByServerTypeId(String serverTypeId)
			throws IOException {
		 return ConfigManagerAPIClient.listDITail(serverTypeId);
	}

	@Override
	public DITailStatus update(String serverTypeId, String ditailId,
			DITailStatus ditail) throws IOException {
		 return ConfigManagerAPIClient.updateDITail(serverTypeId, ditailId, ditail);
	}
}
