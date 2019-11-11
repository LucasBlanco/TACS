package com.tacs.ResstApp.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {
	private Long id;
	private String name;
	private String zipball_url;
	private String tarball_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getZipball_Url() {
        return zipball_url;
    }

    public void setZipball_Url(String zipball_url) {
        this.zipball_url = zipball_url;
    }
    
    public String getTarball_Url() {
        return tarball_url;
    }

    public void setTarball_Url(String tarball_url) {
        this.tarball_url = tarball_url;
    }
    
 
}
