package com.github.puppet.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DITailConfig {
	private String name;
	private String[] flumeServers;
	private long speedLimit;
//	private boolean enabled = true;
	private DITailLogGroup[] groups;

	public DITailConfig() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFlumeServers() {
		return flumeServers;
	}

	public void setFlumeServers(String[] flumeServers) {
		this.flumeServers = flumeServers;
	}

	public long getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(long speedLimit) {
		this.speedLimit = speedLimit;
	}

	public DITailLogGroup[] getGroups() {
		return groups;
	}

	public void setGroups(DITailLogGroup[] groups) {
		this.groups = groups;
	}

}
