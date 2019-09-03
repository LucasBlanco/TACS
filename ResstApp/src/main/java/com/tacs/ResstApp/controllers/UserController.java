package com.tacs.ResstApp.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.tacs.ResstApp.services.mock.LoggerMockService;
import com.tacs.ResstApp.services.mock.RepositoryMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.UserMockService;

@RestController
public class UserController {

    @Autowired
    UserMockService userMockService;

    @Autowired
    RepositoryMockService repositoryMockService;

    @Autowired
    LoggerMockService loggerMockService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        try{
            userMockService.getUser(user.getId());
            return ResponseEntity.ok("e2385gf54875a");
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

    }

    @PostMapping("/logout")
    @DeleteMapping("/logout/{token}")
    public ResponseEntity logout(@PathVariable String token){
        try{
            loggerMockService.logout(token);
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
    public ResponseEntity<Object> createUsers(@RequestBody User user) {
        try {
            User createdUser = userMockService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return ResponseEntity.ok(userMockService.getUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.getUser(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users/{id}/favourites")
    public ResponseEntity<Object> getFavourites(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.getUserFavouriteRepos(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/users/{userId}/favourites/{id}")
    public ResponseEntity<Object> getFavouriteById(@PathVariable Long userId, @PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.getUserFavouriteRepoById(userId, id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/users/{id}/favourites")

    public ResponseEntity<Object> createFavourite(@PathVariable Long id, @RequestBody Long repositoryToFaveId){
        try{
            Repository repositoryToFave = repositoryMockService.getRepository(repositoryToFaveId);
            List<Repository> favourites = userMockService.createFavourite(id, repositoryToFave);
            return ResponseEntity.status(HttpStatus.CREATED).body(favourites);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
    
    @DeleteMapping("/users/{userId}/favourites/{id}")
    public ResponseEntity<Object> deleteFavourite(@PathVariable Long userId, @PathVariable Long id){
        try{
            userMockService.deleteFavourite(userId, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Element from list of favourites deleted succesfully");
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/repositories/{id}")
    public ResponseEntity<Object> getRepository(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userMockService.findRepository(id));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @GetMapping("/repositories")
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin){
        try {
            return ResponseEntity.ok(userMockService.getRepositoriesBetween(inicio, fin));
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
            List<Repository> favourites1 = userMockService.getUserFavouriteRepos(id1);
            List<Repository> favourites2 = userMockService.getUserFavouriteRepos(id2);
            return ResponseEntity.ok(favourites1);
        }
        catch(ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
