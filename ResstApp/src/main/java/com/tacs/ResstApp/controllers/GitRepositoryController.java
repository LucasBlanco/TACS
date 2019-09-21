package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.model.FavouritesResponse;
import com.tacs.ResstApp.model.GitRepositoriesResponse;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tacs.ResstApp.utils.CryptoUtils;
import org.hibernate.annotations.MetaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tacs.ResstApp.model.Search;


@RestController
public class GitRepositoryController {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserService userService;

    @GetMapping("repositories")
    public ResponseEntity<Object> getRepositories(@RequestParam(value="pageId", required = false) String pageId){
        try {
            List<Repository> repos = repositoryService.getRepositories(pageId);
            String lastRepoId = repos.get(repos.size()-1).getId().toString();
            String lastRepoIdFilledWithZeros = CryptoUtils.leftPadWithCharacter(lastRepoId, 9, '0');
            String nextPageId = CryptoUtils.encrypt(lastRepoIdFilledWithZeros);
            GitRepositoriesResponse response = new GitRepositoriesResponse(repos, nextPageId);
            return ResponseEntity.ok(response);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @GetMapping("/repositories/{name}")
    public ResponseEntity<Object> getRepository(@PathVariable String name){
        try {
            System.out.println(name);
            return ResponseEntity.ok(repositoryService.getUpdatedRepository(name));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @GetMapping("/repositoriesa")
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam(value="pageId", required = false) String pageId, @RequestParam(value = "since", required = false) String since, @RequestParam(value = "to", required = false) String to, @RequestParam("start") int start, @RequestParam("limit") int limit){
        try {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate sinceParsed = since == null ? LocalDate.MIN : LocalDate.parse(since , DATEFORMATTER);
            LocalDate toParsed = to == null ? LocalDate.now() : LocalDate.parse(to , DATEFORMATTER);
            List<Repository> repos = repositoryService.getRepositoriesBetween(sinceParsed, toParsed);

            FavouritesResponse response = new FavouritesResponse(repos.size(), repos.subList(start, Math.min(start + limit, repos.size())));
            return ResponseEntity.ok(response);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }
    
    @GetMapping("/repositories/search")
    public ResponseEntity<Object> getRepositoriesFiltered(Search search) {
    	try{
    	    return ResponseEntity.ok(repositoryService.getRepositoriesFiltered(search));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

}
