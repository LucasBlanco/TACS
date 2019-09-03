package com.tacs.ResstApp.controllers;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.UserMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tacs.ResstApp.model.Filter;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMockService userMockService;

    @PostMapping("/login")
    public String login(){
        return "Login";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Logout";
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUsers(@RequestBody List<User> users){
		List<User> usersList = userMockService.createUsers(users);
		return ResponseEntity.ok(usersList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.getUser(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/users/{id}/filters")
    public ResponseEntity<Object> createFilters(@PathVariable Long id){    	
    	Filter filter1 = new Filter();
        Filter filter2 = new Filter();
        filter1.setCriterios(Arrays.asList("NAME", "AGE", "LANGUAGE", "OWNER", "LOCS"));
        filter2.setCriterios(Arrays.asList());
        List<Filter> filters = Arrays.asList(filter1, filter2);
		return ResponseEntity.ok(filters);
    }

    @GetMapping("/users/{id}/favourites")
    public ResponseEntity<Object> getFavourites(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.getUserFavouriteRepos(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/users/{id}/favourites")
    public ResponseEntity<Object> createFavourites(@PathVariable Long id, @RequestBody List<Repository> repositoriesToFave){
        try{
            List<Repository> favourites = userMockService.createFavourites(id, repositoriesToFave);
            return ResponseEntity.ok(favourites);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @DeleteMapping("/users/{userId}/favourites/{id}")
    public ResponseEntity<Object> deleteFavourite(@PathVariable Long userId, @PathVariable Long id){
        try{
            List<Repository> favourites = userMockService.deleteFavourite(userId, id);
            return ResponseEntity.ok(favourites);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
