package com.tacs.ResstApp.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @PostMapping("/login")
    public String login(){
        return "Login";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Logout";
    }

    @PostMapping("/users")
    public List<String> createUsers(){
        return Arrays.asList("Hola");
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id){
        return "Hola";
    }

    @PostMapping("/users/{id}/filters")
    public String createFilters(@PathVariable Long id){
        return "Created id "+id;
    }

    @PostMapping("/users/{id}/favourites")
    public String createFavourites(@PathVariable Long id){
        return "Created id "+id;
    }

    @PutMapping("/users/{userId}/favourites/{id}")
    public String updateFavourites(@PathVariable Long userId, @PathVariable Long id){
        return "Updated userid: "+userId+" id "+id;
    }

    @DeleteMapping("/users/{userId}/favourites/{id}")
    public String deleteFavourites(@PathVariable Long userId, @PathVariable Long id){
        return "Deleted userid: "+userId+" id "+id;
    }
}
