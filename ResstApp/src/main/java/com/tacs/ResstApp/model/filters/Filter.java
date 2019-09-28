package com.tacs.ResstApp.model.filters;

public abstract class Filter {
	public String getQueryProperty() {
		return getPropertyName() + ":" + getValue();
	}

	protected abstract String getValue();

	protected abstract String getPropertyName();
}
