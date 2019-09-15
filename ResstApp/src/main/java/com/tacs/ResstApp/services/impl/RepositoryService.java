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
    GitService gitService;

    @Autowired
    private RepositoryRepository repositoryRepository;

    private List<Repository> repositories = new ArrayList<Repository>();

    //para mockear
    public RepositoryService() {
        Repository repository1 = new Repository(1L, "TACS");
        Repository repository2 = new Repository(2L, "TADP");
        Repository repository3 = new Repository(3L, "DDS");
        Repository repository4 = new Repository(4L, "PDP");
        Repository repository5 = new Repository(5L, "SO");
        Repository repository6 = new Repository(6L, "GDD");
        this.repositories = new ArrayList<>(Arrays.asList(repository1, repository2, repository3, repository4, repository5, repository6));
    }

    public List<Repository> getRepositories() throws IOException {
        return repositories;
    }

    public Repository getRepository(Long id) throws ServiceException {
        Optional<Repository> repository = repositoryRepository.findById(id);
        if(repository.isPresent()){
            return repository.get();
        }
        //TODO aca se deberia buscar si el repositorio exite en github
        throw new ServiceException("Repository does not exist");
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
        return repositories;
    }

}