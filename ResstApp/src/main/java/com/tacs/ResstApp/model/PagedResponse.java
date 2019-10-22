package com.tacs.ResstApp.model;

import lombok.Data;

import java.util.List;

@Data
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
}
