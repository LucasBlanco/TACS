package com.tacs.ResstApp.model.filters;

public class LanguageFilter extends Filter { //
    private String mainLanguage;

    public String getMainLanguage() {
    	return mainLanguage;
    }
    
    public void setMainLanguage(String mainLanguage) {
    	this.mainLanguage = mainLanguage;
    }

	protected String getValue() {
		return mainLanguage;
	}

	@Override
	protected String getPropertyName() {
		return "language";
	}

	
}
