package com.tacs.ResstApp.services.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tacs.ResstApp.model.*;
import com.tacs.ResstApp.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.repositories.RepositoryRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

    @Autowired
    private GitService gitService;

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Cacheable("repos")
	public GitSearchResponse getRepositoriesFiltered(Search search, String page) throws ServiceException, IOException {
		GitSearchResponse gitSearch = gitService.filterBy(search, page);
		addNumberOfFavourites(gitSearch.getRepositories());
    	return gitSearch;
	}

    public List<Repository> getRepositories(String pageId) throws ServiceException{
	    try{
            String lastRepoId = null;
            if (pageId != null){
                String decryptedPageId = CryptoUtils.decrypt(pageId);
                lastRepoId = CryptoUtils.removeLeftCharacterRepeated(decryptedPageId,'0');
            }
            List<Repository> repositories = gitService.getRepositories(lastRepoId);
            addNumberOfFavourites(repositories);
            return repositories;
        }
	    catch(IOException ex){
	        throw new ServiceException(ex.getMessage());
        }
    }
    

    private void addNumberOfFavourites(List<Repository> repositories) {
		repositories.forEach(r -> addNFavs(r));
	}

	private void addNFavs(Repository r) {
		if (repositoryRepository.existsById(r.getId())) {
			r.setFavs(repositoryRepository.findById(r.getId()).get().getFavs());
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
    	repo.setSize(re.getSize());
    	repo.setStars(re.getStars());
    	repo.setTotalIssues(re.getTotalIssues());
    	return repo;
    }
    
    public Repository newFavourite(Repository repo) throws ServiceException, IOException {
    	Repository re = gitService.getRepositoryByUserRepo(repo.getOwner(), repo.getName());
		re.setRegistrationDate(LocalDate.now());
		return re;
    }
    
    public Repository getRepositoryForDelete(Long repoId) throws ServiceException, IOException {
    	Optional<Repository> repo =  repositoryRepository.findById(repoId);
    	if (repo.isPresent()) {
    		return repo.get();
    	} else {
    		throw new ServiceException("Repo does not exist in database");
    	}
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

	public ContributorsResponse getContributors(Repository repository) throws ServiceException, IOException {
		try{
			List<Contributor> contributors = gitService.getContributorsByUserRepo(repository.getOwner(), repository.getName());
			ContributorsResponse response = new ContributorsResponse();
			response.setContribuors(contributors);
			return response;
		} catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	public GitIgnoreTemplateResponse getGitIgnoreTemplates() throws ServiceException, IOException {
		try{
			List<GitIgnoreTemplate> templates = gitService.getGitIgnoreTemplates();
			GitIgnoreTemplateResponse response = new GitIgnoreTemplateResponse();
			response.setTemplates(templates);
			return response;
		} catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	public CommitsResponse getCommits(Repository repository) throws ServiceException {
		try{
			List<Commit> commits = gitService.getCommitsByUserRepo(repository.getOwner(), repository.getName());
			CommitsResponse response = new CommitsResponse();
			response.setCommits(commits);
			return response;
		} catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}
}