package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.List;

public class ContributorsResponse {
    List<String> contribuors = new ArrayList<>();

    public List<String> getContribuors() {
        return contribuors;
    }

    public void setContribuors(List<String> contribuors) {
        this.contribuors = contribuors;
    }
}
