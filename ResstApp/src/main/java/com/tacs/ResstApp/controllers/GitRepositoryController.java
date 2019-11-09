package com.tacs.ResstApp.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tacs.ResstApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "*")
    @GetMapping("repositories")
    public ResponseEntity<Object> getRepositories(@RequestParam(value = "pageId", required = false) String pageId) {
        try {
            List<Repository> repos = repositoryService.getRepositories(pageId);
            String lastRepoId = repos.get(repos.size() - 1).getId().toString();
            String lastRepoIdFilledWithZeros = CryptoUtils.leftPadWithCharacter(lastRepoId, 9, '0');
            String nextPageId = CryptoUtils.encrypt(lastRepoIdFilledWithZeros);
            GitRepositoriesResponse response = new GitRepositoriesResponse(repos, nextPageId);
            return ResponseEntity.ok(response);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/repositories/{user}/{repoName}")
    public ResponseEntity<Object> getRepository(@PathVariable String user, @PathVariable String repoName) {
        try {
            return ResponseEntity.ok(repositoryService.getRepositoryByUserRepo(user, repoName));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/favourites")
    public ResponseEntity<Object> getRepositoryByDate(
            @RequestParam(value = "pageId", required = false) String pageId,
            @RequestParam(value = "since", required = false) String since,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam("start") int start,
            @RequestParam("limit") int limit
    ) {
        try {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate sinceParsed = since == null ? LocalDate.MIN : LocalDate.parse(since, DATEFORMATTER);
            LocalDate toParsed = to == null ? LocalDate.now() : LocalDate.parse(to, DATEFORMATTER);
            List<Repository> repos = repositoryService.getRepositoriesBetween(sinceParsed, toParsed);
            FavouritesResponse response = new FavouritesResponse(repos.size(), repos.subList(start, Math.min(limit, repos.size())));

            return ResponseEntity.ok(response);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/favourites/{name}")
    public ResponseEntity<Object> getRepositoryByName( @PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(userService.getFavouriteByName(name));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/repositories/search")
    public ResponseEntity<Object> getRepositoriesFiltered(Search search, @RequestParam(value = "page", required = false) String page) {
        try {
            GitSearchResponse response = repositoryService.getRepositoriesFiltered(search, page);
            return ResponseEntity.ok(response);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/contributors")
    public ResponseEntity<Object> getContributorsFromRepo(@RequestParam String owner, @RequestParam String reponame) {
        try {
            Repository repository = new Repository();
            repository.setOwner(owner);
            repository.setName(reponame);
            ContributorsResponse contributors = repositoryService.getContributors(repository);
            return ResponseEntity.ok(contributors);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/gitIgnoreTemplates")
    public ResponseEntity<Object> getGitIgnoreTemplates() {
        try {
            GitIgnoreTemplateResponse templates = repositoryService.getGitIgnoreTemplates();
            return ResponseEntity.ok(templates);
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
}
