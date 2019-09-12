package com.tacs.ResstApp.model;

import java.util.List;

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
}
