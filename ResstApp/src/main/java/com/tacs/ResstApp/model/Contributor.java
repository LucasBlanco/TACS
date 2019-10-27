package com.tacs.ResstApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contributor {
    private String login;
    private Long id;
    private Integer contributions;

    @JsonProperty("username")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }
}
