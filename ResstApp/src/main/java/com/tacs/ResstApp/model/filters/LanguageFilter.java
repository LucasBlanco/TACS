package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class LanguageFilter implements Filter {
    private String mainLanguage;

    public String getMainLanguage() {
    	return mainLanguage;
    }
    
    public void setMainLanguage(String mainLanguage) {
    	this.mainLanguage = mainLanguage;
    }

    @Override
	public boolean filter(Repository repository) {
		return this.getMainLanguage().equalsIgnoreCase(repository.getMainLanguage());
	}

	@Override
	public String getQueryProperty() {
		return "language:" + mainLanguage;
	}

}
