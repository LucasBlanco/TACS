package com.tacs.ResstApp.model.filters;

public abstract class Filter { //voy a tener un property filter que no va a tener un value.
	public String getQueryProperty() {
		if(getPropertyName()!=null) {
			return getPropertyName() + ":" + getValue();
		} else
		System.out.println(getValue());
		return getValue();
	}

	protected abstract String getValue();

	protected abstract String getPropertyName();
}
