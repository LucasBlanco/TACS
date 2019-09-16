package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class IssuesFilter implements Filter {
    private Integer nofissues;

    public IssuesFilter(Integer nofissues) {
        this.nofissues = nofissues;
    }

	@Override
	public boolean filter(Repository repository) {
		// TODO Auto-generated method stub
		return false;
	}
}
