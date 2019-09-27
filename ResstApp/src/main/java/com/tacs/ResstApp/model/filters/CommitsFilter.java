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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalCommits == null) ? 0 : totalCommits.hashCode());
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
		CommitsFilter other = (CommitsFilter) obj;
		if (totalCommits == null) {
			if (other.totalCommits != null)
				return false;
		} else if (!totalCommits.equals(other.totalCommits))
			return false;
		return true;
	}
	
}
