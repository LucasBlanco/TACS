package com.tacs.ResstApp.model;

import java.util.List;
import java.util.Set;

public class ComparisonDTO {
	private Long favId1;
	private Long favId2;
	private List<Repository> commonRepositories;
	private Set<String> commonLanguages;
	
	public ComparisonDTO(Long favId1, Long favId2, List<Repository> commonRepositories, Set<String> commonLanguages) {
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
	public Set<String> getCommonLanguages() {
		return commonLanguages;
	}
	public void setCommonLanguages(Set<String> commonLanguages) {
		this.commonLanguages = commonLanguages;
	}
}
