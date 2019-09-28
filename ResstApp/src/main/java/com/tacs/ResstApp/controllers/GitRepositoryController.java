package com.tacs.ResstApp.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tacs.ResstApp.model.FavouritesResponse;
import com.tacs.ResstApp.model.GitRepositoriesResponse;
import com.tacs.ResstApp.model.PagedResponse;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.Search;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;
import com.tacs.ResstApp.utils.CryptoUtils;


@RestController
public class GitRepositoryController {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("repositories")
    public ResponseEntity<Object> getRepositories(@RequestParam(value="pageId", required = false) String pageId){
        try {
            List<Repository> repos = repositoryService.getRepositories(pageId);
            String lastRepoId = repos.get(repos.size()-1).getId().toString();
            String lastRepoIdFilledWithZeros = CryptoUtils.leftPadWithCharacter(lastRepoId, 9, '0');
            String nextPageId = CryptoUtils.encrypt(lastRepoIdFilledWithZeros);
            GitRepositoriesResponse response = new GitRepositoriesResponse(repos, nextPageId);
            return ResponseEntity.ok(response);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/repositories/{user}/{repoName}")
	public ResponseEntity<Object> getRepository(@PathVariable String user, @PathVariable String repoName){
        try {
            return ResponseEntity.ok(repositoryService.getRepositoryByUserRepo(user, repoName));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/favourites")
    public ResponseEntity<Object> getRepositoryByDate(@RequestParam(value="pageId", required = false) String pageId, @RequestParam(value = "since", required = false) String since, @RequestParam(value = "to", required = false) String to, @RequestParam("start") int start, @RequestParam("limit") int limit){
        try {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate sinceParsed = since == null ? LocalDate.MIN : LocalDate.parse(since , DATEFORMATTER);
            LocalDate toParsed = to == null ? LocalDate.now() : LocalDate.parse(to , DATEFORMATTER);
            List<Repository> repos = repositoryService.getRepositoriesBetween(sinceParsed, toParsed);

            FavouritesResponse response = new FavouritesResponse(repos.size(), repos.subList(start, Math.min(start + limit, repos.size())));
            return ResponseEntity.ok(response);
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/repositories/search")
    public ResponseEntity<Object> getRepositoriesFiltered(Search search, @RequestParam(value="lastId", required = false) String lastId) {
    	try{
    	    List<Repository> repositoriesFiltered = repositoryService.getRepositoriesFiltered(search, lastId);
			return ResponseEntity.ok(new GitRepositoriesResponse(repositoriesFiltered, repositoryService.getNextPageId(repositoriesFiltered)));
        }
        catch(ServiceException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

}
