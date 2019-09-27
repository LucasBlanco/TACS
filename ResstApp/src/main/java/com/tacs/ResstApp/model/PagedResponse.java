package com.tacs.ResstApp.model;

import java.util.List;

public class PagedResponse<T> {
	
	private Integer totalCount;
	private Integer page;
	private Integer pageSize;
	private List<T> list;
	
	public PagedResponse(Integer page, Integer pageSize, List<T> list) {
		this.totalCount = list.size();
		this.page = page==null?1:page;
		this.pageSize = pageSize==null?totalCount:pageSize;
		int fromIndex = this.pageSize * (this.page - 1);
		int toIndex = this.pageSize * this.page;
		if (toIndex > totalCount) {
			toIndex = totalCount;
		}
			
		this.list = list.subList(fromIndex, toIndex);
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
