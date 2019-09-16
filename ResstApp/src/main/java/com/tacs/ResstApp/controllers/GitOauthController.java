package com.tacs.ResstApp.controllers;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.tacs.ResstApp.services.impl.GithubOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class GitOauthController {

    @Autowired
    GithubOauthService githubOauthService;

    /*
    TODO: 1) Desde el front (www.tacs.com/github/login) se deberia hacer un get a http:localhost:8090/githubOauth/authorizationUrl que retorna una url
          2) La url se abre en el explorador
          3) El usuario da su permiso y github redirecciona al front (www.tacs.com/github/code) con un query param ?code
          4) El front muestra una pantalla de exito y hace un get al back enviando el code
          4) Con el code, el back hace un post a github y retorna el token al front
          5) Se guarda el token en el localstorage, que sera usado en el header de todas las futuras request
          5.1) Se guarda en la base el token encriptado asociado a un usuario?????
    */

    @GetMapping("/githubOauth/authorizationUrl")
    public ResponseEntity getOauthAuthenticationUrl() throws InterruptedException, ExecutionException, IOException {
        String authorizationUrl = githubOauthService.getAuthorizationUrl();
        Map<String, String> authentication = new HashMap<String, String>();
        authentication.put("authenticationUrl", authorizationUrl);
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/githubOauth/callback")
    public ResponseEntity getOauthCallback(@RequestParam(name="code") String code) throws InterruptedException, ExecutionException, IOException {
        OAuth2AccessToken token = githubOauthService.getToken(code);
        githubOauthService.initialiceRequestsPayload(token.getAccessToken());
        return ResponseEntity.ok("Token "+ token.getAccessToken() +" guardado con exito!");
    }
}
