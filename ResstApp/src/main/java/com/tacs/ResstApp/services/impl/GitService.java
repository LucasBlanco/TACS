package com.tacs.ResstApp.services.impl;


import com.google.gson.Gson;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.GitUser;
import com.tacs.ResstApp.model.Repository;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Component
public class GitService {

    private String baseUrl = "https://api.github.com";
    private Gson gson = new Gson();

    private String executeRequest(Request request) throws IOException {
        HttpResponse response = request//.addHeader("Authorization", "token " + GitCredentials.getToken())
                .execute()
                .returnResponse();
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }
        return EntityUtils.toString(response.getEntity());

    }

    public List<Repository> getRepositories() throws IOException {

        String result = executeRequest(Request.Get(baseUrl + "/user/repos" + GithubOauthService.getAuthentication()));
        GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
        List<Repository> listaRepos = Arrays.stream(repos)
                .map(repo -> new Repository(repo.getId(), repo.getName()))
                .collect(toList());
        return listaRepos;

    }

    public Repository getRepositoryById(String repoName) throws IOException {
        String userName = "tacsgit";//GitCredentials.getUserName();
        String result = executeRequest(Request.Get(baseUrl + "/repos/" + userName + "/" + repoName + GithubOauthService.getAuthentication()));
        GitRepository repo = gson.fromJson(result, GitRepository.class);
        return new Repository(repo.getId(), repo.getName());

    }

    public String getUserName() throws IOException {
        String userName = GitCredentials.getUserName();
        String result = executeRequest(Request.Get(baseUrl + "/user" + GithubOauthService.getAuthentication()));
        HashMap<String, String> user = gson.fromJson(result, HashMap.class);
        return user.get("login");
    }
}
