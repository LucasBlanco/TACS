package com.tacs.ResstApp.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryService {

	private List<Repository> repositories = new ArrayList<Repository>();

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

	public List<Repository> getRepositoriesFiltered(String language, Integer nofcommits, Integer nofstars,
			Integer nofissues) throws ServiceException {
		return repositories;
	}

}