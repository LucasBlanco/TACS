package com.tacs.ResstApp.model.filters;

public class NameFilter extends Filter {
	
    private String name;

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

	protected String getValue() {
		return name;
	}

	@Override
	protected String getPropertyName() {
		return "name";
	}

}
