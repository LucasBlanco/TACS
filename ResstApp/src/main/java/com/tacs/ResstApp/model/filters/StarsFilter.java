package com.tacs.ResstApp.model.filters;

public class StarsFilter extends RangeFilter {
    private Integer minStars;
    private Integer maxStars;

    public Integer getMinStars() {
    	return minStars;
    }
    
    public void setMinStars(Integer minStars) {
    	this.minStars = minStars;
    }

	@Override
	protected String getMaxValue() {
		return parseInt(getMaxStars());
	}

	@Override
	protected String getMinValue() {
		return parseInt(getMinStars());
	}

	@Override
	protected String getPropertyName() {
		return "stars";
	}

	public Integer getMaxStars() {
		return maxStars;
	}

	public void setMaxStars(Integer maxStars) {
		this.maxStars = maxStars;
	}

}
