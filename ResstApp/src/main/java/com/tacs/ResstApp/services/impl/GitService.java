package com.tacs.ResstApp.services.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.Search;
import com.tacs.ResstApp.services.exceptions.GHRateLimitExceededException;

@Component
public class GitService {
	
	@Value("${github.baseUrl}")
    private String baseUrl;
    private Gson gson = new Gson();
	@Value("${github.clientId}")
	private String clientId;
	@Value("${github.clientSecret}")
	private String clientSecret;

    public Repository getRepositoryByUserRepo(String username, String repoName) throws IOException {
    	System.out.println(createUrl("/repos/" + username + "/" + repoName));
    	String result = executeGet(createUrl("/repos/" + username + "/" + repoName));
        Repository repo = parseRepository(result);
        repo.setOwner(username);
        return repo;
    }

	public Repository parseRepository(String result) throws IOException {
		JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
        Repository repo = new Repository(obj.get("id").getAsLong(), obj.get("name").getAsString());
        repo.setMainLanguage(obj.get("language").isJsonNull()?null:obj.get("language").getAsString());
        JsonElement score = obj.get("score");
		repo.setScore(score == null || score.isJsonNull()?0:Double.valueOf(score.getAsString()));
        repo.setNofForks(obj.get("forks_count").isJsonNull()?null:obj.get("forks_count").getAsInt());
        repo.setTotalIssues(obj.get("open_issues_count").isJsonNull()?null:obj.get("open_issues_count").getAsInt());
        JsonElement stars = obj.get("stargazers_count");
		repo.setStars(stars.isJsonNull()?0:stars.getAsInt());
        repo.setSize(obj.get("size").isJsonNull()?null:obj.get("size").getAsInt());
        
        /*
        if (!obj.get("commits_url").isJsonNull()) {
	        String resultCommits = executeRequest(Request.Get(obj.get("commits_url").getAsString().replace("{/sha}", "") + GithubOauthService.getAuthentication()));
	        JsonArray objCommits = new JsonParser().parse(resultCommits).getAsJsonArray();
	        repo.setTotalCommits(objCommits.size());
	    }*/
        
        repo.setMainLanguage(obj.get("language").isJsonNull()?null:obj.get("language").getAsString());
        
        List<String> languages = new ArrayList<String>();
        if (obj.get("languages_url") != null) {
	        String languageUri = obj.get("languages_url").getAsString();
			String resultLanguages = executeGet(languageUri);
	        JsonObject objLanguages = new JsonParser().parse(resultLanguages).getAsJsonObject();
	        for(Map.Entry<String, JsonElement> entry : objLanguages.entrySet()) {
	        	languages.add(entry.getKey());
	        }
        }
        
        JsonObject owner = obj.get("owner").isJsonNull() ? null : obj.get("owner").getAsJsonObject();
        repo.setOwner(owner == null ? null : owner.get("login").getAsString());
        
        repo.setLanguages(languages);
		return repo;
	}

	public List<Repository> filterBy(Search search) throws IOException {
		List<String> queries = search.buildGitSearchQuery();
		String uri = "/search/repositories?q=" + concatQueries(queries);
		System.out.println(uri);
		String executeRequest = this.executeGet(createUrl(uri));
		JsonObject response = new JsonParser().parse(executeRequest).getAsJsonObject();
		JsonArray items = response.get("items").getAsJsonArray();
        List<Repository> repos = new ArrayList<Repository>();
        for (JsonElement el : items) {
            repos.add(parseRepository(el.toString()));
        }
        return repos;
	}

	private String concatQueries(List<String> queries) {
		return String.join("+", queries);
	}

	private String createUrl(String resource) {
		return baseUrl + resource;
	}
	
	private String authenticationTrailer(String resource) {
		return (resource.contains("?") ? "&" : "?") + "client_id=" + clientId + "&client_secret=" + clientSecret;
	}

	private String executeGet(String url) throws IOException {
		Request request = Request.Get(url + authenticationTrailer(url));
		HttpResponse response = request.execute().returnResponse();
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() == 403) {
			Long resetTimeInSeconds = Long.valueOf(response.getFirstHeader("X-RateLimit-Reset").getValue());
			Long minutes = (resetTimeInSeconds - Instant.now().atZone(ZoneOffset.UTC).toInstant().getEpochSecond())
					/ 60;
			throw new GHRateLimitExceededException(minutes);
		} else if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return EntityUtils.toString(response.getEntity());

	}

	public List<Repository> getRepositories(String lastRepoId) throws IOException {
		String result = executeGet(createUrl("/repositories" + (lastRepoId == null ? "" : "?since=" + lastRepoId)));
		GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
		List<Repository> listaRepos = Arrays.stream(repos).map(repo -> new Repository(repo.getId(), repo.getName()))
				.collect(toList());
		return listaRepos;

	}

}
