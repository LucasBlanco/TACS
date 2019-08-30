package com.tacs.ResstApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<String> getUsers(){
        return Arrays.asList("Hola");
    }

    @GetMapping("/users/{id}")
    public String getUserById(){
        return "Hola";
    }
}
