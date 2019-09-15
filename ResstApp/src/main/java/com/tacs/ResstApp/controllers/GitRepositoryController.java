package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.GithubOauthService;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class GitRepositoryController {


    @Autowired
    RepositoryService repositoryMockService;

    @Autowired
    UserService userMockService;

    @GetMapping("/repositories/{id}")
    public ResponseEntity<Object> getRepository(@PathVariable Long id){
        try {
            return ResponseEntity.ok(repositoryMockService.getRepository(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/repositories")
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam("since") String since, @RequestParam("to") String to, @RequestParam("start") int start, @RequestParam("limit") int limit){
        try {
        	DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	    LocalDate sinceParsed = LocalDate.parse(since , DATEFORMATTER);
    	    LocalDate toParsed = LocalDate.parse(to , DATEFORMATTER);
            List<Repository> repos = repositoryMockService.getRepositoriesBetween(sinceParsed, toParsed);
            GitRepositoriesResponse response = new GitRepositoriesResponse(repos.size(), repos);
            return ResponseEntity.ok(response);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
    
    @GetMapping("/repositories/filters")
    public ResponseEntity<Object> getRepositoriesFiltered(@RequestParam(name="language", required = false) String language, @RequestParam(name="nofcommits",required = false) Integer nofcommits,
    		@RequestParam(name="nofstars",required = false) Integer nofstars, @RequestParam(name="nofissues",required = false) Integer nofissues, @RequestParam(name="nofsubscribers",required = false) Integer nofsubscribers) { 
    	try{
            return ResponseEntity.ok(repositoryMockService.getRepositoriesFiltered(language, nofcommits, nofstars, nofissues, nofsubscribers));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

}

class GitRepositoriesResponse{
    Integer totalAmount;
    List<Repository> repositories;

    public GitRepositoriesResponse(Integer size, List<Repository> repositories){
        this.totalAmount = size;
        this.repositories = repositories;
    }
}