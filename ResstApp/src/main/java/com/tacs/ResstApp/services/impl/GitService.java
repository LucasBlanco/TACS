package com.tacs.ResstApp.services.impl;

import com.google.gson.Gson;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.Repository;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GitService {


    private String baseUrl = "https://api.github.com";
    private Gson gson = new Gson();

    private String executeRequest(Request request) throws IOException {
        return request.addHeader("Authorization", "token " + GitTokenSingleton.getToken())
                .execute()
                .returnContent()
                .asString();
    }

    public List<Repository> getRepositories() throws IOException {
        String result = executeRequest(Request.Get(baseUrl + "/user/repos"));
        GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
        List<Repository> listaRepos = Arrays.stream(repos)
                .map(repo -> new Repository(repo.getId(), repo.getName()))
                .collect(toList());
        return listaRepos;
    }

    public Repository getRepositoryById(Long idUser, Long idRepo) throws IOException {
        String result = executeRequest(Request.Get(baseUrl + "/" + idUser + "/" + idRepo));
        GitRepository repo = gson.fromJson(result, GitRepository.class);
        return new Repository(repo.getId(), repo.getName());
    }
}
