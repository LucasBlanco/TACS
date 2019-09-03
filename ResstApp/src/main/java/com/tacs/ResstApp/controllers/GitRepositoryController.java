package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.RepositoryMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class GitRepositoryController {


    @Autowired
    RepositoryMockService repositoryMockService;

    @GetMapping("/repositories/{id}")
    public ResponseEntity<Object> getRepository(@PathVariable Long id){
        try{
            return ResponseEntity.ok(repositoryMockService.getRepository(id));
        }
        catch(ServiceException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/repositories")
    public ResponseEntity<Object> getRepositories(@RequestParam("since") String since, @RequestParam("to") String to){
        try{
            Date sinceDate = new SimpleDateFormat("ddMMyyyy").parse(since);
            Date toDate = new SimpleDateFormat("ddMMyyyy").parse(to);
            return ResponseEntity.ok(repositoryMockService.getRepositories(sinceDate, toDate));
        }
        catch(ParseException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
