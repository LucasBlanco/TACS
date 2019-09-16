package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class CommitsFilter implements Filter {
    private Integer nofcommits;

    public Integer getNofcommits() {
		return nofcommits;
	}

	public void setNofcommits(Integer nofcommits) {
		this.nofcommits = nofcommits;
	}

	@Override
	public boolean filter(Repository repository) {
		return repository.getNofCommits() >= nofcommits;
	}
}
