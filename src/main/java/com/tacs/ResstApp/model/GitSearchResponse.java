package com.tacs.ResstApp.model;

import java.util.List;

public class GitSearchResponse {
    private List<Repository> repositories;
    private int totalRepositories;

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public int getTotalRepositories() {
        return totalRepositories;
    }

    public void setTotalRepositories(int totalRepositories) {
        this.totalRepositories = totalRepositories;
    }
}
