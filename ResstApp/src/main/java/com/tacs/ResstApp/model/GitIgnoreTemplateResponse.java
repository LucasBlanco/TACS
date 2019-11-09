package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.List;

public class GitIgnoreTemplateResponse {

    public GitIgnoreTemplateResponse(List<GitIgnoreTemplate> templates){
        this.templates = templates;
    }

    public GitIgnoreTemplateResponse(){}

    List<GitIgnoreTemplate> templates = new ArrayList<>();

    public List<GitIgnoreTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<GitIgnoreTemplate> templates) {
        this.templates = templates;
    }
}
