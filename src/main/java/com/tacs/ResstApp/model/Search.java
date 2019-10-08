package com.tacs.ResstApp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tacs.ResstApp.model.filters.ContainsWordFilter;
import com.tacs.ResstApp.model.filters.Filter;
import com.tacs.ResstApp.model.filters.ForksFilter;
import com.tacs.ResstApp.model.filters.LanguageFilter;
import com.tacs.ResstApp.model.filters.SizeFilter;
import com.tacs.ResstApp.model.filters.StarsFilter;

public class Search {
    private ForksFilter forksFilter;
    private LanguageFilter languageFilter;
    private SizeFilter sizeFilter;
    private StarsFilter starsFilter;
    private ContainsWordFilter containsWordFilter;

    public ForksFilter getForksFilter() {
    	return forksFilter;
    }
    
    public void setForksFilter(ForksFilter forksFilter) {
    	this.forksFilter = forksFilter;
    }
    
    public LanguageFilter getLanguageFilter() {
    	return languageFilter;
    }
    
    public void setLanguageFilter(LanguageFilter languageFilter) {
    	this.languageFilter = languageFilter;
    }
    
    public SizeFilter getSizeFilter() {
    	return sizeFilter;
    }
    
    public void setSizeFilter(SizeFilter sizeFilter) {
    	this.sizeFilter = sizeFilter;
    }
    
    public StarsFilter getStarsFilter() {
    	return starsFilter;
    }
    
    public void setContainsWordFilter(ContainsWordFilter containsWordFilter) {
    	this.containsWordFilter = containsWordFilter;
    }
    
    public ContainsWordFilter getContainsWordFilter() {
    	return this.containsWordFilter;
    }
    
    public void setStarsFilter(StarsFilter starsFilter) {
    	this.starsFilter = starsFilter;
    }

	private Stream<Filter> getFiltersAttributes() {
		return Stream
				.of(containsWordFilter, forksFilter, languageFilter, sizeFilter, starsFilter)
				.filter(Objects::nonNull);
	}

	public List<String> buildGitSearchQuery() {
		return this.getFiltersAttributes().map(f -> f.getQueryProperty()).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forksFilter == null) ? 0 : forksFilter.hashCode());
		result = prime * result + ((languageFilter == null) ? 0 : languageFilter.hashCode());
		result = prime * result + ((sizeFilter == null) ? 0 : sizeFilter.hashCode());
		result = prime * result + ((starsFilter == null) ? 0 : starsFilter.hashCode());
		result = prime * result + ((containsWordFilter == null) ? 0 : containsWordFilter.hashCode());
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
		Search other = (Search) obj;
		if (forksFilter == null) {
			if (other.forksFilter != null)
				return false;
		} else if (!forksFilter.equals(other.forksFilter))
			return false;
		if (languageFilter == null) {
			if (other.languageFilter != null)
				return false;
		} else if (!languageFilter.equals(other.languageFilter))
			return false;
		if (sizeFilter == null) {
			if (other.sizeFilter != null)
				return false;
		} else if (!sizeFilter.equals(other.sizeFilter))
			return false;
		if (starsFilter == null) {
			if (other.starsFilter != null)
				return false;
		} else if (!starsFilter.equals(other.starsFilter))
			return false;
		if (containsWordFilter == null) {
			if (other.containsWordFilter != null)
				return false;
		} else if (!containsWordFilter.equals(other.containsWordFilter))
			return false;
		return true;
	}

}
