package com.tacs.ResstApp.model.filters;

import java.util.Optional;

public abstract class RangeFilter extends Filter {

	@Override
	protected String getValue() {
		return defaultValue(getMinValue()) + ".." + defaultValue(getMaxValue());
	}

	private String defaultValue(String value) {
		return Optional.ofNullable(value).orElse("*");
	}
	
	protected String parseInt(Integer value) {
		return value != null ? Integer.toString(value) : null;
	}

	protected abstract String getMaxValue();

	protected abstract String getMinValue();
}
