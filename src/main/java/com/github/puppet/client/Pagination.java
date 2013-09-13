package com.github.puppet.client;

import java.util.List;

public class Pagination<T> {
	public Pagination() {
	}

	private int totalCount;
	private List<T> entries;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getEntries() {
		return entries;
	}

	public void setEntries(List<T> entries) {
		this.entries = entries;
	}
}
