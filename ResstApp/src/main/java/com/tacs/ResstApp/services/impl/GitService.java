package com.tacs.ResstApp.services.impl;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String userName = "tptacs";//GitCredentials.getUserName();
        String result = executeRequest(Request.Get(baseUrl + "/repos/" + userName + "/" + repoName + GithubOauthService.getAuthentication()));
        Repository repo = parseRepository(result);
        return repo;

    }

	private Repository parseRepository(String result) throws IOException {
		JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
        Repository repo = new Repository(obj.get("id").getAsLong(), obj.get("name").getAsString());
        repo.setMainLanguage(obj.get("language").isJsonNull()?null:obj.get("language").getAsString());
        repo.setNofFaved(obj.get("subscribers_count").isJsonNull()?null:obj.get("subscribers_count").getAsInt());
        repo.setScore(obj.get("stargazers_count").isJsonNull()?null:obj.get("stargazers_count").getAsDouble());
        repo.setNofForks(obj.get("forks_count").isJsonNull()?null:obj.get("forks_count").getAsInt());
        repo.setTotalIssues(obj.get("open_issues_count").isJsonNull()?null:obj.get("open_issues_count").getAsInt());
        
        /*String resultCommits = executeRequest(Request.Get(obj.get("commits_url").getAsString().replace("{/sha}", "") + GithubOauthService.getAuthentication()));
        JsonArray objCommits = new JsonParser().parse(resultCommits).getAsJsonArray();
        repo.setTotalCommits(objCommits.size());*/
        
        String resultLanguages = executeRequest(Request.Get(obj.get("languages_url").getAsString() + GithubOauthService.getAuthentication()));
        JsonObject objLanguages = new JsonParser().parse(resultLanguages).getAsJsonObject();
        List<String> languages = new ArrayList<String>();
        for(Map.Entry<String, JsonElement> entry : objLanguages.entrySet()) {
        	languages.add(entry.getKey());
        }
        repo.setLanguages(languages);
		return repo;
	}

    public String getUserName() throws IOException {
        String userName = GitCredentials.getUserName();
        String result = executeRequest(Request.Get(baseUrl + "/user" + GithubOauthService.getAuthentication()));
        HashMap<String, String> user = gson.fromJson(result, HashMap.class);
        return user.get("login");
    }
}
