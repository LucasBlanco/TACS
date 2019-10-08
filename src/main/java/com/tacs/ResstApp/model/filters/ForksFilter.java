package com.tacs.ResstApp.model.filters;

public class ForksFilter extends RangeFilter {
    private Integer minForks;
    private Integer maxForks;

    public Integer getMinForks() {
    	return minForks;
    }
    
    public void setMinForks(Integer minForks) {
    	this.minForks = minForks;
    }

	@Override
	protected String getPropertyName() {
		return "forks";
	}

	@Override
	protected String getMaxValue() {
		return parseInt(getMaxForks());
	}

	@Override
	protected String getMinValue() {
		return parseInt(getMinForks());
	}

	public Integer getMaxForks() {
		return maxForks;
	}

	public void setMaxForks(Integer maxForks) {
		this.maxForks = maxForks;
	}
}
