package com.tacs.ResstApp.model.filters;

public class ContainsWordFilter extends Filter {
	
    private String words;

    public String getWords() {
    	return words;
    }
    
    public void setWords(String words) {
    	this.words = words;
    }
    
    public void setName(String name) {
    	this.words = name;
    }

	protected String getValue() {
		return words.replaceAll("\\s+","+");
	}

	@Override
	protected String getPropertyName() {
		return null;
	}

}
