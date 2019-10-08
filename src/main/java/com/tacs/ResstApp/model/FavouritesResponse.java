package com.tacs.ResstApp.model;

import java.util.List;

public class FavouritesResponse {
    private Integer totalAmount;
    private List<Repository> repositories;

    public FavouritesResponse(Integer size, List<Repository> repositories){
        this.totalAmount = size;
        this.repositories = repositories;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}