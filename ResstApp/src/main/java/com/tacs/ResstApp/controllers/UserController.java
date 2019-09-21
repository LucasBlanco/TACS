package com.tacs.ResstApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        try{
            String generatedToken = userService.login(user);
            return ResponseEntity.ok(generatedToken);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody User user){
        try{
            userService.logout(user);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Logout successful");
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        try {
            return ResponseEntity.ok(userService.getUsers());
        }
        catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUser(userId);
            userService.updateUser(user);
            return ResponseEntity.ok(user);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users/{userId}/favourites")
    public ResponseEntity<Object> getFavourites(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(userService.getUserFavouriteRepos(userId));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @PostMapping("/users/{userId}/favourites")
    public ResponseEntity<Object> addFavourite(@PathVariable Long userId, @RequestBody Repository gitRepository){
        try{
            List<Repository> favourites = userService.addFavourite(userId, gitRepository);
            return ResponseEntity.status(HttpStatus.CREATED).body(favourites);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
    
    @DeleteMapping("/users/{userId}/favourites/{repoId}")
    public ResponseEntity<Object> deleteFavourite(@PathVariable Long userId, @PathVariable Long repoId){
        try {
        	userService.deleteFavourite(userId, repoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Element from list of favourites deleted succesfully");
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }


    @GetMapping("/comparison/favourites")
    public ResponseEntity<Object> compareFavourites(@RequestParam("id1") Long id1, @RequestParam("id2") Long id2){
        try{
            return ResponseEntity.ok(userService.getFavouritesComparison(id1, id2));
        }
        catch(ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
}
