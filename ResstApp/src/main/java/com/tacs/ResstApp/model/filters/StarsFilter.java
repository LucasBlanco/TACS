package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class StarsFilter implements Filter {
    private Integer totalStars;

    public Integer getTotalStars() {
    	return totalStars;
    }
    
    public void setTotalStars(Integer totalStars) {
    	this.totalStars = totalStars;
    }

    @Override
    public boolean filter(Repository repository) {
        return repository.getStars() >= getTotalStars();
    }

	@Override
	public String getQueryProperty() {
		return "stars:" + Integer.toString(totalStars);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalStars == null) ? 0 : totalStars.hashCode());
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
		StarsFilter other = (StarsFilter) obj;
		if (totalStars == null) {
			if (other.totalStars != null)
				return false;
		} else if (!totalStars.equals(other.totalStars))
			return false;
		return true;
	}
	
	
}
