package com.tacs.ResstApp.services.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class RepositoryMockService {

	private List<Repository> repositories;

	public RepositoryMockService() {
		Repository repo1 = new Repository(1L, "TACS");
		Repository repo2 = new Repository(2L, "TADP");
		Repository repo3 = new Repository(3L, "DDS");
		Repository repo4 = new Repository(4L, "PDP");
		Repository repo5 = new Repository(5L, "SO");
		Repository repo6 = new Repository(6L, "GDD");
		this.repositories = new ArrayList<>(Arrays.asList(repo1, repo2, repo3, repo4, repo5, repo6));
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