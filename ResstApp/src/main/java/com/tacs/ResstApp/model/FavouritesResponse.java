package com.tacs.ResstApp.model;

import lombok.Data;

import java.util.List;

@Data
public class FavouritesResponse {
    private Integer totalAmount;
    private List<Repository> repositories;

    public FavouritesResponse(Integer size, List<Repository> repositories){
        this.totalAmount = size;
        this.repositories = repositories;
    }
}