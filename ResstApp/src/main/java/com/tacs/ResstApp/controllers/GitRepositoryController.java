package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.model.Search;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

import org.hibernate.annotations.MetaValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        	DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	    LocalDateTime sinceParsed = LocalDateTime.parse(since, DATEFORMATTER);
    	    LocalDateTime toParsed = LocalDateTime.parse(to, DATEFORMATTER);
            return ResponseEntity.ok(repositoryMockService.getRepositoriesBetween(sinceParsed, toParsed));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
    
    @GetMapping("/repositories/filters")
    public ResponseEntity<Object> getRepositoriesFiltered(Search search) {
    	try{
    	    return ResponseEntity.ok(repositoryMockService.getRepositoriesFiltered(search));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

}
