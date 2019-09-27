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

	@Override
	public String getQueryProperty() {
		return "issues:" + Integer.toString(totalIssues);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalIssues == null) ? 0 : totalIssues.hashCode());
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
		IssuesFilter other = (IssuesFilter) obj;
		if (totalIssues == null) {
			if (other.totalIssues != null)
				return false;
		} else if (!totalIssues.equals(other.totalIssues))
			return false;
		return true;
	}
	
	
}
