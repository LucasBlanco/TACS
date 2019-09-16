package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class ScoreFilter implements Filter {
    private Double repositoryScore;

	public Double getRepositoryScore() {
		return repositoryScore;
	}

	public void setRepositoryScore(Double repositoryScore) {
		this.repositoryScore = repositoryScore;
	}
	
	@Override
	public boolean filter(Repository repository) {
		return repository.getScore() >= this.getRepositoryScore();
	}
}
