package com.tacs.ResstApp.model.filters;

public class SizeFilter extends RangeFilter {
    private Integer minRepositorySize;
    private Integer maxRepositorySize;

	public Integer getMinRepositorySize() {
		return minRepositorySize;
	}

	public void setMinRepositorySize(Integer minRepositorySize) {
		this.minRepositorySize = minRepositorySize;
	}
	
	@Override
	protected String getMaxValue() {
		return parseInt(getMaxRepositorySize());
	}

	@Override
	protected String getMinValue() {
		return parseInt(getMinRepositorySize());
	}

	@Override
	protected String getPropertyName() {
		return "size";
	}

	public Integer getMaxRepositorySize() {
		return maxRepositorySize;
	}

	public void setMaxRepositorySize(Integer maxRepositorySize) {
		this.maxRepositorySize = maxRepositorySize;
	}
	
}
