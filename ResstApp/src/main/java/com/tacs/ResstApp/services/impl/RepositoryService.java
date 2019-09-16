package com.tacs.ResstApp.services.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

    @Autowired
    private GitService gitService;

    @Autowired
    private RepositoryRepository repositoryRepository;

    //para mockear
    public RepositoryService() {
    }

    public List<Repository> getRepositories() throws IOException {
        return repositoryRepository.findAll();
    }

    public Repository getRepository(String name) throws ServiceException, IOException {
        /*Optional<Repository> repository = repositoryRepository.findById(id);
        if (repository.isPresent()) {
            return repository.get();
        }
        throw new ServiceException("Repository does not exist");
        */
        return gitService.getRepositoryById(name);
    }

    public List<Repository> getRepositoriesBetween(LocalDate since, LocalDate to) throws ServiceException, IOException {
        List<Repository> lista = gitService.getRepositories();
        return lista
                .stream()
                .filter(r -> r.getRegistrationDate().isAfter(since) && r.getRegistrationDate().isBefore(to))
                .collect(Collectors.toList());
    }

    public List<Repository> getRepositoriesFiltered(String language, Integer nofcommits, Integer nofstars,
                                                    Integer nofissues, Integer nofsubscribers) throws ServiceException {
        return repositoryRepository.findAll();
    }

}