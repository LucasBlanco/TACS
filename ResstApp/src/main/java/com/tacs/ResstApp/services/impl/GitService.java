package com.tacs.ResstApp.services.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.Repository;

@Component
public class GitService {

    private String baseUrl = "https://api.github.com";
    private Gson gson = new Gson();

    public List<Repository> getRepositories() throws IOException {
        String result = Request.Get(baseUrl + "/user/repos")
                .addHeader("Authorization", "token " + GitTokenSingleton.getToken())
                .execute()
                .returnContent()
                .asString();

        GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
        List<Repository> listaRepos = Arrays.stream(repos)
                .map( repo -> new Repository(repo.getId(), repo.getName()))
                .collect(toList());

        return listaRepos;
    }
}
