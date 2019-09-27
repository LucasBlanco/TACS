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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mainLanguage == null) ? 0 : mainLanguage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LanguageFilter other = (LanguageFilter) obj;
		if (mainLanguage == null) {
			if (other.mainLanguage != null)
				return false;
		} else if (!mainLanguage.equals(other.mainLanguage))
			return false;
		return true;
	}

	
}
