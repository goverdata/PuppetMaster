package com.github.puppet.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DITailLogGroup {
	private String id;
	private String name;// User this field to replace rawDataId
	private DITailLogInfo[] logs;
	private boolean enabled = true;

	private Map<String, String> properties; // Can have appName and category

	public DITailLogGroup() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public DITailLogInfo[] getLogs() {
		return logs;
	}

	public void setLogs(DITailLogInfo[] logs) {
		this.logs = logs;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
