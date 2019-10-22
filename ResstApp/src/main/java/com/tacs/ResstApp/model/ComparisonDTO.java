package com.tacs.ResstApp.model;

import lombok.Data;

import java.util.List;

@Data
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
}