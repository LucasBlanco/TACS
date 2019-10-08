package com.tacs.ResstApp.model;

import java.util.List;

public class ComparisonDTO {
	private Long favId1;
	private Long favId2;
	private List<Repository> commonRepositories;
	private List<String> commonLanguages;
	
	public ComparisonDTO(Long favId1, Long favId2, List<Repository> commonRepositories, List<String> commonLanguages) {
		super();
		this.favId1 = favId1;
		this.favId2 = favId2;
		this.commonRepositories = commonRepositories;
		this.commonLanguages = commonLanguages;
	}
	
	public Long getFavId1() {
		return favId1;
	}
	public void setFavId1(Long favId1) {
		this.favId1 = favId1;
	}
	public Long getFavId2() {
		return favId2;
	}
	public void setFavId2(Long favId2) {
		this.favId2 = favId2;
	}
	public List<Repository> getCommonRepositories() {
		return commonRepositories;
	}
	public void setCommonRepositories(List<Repository> commonRepositories) {
		this.commonRepositories = commonRepositories;
	}
	public List<String> getCommonLanguages() {
		return commonLanguages;
	}
	public void setCommonLanguages(List<String> commonLanguages) {
		this.commonLanguages = commonLanguages;
	}
}
