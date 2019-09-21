package com.tacs.ResstApp.services.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.tacs.ResstApp.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.Search;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import com.tacs.ResstApp.repositories.UserRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

    @Autowired
    private GitService gitService;

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private UserRepository userRepository;

	public List<Repository> getRepositoriesFiltered(Search search) throws ServiceException, IOException {
		return search.filter(this.getRepositories(null));
	}

    public List<Repository> getRepositories(String pageId) throws ServiceException{
	    try{
            String lastRepoId = null;
            if (pageId != null){
                String decryptedPageId = CryptoUtils.decrypt(pageId);
                lastRepoId = CryptoUtils.removeLeftCharacterRepeated(decryptedPageId,'0');
            }
            return gitService.getRepositories(lastRepoId);
        }
	    catch(IOException ex){
	        throw new ServiceException(ex.getMessage());
        }
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
        List<Repository> lista = getRepositories(null);
        return lista
                .stream()
                .filter(r -> r.getRegistrationDate().isAfter(since) && r.getRegistrationDate().isBefore(to))
                .collect(Collectors.toList());
    }

	public void save(Repository repository) {
		repositoryRepository.save(repository);
	}

    public Repository getUpdatedRepository(String name) throws ServiceException, IOException {
        Repository repository = gitService.getRepositoryById(name);
        System.out.println(repository.getMainLanguage());
        List<User> users = userRepository.findByFavourites(repository);
        repository.setFavs(users.size());
	    return repository;
    }
}