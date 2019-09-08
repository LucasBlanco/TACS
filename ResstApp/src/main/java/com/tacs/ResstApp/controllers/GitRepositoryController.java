package com.tacs.ResstApp.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

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
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to, @RequestParam(name = "start", required = false) Integer start, @RequestParam(name = "limit", required = false) Integer limit){
        try {
            return ResponseEntity.ok(repositoryMockService.getRepositoriesBetween(since.atStartOfDay(), to.atStartOfDay()));
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
    		@RequestParam(name="nofstars",required = false) Integer nofstars, @RequestParam(name="nofissues",required = false) Integer nofissues) { 
    	try{
            return ResponseEntity.ok(repositoryMockService.getRepositoriesFiltered(language, nofcommits, nofstars, nofissues));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
