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
import com.tacs.ResstApp.model.filters.SubscribersFilter;

public class Search {
    private CommitsFilter commitsFilters;
    private IssuesFilter issuesFilters;
    private LanguageFilter languageFilters;
    private ScoreFilter scoreFilters;
    private SubscribersFilter subscribersFilters;

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
    
    public SubscribersFilter getSubscribersFilters() {
    	return subscribersFilters;
    }
    
    public void setSubscribersFilters(SubscribersFilter subscribersFilters) {
    	this.subscribersFilters = subscribersFilters;
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
				.of(commitsFilters, issuesFilters, languageFilters, scoreFilters, subscribersFilters)
				.filter(Objects::nonNull);
	}

}
