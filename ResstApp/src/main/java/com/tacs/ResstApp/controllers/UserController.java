package com.tacs.ResstApp.controllers;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity logout(@PathVariable String token){
        try{
            userMockService.logout(token);
            return (ResponseEntity) ResponseEntity.noContent();
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUsers(@RequestBody User user){
        try{
            List<User> usersList = userMockService.createUser(user);
            return ResponseEntity.ok(usersList);
        }
		catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

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

    @PostMapping("/users/{id}/favourites")
    public ResponseEntity<Object> createFavourites(@PathVariable Long userId, @RequestBody Long repositoryId){
        try{
            List<Repository> favourites = userMockService.addFavourite(userId, repositoryId);
            return ResponseEntity.ok(favourites);
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
            List<Repository> favourites = userMockService.deleteFavourite(userId, id);
            return ResponseEntity.ok(favourites);
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
}
