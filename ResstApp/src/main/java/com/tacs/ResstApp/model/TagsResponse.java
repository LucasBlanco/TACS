package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.List;

public class TagsResponse {
    List<Tag> tags = new ArrayList<>();

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}