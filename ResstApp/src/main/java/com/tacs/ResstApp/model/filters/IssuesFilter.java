package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class IssuesFilter implements Filter {
    private Integer totalIssues;

    public Integer getTotalIssues() {
    	return totalIssues;
    }
    
    public void setTotalIssues(Integer totalIssues) {
    	this.totalIssues = totalIssues;
    }

	@Override
	public boolean filter(Repository repository) {
		return repository.getTotalIssues() >= this.getTotalIssues(); 
	}
}
