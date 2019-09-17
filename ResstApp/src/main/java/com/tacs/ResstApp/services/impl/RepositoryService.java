package com.tacs.ResstApp.services.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.Search;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

    @Autowired
    private GitService gitService;

    @Autowired
    private RepositoryRepository repositoryRepository;

	public List<Repository> getRepositoriesFiltered(Search search) throws ServiceException, IOException {
		return search.filter(this.getRepositories());
	}

    public List<Repository> getRepositories() throws IOException {
        return gitService.getUserRepositories();
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

	public void save(Repository repository) {
		repositoryRepository.save(repository);
	}

}