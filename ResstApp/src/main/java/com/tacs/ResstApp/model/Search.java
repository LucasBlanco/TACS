package com.tacs.ResstApp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Search {
    private List<CommitsFilter> commitsFilters;
    private List<IssuesFilter> issuesFilters;
    private List<LanguageFilter> languageFilters;
    private List<StarsFilter> starsFilters;
    private List<SubscribersFilter> subscribersFilters;

    public List<CommitsFilter> getCommitsFilters() {
        return commitsFilters;
    }

    public void setCommitsFilters(List<CommitsFilter> commitsFilters) {
        this.commitsFilters = commitsFilters;
    }

    public List<IssuesFilter> getIssuesFilters() {
        return issuesFilters;
    }

    public void setIssuesFilters(List<IssuesFilter> issuesFilters) {
        this.issuesFilters = issuesFilters;
    }

    public List<LanguageFilter> getLanguageFilters() {
        return languageFilters;
    }

    public void setLanguageFilters(List<LanguageFilter> languageFilters) {
        this.languageFilters = languageFilters;
    }

    public List<StarsFilter> getStarsFilters() {
        return starsFilters;
    }

    public void setStarsFilters(List<StarsFilter> starsFilters) {
        this.starsFilters = starsFilters;
    }

    public List<SubscribersFilter> getSubscribersFilters() {
        return subscribersFilters;
    }

    public void setSubscribersFilters(List<SubscribersFilter> subscribersFilters) {
        this.subscribersFilters = subscribersFilters;
    }

	public List<Repository> filter(List<Repository> repositories) {
		return repositories.stream().filter(r -> validateByFilters(r)).collect(Collectors.toList());
	}

	private boolean validateByFilters(Repository repository) {
		return this.getFiltersAttributes()
				.allMatch(a -> a.stream().allMatch(f -> f.filter(repository)));
	}

	private Stream<List<? extends Filter>> getFiltersAttributes() {
		return Stream.of(commitsFilters, issuesFilters, languageFilters, starsFilters, subscribersFilters)
				.filter(Objects::nonNull);
	}
}
