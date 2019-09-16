package com.tacs.ResstApp.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tacs.ResstApp.model.*;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

	private List<Repository> repositories = new ArrayList<Repository>();
	
	//para mockear
    public RepositoryService() {
        Repository repository1 = new Repository(1L,"TACS");
        repository1.setNofCommits(20);
        Repository repository2 = new Repository(2L,"TADP");
        Repository repository3 = new Repository(3L,"DDS");
        Repository repository4 = new Repository(4L,"PDP");
        Repository repository5 = new Repository(5L,"SO");
        Repository repository6 = new Repository(6L,"GDD");
        this.repositories = new ArrayList<>(Arrays.asList(repository1, repository2, repository3, repository4, repository5, repository6));
    }

	public List<Repository> getRepositories() {
		return repositories;
	}

	public Repository getRepository(Long id) throws ServiceException {
		return repositories.stream().filter(r -> r.getId() == id).findFirst()
				.orElseThrow(() -> new ServiceException("Repository does not exist"));
	}

	public List<Repository> getRepositoriesBetween(LocalDateTime since, LocalDateTime to) throws ServiceException {
		return repositories.stream()
				.filter(r -> r.getRegistrationDate().isAfter(since) && r.getRegistrationDate().isBefore(to))
				.collect(Collectors.toList());
	}

	public List<Repository> getRepositoriesFiltered(Search search) throws ServiceException {
		return search.filter(this.getRepositories());
	}

}