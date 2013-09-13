package com.github.puppet.ditail;

import java.io.IOException;

import com.github.puppet.client.Pagination;
import com.github.puppet.domain.DITailStatus;

public interface DITailService {

	Pagination<DITailStatus> listByServerTypeId(String serverTypeName)
			throws IOException;

	DITailStatus update(String serverTypeName, String ditailId,
			DITailStatus ditail) throws IOException;

}
