package com.github.puppet.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DITailStatus {
	private String id;
	private String hostname;
	private String dcId;
	private String status;
	private String serverTypeName; // ditail report to api
	// private String serverType;
	private long speedLimit; // DITail do not have to send this field
	private boolean enabled;
	private DITailHeartBeat[] heartBeats;

	public DITailStatus() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getServerTypeName() {
		return serverTypeName;
	}

	public void setServerTypeName(String serverTypeName) {
		this.serverTypeName = serverTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(long speedLimit) {
		this.speedLimit = speedLimit;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public DITailHeartBeat[] getHeartBeats() {
		return heartBeats;
	}

	public void setHeartBeats(DITailHeartBeat[] heartBeats) {
		this.heartBeats = heartBeats;
	}
}
