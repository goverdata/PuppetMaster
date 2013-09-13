package com.github.puppet.ditail;

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
import com.github.puppet.domain.DITailStatus;

@Component
@Path("/servertypes/{serverTypeName}/ditails")
@Produces(value = MediaType.APPLICATION_JSON)
public class DITailController {
	private static ObjectMapper mapper = new ObjectMapper();

	@Resource(name = "ditailService")
	private DITailService service;

	@GET
	public Pagination<DITailStatus> listByServerTypeId(
			@PathParam("serverTypeName") String serverTypeName) throws IOException {
		return service.listByServerTypeId(serverTypeName);
	}

	@PUT
	@Path("/{ditailId}")
	public DITailStatus update(@PathParam("serverTypeName") String serverTypeName,
			@PathParam("ditailId") String ditailId, String jsonObject)
			throws IOException {
		DITailStatus ditail = mapper.readValue(jsonObject, DITailStatus.class);
		ditail.setId(ditailId);
		ditail.setServerTypeName(serverTypeName);
		return service.update(serverTypeName, ditailId, ditail);
	}
}
