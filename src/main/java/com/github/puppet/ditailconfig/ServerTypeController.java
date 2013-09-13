package com.github.puppet.ditailconfig;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.github.puppet.client.Pagination;
import com.github.puppet.domain.DITailConfig;

@Component
@Path("/servertypes")
@Produces(value = MediaType.APPLICATION_JSON)
public class ServerTypeController {
	private static ObjectMapper mapper = new ObjectMapper();

	@Resource(name = "serverTypeService")
	private ServerTypeService service;

	@GET
	@Path("/{serverTypeName}")
	public DITailConfig get(@PathParam("serverTypeName") String serverTypeName) throws IOException {
		return service.get(serverTypeName);
	}

	@GET
	public Pagination<DITailConfig> listAll() throws IOException {
		return service.listAll();
	}

	@PUT
	@Path("/{serverTypeName}")
	public DITailConfig update(@PathParam("serverTypeName") String serverTypeName, String jsonObject)
			throws IOException {
		DITailConfig serverType = mapper.readValue(jsonObject, DITailConfig.class);
		serverType.setName(serverTypeName);
		return service.update(serverType);
	}
}
