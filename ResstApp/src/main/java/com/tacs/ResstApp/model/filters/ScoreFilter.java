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

	@Override
	public String getQueryProperty() {
		return "score:" + Double.toString(repositoryScore);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((repositoryScore == null) ? 0 : repositoryScore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreFilter other = (ScoreFilter) obj;
		if (repositoryScore == null) {
			if (other.repositoryScore != null)
				return false;
		} else if (!repositoryScore.equals(other.repositoryScore))
			return false;
		return true;
	}
	
	
}
