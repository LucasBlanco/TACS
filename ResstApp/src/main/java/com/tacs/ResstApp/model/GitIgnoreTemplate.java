package com.tacs.ResstApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitIgnoreTemplate {
    private String name;
    private String download_url;

    public GitIgnoreTemplate(String name, String download_url){
        this.name = name;
        this.download_url = download_url;
    }

    public GitIgnoreTemplate(){}

    public String getName() {
        return name;
    }

    public String getDownloadUrl() {
        return download_url;
    }
}
