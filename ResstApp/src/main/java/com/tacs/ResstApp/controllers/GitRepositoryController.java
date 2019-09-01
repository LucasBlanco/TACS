package com.tacs.ResstApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitRepositoryController {

    @GetMapping("/repositories/{id}")
    public String getRepository(@PathVariable Long id){
        return "Repository id "+id;
    }
    
    @GetMapping("/repositories")
    public String getRepository(){
        return "Repositories";
    }
}
