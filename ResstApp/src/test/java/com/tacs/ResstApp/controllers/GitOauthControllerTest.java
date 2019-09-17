package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.services.impl.GitCredentials;
import com.tacs.ResstApp.services.impl.GitService;
import com.tacs.ResstApp.services.impl.GithubOauthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class GitOauthControllerTest {

    @InjectMocks
    GitOauthController gitOauthController = new GitOauthController();

    @Mock
    GithubOauthService githubOauthService = new GithubOauthService();


    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUrlReturnsCorrectUrl() throws Exception {

        Mockito.when(githubOauthService.getAuthorizationUrl()).thenReturn("unaUrl");

        ResponseEntity<Object> response = gitOauthController.getOauthAuthenticationUrl();
        HashMap<String, String> returnedUrl = (HashMap<String, String>) response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("unaUrl", returnedUrl.get("authenticationUrl"));
    }

}
