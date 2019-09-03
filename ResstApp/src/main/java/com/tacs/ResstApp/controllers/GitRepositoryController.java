package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.RepositoryMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> getRepositories(){
        return ResponseEntity.ok(repositoryMockService.getRepositories());
    }
}
