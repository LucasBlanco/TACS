package com.tacs.ResstApp.model;

import lombok.Data;

import java.util.List;

@Data
public class GitRepositoriesResponse{
    private String nextPageId;
    private List<Repository> repositories;

    public GitRepositoriesResponse(List<Repository> repositories, String nextPageId){
        this.repositories = repositories;
        this.nextPageId = nextPageId;
    }
}