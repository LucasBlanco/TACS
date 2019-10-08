package com.tacs.ResstApp.model;

import java.util.List;

public class GitRepositoriesResponse{
    private String nextPageId;
    private List<Repository> repositories;

    public GitRepositoriesResponse(List<Repository> repositories, String nextPageId){
        this.repositories = repositories;
        this.nextPageId = nextPageId;
    }

    public String getNextPageId() {
        return nextPageId;
    }

    public void setNextPageId(String nextPageId) {
        this.nextPageId = nextPageId;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}