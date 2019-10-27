package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.List;

public class ContributorsResponse {
    List<Contributor> contribuors = new ArrayList<>();

    public List<Contributor> getContribuors() {
        return contribuors;
    }

    public void setContribuors(List<Contributor> contribuors) {
        this.contribuors = contribuors;
    }
}
