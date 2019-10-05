package com.tacs.ResstApp.model.filters;

public class ContainsWordFilter extends Filter {
	
    private String words;

    public String getName() {
    	return words;
    }
    
    public void setName(String name) {
    	this.words = name;
    }

	protected String getValue() {
		return words;
	}

	@Override
	protected String getPropertyName() {
		return null;
	}

}
