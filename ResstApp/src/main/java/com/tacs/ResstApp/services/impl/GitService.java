package com.tacs.ResstApp.services.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
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

@Component
public class GitService {

	@Value("${github.baseUrl}")
	private String baseUrl;
	@Value("${github.clientId}")
	private String clientId;
	@Value("${github.clientSecret}")
	private String clientSecret;
	private Gson gson = new Gson();

	private String createUrl(String resource) {
		return baseUrl + resource + (resource.contains("?") ? "&" : "?") + "client_id=" + clientId + "&cliente_secret="
				+ clientSecret;
	}

	private String executeGet(String resource) throws IOException {
		Request request = Request.Get(createUrl(resource));
		HttpResponse response = request.execute().returnResponse();
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return EntityUtils.toString(response.getEntity());

	}

	public List<Repository> getRepositories() throws IOException {
		String result = executeGet("/user/repos");
		GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
		List<Repository> listaRepos = Arrays.stream(repos).map(repo -> new Repository(repo.getId(), repo.getName()))
				.collect(toList());
		return listaRepos;

	}

	public List<Repository> getUserRepositories() throws IOException {
		String result = executeGet("/users/" + "tptacs" + "/repos");
		JsonArray objArray = new JsonParser().parse(result).getAsJsonArray();
		List<Repository> repos = new ArrayList<Repository>();
		for (JsonElement el : objArray) {
			repos.add(parseRepository(el.toString()));
		}
		// List<Repository> repos = Arrays.stream(gson.fromJson(result,
		// Repository[].class)).collect(toList());
		return repos;

	}

	public Repository getRepositoryById(String repoName) throws IOException {
		String userName = "tptacs";
		String result = executeGet("/repos/" + userName + "/" + repoName);
		Repository repo = parseRepository(result);
		return repo;

	}

	public Repository parseRepository(String result) throws IOException {
		JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
		Repository repo = new Repository(obj.get("id").getAsLong(), obj.get("name").getAsString());
		repo.setMainLanguage(obj.get("language").isJsonNull() ? null : obj.get("language").getAsString());
		// TODO: Obtener este valor, "subscribers_count" no viene en la información del
		// repositorio
		// repo.setNofFaved(obj.get("subscribers_count").isJsonNull()?null:obj.get("subscribers_count").getAsInt());
		repo.setScore(obj.get("stargazers_count").isJsonNull() ? null : obj.get("stargazers_count").getAsDouble());
		repo.setNofForks(obj.get("forks_count").isJsonNull() ? null : obj.get("forks_count").getAsInt());
		repo.setTotalIssues(obj.get("open_issues_count").isJsonNull() ? null : obj.get("open_issues_count").getAsInt());

		/*
		 * if (!obj.get("commits_url").isJsonNull()) { String resultCommits =
		 * executeRequest(Request.Get(obj.get("commits_url").getAsString().replace(
		 * "{/sha}", "") + GithubOauthService.getAuthentication())); JsonArray
		 * objCommits = new JsonParser().parse(resultCommits).getAsJsonArray();
		 * repo.setTotalCommits(objCommits.size()); }
		 */

		List<String> languages = new ArrayList<String>();
		if (obj.get("languages_url") != null) {
			String resultLanguages = executeGet(obj.get("languages_url").getAsString());
			JsonObject objLanguages = new JsonParser().parse(resultLanguages).getAsJsonObject();
			for (Map.Entry<String, JsonElement> entry : objLanguages.entrySet()) {
				languages.add(entry.getKey());
			}
		}
		repo.setLanguages(languages);
		return repo;
	}
}
