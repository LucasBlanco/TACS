package com.tacs.ResstApp.services.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tacs.ResstApp.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("repos")
	public List<Repository> getRepositoriesFiltered(Search search) throws ServiceException, IOException {
		return gitService.filterBy(search);
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

    public Repository getRepository(Repository repo) throws ServiceException, IOException {
    	Optional<Repository> repository = repositoryRepository.findById(repo.getId());
    	if (repository.isPresent()) {
    		return updateRepository(repository.get());
    	} else {
    		return newFavourite(repo);
    	}
    }
    
    public Repository updateRepository(Repository repo) throws ServiceException, IOException {
    	Repository re = gitService.getRepositoryByUserRepo(repo.getOwner(), repo.getName());
    	repo.setLanguages(re.getLanguages());
    	repo.setMainLanguage(re.getMainLanguage());
    	repo.setNofForks(repo.getNofForks());
    	repo.setScore(re.getScore());
    	repo.setStars(re.getStars());
    	repo.setTotalCommits(re.getTotalCommits());
    	repo.setTotalIssues(re.getTotalIssues()); //puede q me falte algo
    	return repo;
    }
    
    public Repository newFavourite(Repository repo) throws ServiceException, IOException {
    	Repository re = gitService.getRepositoryByUserRepo(repo.getOwner(), repo.getName());
		re.setRegistrationDate(LocalDate.now());
		return re;
    }
    
    public Repository getRepositoryById(Long repoId) throws ServiceException, IOException {
    	Optional<Repository> repo =  repositoryRepository.findById(repoId);
    	if (repo.isPresent()) {
    		return repo.get();
    	} else throw new ServiceException("Repo does not exist in database");
    }

    public List<Repository> getRepositoriesBetween(LocalDate since, LocalDate to) throws ServiceException, IOException {
        //List<Repository> lista = getRepositories(null); Es sobre nuestros repositorios
        return findLocalRepositories()
                .stream()
                .filter(r -> r.getRegistrationDate().isAfter(since) && r.getRegistrationDate().isBefore(to))
                .collect(Collectors.toList());
    }

	public List<Repository> findLocalRepositories() {
		return repositoryRepository.findAll();
	}

	public void save(Repository repository) {
		repositoryRepository.save(repository);
	}

    public Repository getRepositoryByUserRepo(String username, String repoName) throws ServiceException, IOException {
        //TODO buscar en la base por owner y nombre repo, si existe llamamos al método updateRepository y sino usamos el de git directo con favs en 0
    	Repository repository = gitService.getRepositoryByUserRepo(username, repoName);
        Optional<Repository> savedRepo = repositoryRepository.findById(repository.getId());
        if(savedRepo.isPresent()) {
        	repository.setFavs(savedRepo.get().getFavs());        
        }
	    return repository;
    }
}