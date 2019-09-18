package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.model.GitRepositoriesResponse;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @GetMapping("/repositories")
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam("since") String since, @RequestParam("to") String to, @RequestParam("start") int start, @RequestParam("limit") int limit){
        try {
        	DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	    LocalDate sinceParsed = LocalDate.parse(since , DATEFORMATTER);
    	    LocalDate toParsed = LocalDate.parse(to , DATEFORMATTER);
            List<Repository> repos = repositoryService.getRepositoriesBetween(sinceParsed, toParsed);
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
