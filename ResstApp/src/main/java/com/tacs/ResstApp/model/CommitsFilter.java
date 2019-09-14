package com.tacs.ResstApp.model;

public class CommitsFilter implements Filter {
    private Integer nofcommits;

    public CommitsFilter(Integer nofcommits) {
        this.nofcommits = nofcommits;
    }

	@Override
	public boolean filter(Repository repository) {
		return repository.getNofCommits() >= nofcommits;
	}
}
