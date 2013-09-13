package com.github.puppet.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DITailLogInfo {
	private String dir;
	private String pattern;
	private String beginLine;
	private String endLine;

	public DITailLogInfo() {
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getBeginLine() {
		return beginLine;
	}

	public void setBeginLine(String beginLine) {
		this.beginLine = beginLine;
	}

	public String getEndLine() {
		return endLine;
	}

	public void setEndLine(String endLine) {
		this.endLine = endLine;
	}
}
