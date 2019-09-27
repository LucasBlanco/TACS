package com.tacs.ResstApp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tacs.ResstApp.model.filters.CommitsFilter;
import com.tacs.ResstApp.model.filters.Filter;
import com.tacs.ResstApp.model.filters.IssuesFilter;
import com.tacs.ResstApp.model.filters.LanguageFilter;
import com.tacs.ResstApp.model.filters.ScoreFilter;
import com.tacs.ResstApp.model.filters.StarsFilter;

public class Search {
    private CommitsFilter commitsFilters;
    private IssuesFilter issuesFilters;
    private LanguageFilter languageFilters;
    private ScoreFilter scoreFilters;
    private StarsFilter starsFilters;

    public CommitsFilter getCommitsFilters() {
    	return commitsFilters;
    }
    
    public void setCommitsFilters(CommitsFilter commitsFilters) {
    	this.commitsFilters = commitsFilters;
    }
    
    public IssuesFilter getIssuesFilters() {
    	return issuesFilters;
    }
    
    public void setIssuesFilters(IssuesFilter issuesFilters) {
    	this.issuesFilters = issuesFilters;
    }
    
    public LanguageFilter getLanguageFilters() {
    	return languageFilters;
    }
    
    public void setLanguageFilters(LanguageFilter languageFilters) {
    	this.languageFilters = languageFilters;
    }
    
    public ScoreFilter getScoreFilters() {
    	return scoreFilters;
    }
    
    public void setScoreFilters(ScoreFilter scoreFilters) {
    	this.scoreFilters = scoreFilters;
    }
    
    public StarsFilter getStarsFilters() {
    	return starsFilters;
    }
    
    public void setStarsFilters(StarsFilter starsFilters) {
    	this.starsFilters = starsFilters;
    }

	public List<Repository> filter(List<Repository> repositories) {
		return repositories.stream().filter(r -> validateByFilters(r)).collect(Collectors.toList());
	}

	private boolean validateByFilters(Repository repository) {
		return this.getFiltersAttributes()
				.allMatch(f -> f.filter(repository));
	}

	private Stream<Filter> getFiltersAttributes() {
		return Stream
				.of(commitsFilters, issuesFilters, languageFilters, scoreFilters, starsFilters)
				.filter(Objects::nonNull);
	}

	public List<String> buildGitSearchQuery() {
		return this.getFiltersAttributes().map(f -> f.getQueryProperty()).collect(Collectors.toList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commitsFilters == null) ? 0 : commitsFilters.hashCode());
		result = prime * result + ((issuesFilters == null) ? 0 : issuesFilters.hashCode());
		result = prime * result + ((languageFilters == null) ? 0 : languageFilters.hashCode());
		result = prime * result + ((scoreFilters == null) ? 0 : scoreFilters.hashCode());
		result = prime * result + ((starsFilters == null) ? 0 : starsFilters.hashCode());
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
		if (commitsFilters == null) {
			if (other.commitsFilters != null)
				return false;
		} else if (!commitsFilters.equals(other.commitsFilters))
			return false;
		if (issuesFilters == null) {
			if (other.issuesFilters != null)
				return false;
		} else if (!issuesFilters.equals(other.issuesFilters))
			return false;
		if (languageFilters == null) {
			if (other.languageFilters != null)
				return false;
		} else if (!languageFilters.equals(other.languageFilters))
			return false;
		if (scoreFilters == null) {
			if (other.scoreFilters != null)
				return false;
		} else if (!scoreFilters.equals(other.scoreFilters))
			return false;
		if (starsFilters == null) {
			if (other.starsFilters != null)
				return false;
		} else if (!starsFilters.equals(other.starsFilters))
			return false;
		return true;
	}

}
