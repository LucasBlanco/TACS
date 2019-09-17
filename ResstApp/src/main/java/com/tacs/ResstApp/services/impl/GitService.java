package com.tacs.ResstApp.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tacs.ResstApp.model.GitRepository;
import com.tacs.ResstApp.model.Repository;
import jdk.nashorn.internal.parser.JSONParser;
import net.minidev.json.JSONObject;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

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

        Map<String, Object>[] repos = new ObjectMapper().readValue(result, HashMap[].class);
        List<Repository> listaRepos = Arrays.stream(repos)
                .map( repo -> new Repository(Long.parseLong(repo.get("id").toString()), repo.get("name").toString()).setRegistrationDate(LocalDate.parse(repo.get("created_at").toString().substring(0,10))))
                .collect(toList());

        return listaRepos;
    }
}
