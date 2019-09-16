package com.tacs.ResstApp.services.impl;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.apis.GitHubApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Component
public class GithubOauthService {

    @Autowired
    GitService gitService;

    private final String clientId = "Iv1.10c4d35c163c35f3";
    private final String clientSecret = "f87506daf36e9c8f9239cf83a803623502c6d894";
    private final String secretState = "secret" + new Random().nextInt(999_999);
    private final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("http://localhost:8090/githubOauth/callback")
            .build(GitHubApi.instance());

    public String getAuthorizationUrl(){
        final Scanner in = new Scanner(System.in, "UTF-8");
        final String clientId = "Iv1.10c4d35c163c35f3";
        final String clientSecret = "f87506daf36e9c8f9239cf83a803623502c6d894";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .callback("http://localhost:8090/githubOauth/callback")
                .build(GitHubApi.instance());
        return service.getAuthorizationUrl(secretState);
    }

    public OAuth2AccessToken getToken(String code) throws InterruptedException, ExecutionException, IOException {
        return service.getAccessToken(code);
    }

    public void initialiceRequestsPayload(String token) throws IOException {
        GitCredentials.setToken(token);
        String userName = gitService.getUserName();
        GitCredentials.setUserName(userName);
    }

}
