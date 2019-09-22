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
}
