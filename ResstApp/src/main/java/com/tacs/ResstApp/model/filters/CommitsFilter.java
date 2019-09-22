package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class CommitsFilter implements Filter {
    private Integer totalCommits;

    public Integer getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(Integer totalCommits) {
		this.totalCommits = totalCommits;
	}

	@Override
	public boolean filter(Repository repository) {
		return repository.getTotalCommits() >= this.getTotalCommits();
	}

	@Override
	public String getQueryProperty() {
		// TODO Auto-generated method stub
		return null;
	}
}
