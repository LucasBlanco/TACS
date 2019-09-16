package com.tacs.ResstApp.model.filters;

import com.tacs.ResstApp.model.Repository;

public class LanguageFilter implements Filter {
    private String language;

    public LanguageFilter(String language) {
        this.language = language;
    }

	@Override
	public boolean filter(Repository repository) {
		// TODO Auto-generated method stub
		return false;
	}
}
