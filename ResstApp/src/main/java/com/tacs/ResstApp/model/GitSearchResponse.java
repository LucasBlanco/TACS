package com.tacs.ResstApp.model;

import lombok.Data;

import java.util.List;

@Data
public class GitSearchResponse {
    private List<Repository> repositories;
    private int totalRepositories;
}
