package com.tacs.ResstApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tacs.ResstApp.model.Filter;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;

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
    public ResponseEntity<Object> createUsers(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("user 1");
        user2.setUsername("user 2");
        user3.setUsername("user 3");
		List<User> userList = Arrays.asList(user1 , user2, user3);
		return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
    	User user = new User();
    	user.setUsername("name user");
        return ResponseEntity.ok(user);
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

    @PostMapping("/users/{id}/favourites")
    public ResponseEntity<Object> createFavourites(@PathVariable Long id){
    	List<Repository> repositories = Arrays.asList(new Repository("TACS"), new Repository("TADP"), new Repository("DDS"));
        return ResponseEntity.ok(repositories);
    }

    @PutMapping("/users/{userId}/favourites/{id}")
    public ResponseEntity<Object> updateFavourites(@PathVariable Long userId, @PathVariable Long id){
        return ResponseEntity.ok(new Repository("My new fav repo"));
    }
    
    @DeleteMapping("/users/{userId}/favourites/{id}")
    public ResponseEntity<Object> deleteFavourites(@PathVariable Long userId, @PathVariable Long id){
        return ResponseEntity.ok(new Repository("Removed repo"));
    }
}
