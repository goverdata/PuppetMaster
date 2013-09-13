package com.github.puppet.ditailconfig;

import java.io.IOException;

import com.github.puppet.client.Pagination;
import com.github.puppet.domain.DITailConfig;

public interface ServerTypeService {
	public DITailConfig get(String id) throws IOException ;

	public Pagination<DITailConfig> listAll() throws IOException ;

	public DITailConfig update(DITailConfig serverType) throws IOException ;
}
