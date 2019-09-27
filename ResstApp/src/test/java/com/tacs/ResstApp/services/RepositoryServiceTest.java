package com.tacs.ResstApp.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import com.tacs.ResstApp.services.impl.GitService;
import com.tacs.ResstApp.services.impl.RepositoryService;

@SpringBootTest
public class RepositoryServiceTest {

	@InjectMocks
	RepositoryService repositoryService = new RepositoryService();
	@Mock
	RepositoryRepository repositoryRepository;

	private List<Repository> repositories;
	private Repository repository1 = new Repository(1L, "TACS");
	private Repository repository2 = new Repository(2L, "TADP");
	private LocalDate referenceTime = LocalDate.now();

	@BeforeEach
	public void before() throws Exception {
		repository1.setRegistrationDate(referenceTime.minusDays(2));
		repository2.setRegistrationDate(referenceTime.plusDays(2));
		this.repositories = Arrays.asList(repository1, repository2);
		MockitoAnnotations.initMocks(this);
		Mockito.when(repositoryRepository.findAll()).thenReturn(this.repositories);
	}

	@Test
    public void repositoryServiceDoesNotReturnAnyRepositoriesInOpenIntervalBetweenRepositories() throws Exception {
        List<Repository> repositoriesInOpenInterval = 
        		repositoryService.getRepositoriesBetween(referenceTime.minusDays(1),referenceTime.plusDays(1));
		Assertions.assertThat(repositoriesInOpenInterval).isEmpty();
    }

	@Test
	public void repositoryServiceReturnsRepository1InIntervalIncludingOnlyThatRepository() throws Exception {
		List<Repository> repositoriesInIntervalContainingRepository1 = 
				repositoryService.getRepositoriesBetween(referenceTime.minusDays(3), referenceTime.plusDays(1));
		Assertions.assertThat(repositoriesInIntervalContainingRepository1).containsExactly(repository1);
	}

	@Test
	public void repositoryServiceReturnsBothRepositoriesInIntervalIncludingThem() throws Exception {
		List<Repository> repositoriesInClosedInterval = 
				repositoryService.getRepositoriesBetween(referenceTime.minusDays(3), referenceTime.plusDays(3));
		Assertions.assertThat(repositoriesInClosedInterval).containsExactly(repository1, repository2);
	}

	@Test
	public void repositoryServiceDoesNotReturnAnyRepositoriesInExternalInterval() throws Exception {
		List<Repository> repositoriesInExternalInterval = 
				repositoryService.getRepositoriesBetween(referenceTime.plusDays(5), referenceTime.plusDays(7));
		Assertions.assertThat(repositoriesInExternalInterval).isEmpty();
	}

}
