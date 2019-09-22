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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.GHRateLimitExceededException;

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
		return baseUrl + resource + (resource.contains("?") ? "&" : "?") + "client_id=" + clientId + "&client_secret="
				+ clientSecret;
	}

	private String executeGet(String resource) throws IOException {
		Request request = Request.Get(createUrl(resource));
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
		String result = executeGet("/repositories" + (lastRepoId == null ? "" : "?since=" + lastRepoId));
		GitRepository[] repos = gson.fromJson(result, GitRepository[].class);
		List<Repository> listaRepos = Arrays.stream(repos).map(repo -> new Repository(repo.getId(), repo.getName()))
				.collect(toList());
		return listaRepos;

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
