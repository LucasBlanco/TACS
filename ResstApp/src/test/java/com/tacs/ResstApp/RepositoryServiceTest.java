package com.tacs.ResstApp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.impl.RepositoryService;

@SpringBootTest
public class RepositoryServiceTest {

	RepositoryService repositoryService = new RepositoryService();
	Repository repo1 = new Repository(1L, "TACS");
	Repository repo2 = new Repository(2L, "TADP");
	LocalDateTime referenceTime = LocalDateTime.now();

	@BeforeEach
	public void before() throws Exception {
		repo1.setRegistrationDate(referenceTime.minusHours(2));
		repo2.setRegistrationDate(referenceTime.plusHours(2));
		List<Repository> repositories = new ArrayList<>(Arrays.asList(repo1, repo2));
		repositoryService.getRepositories().addAll(repositories);
	}

	@Test
	public void repositoriesBetween1() throws Exception {
		Assertions.assertTrue(repositoryService.getRepositoriesBetween(referenceTime.minusHours(1), referenceTime.plusHours(1)).isEmpty());
	}
	
	@Test
	public void repositoriesBetween2() throws Exception {
		Assertions.assertEquals(repo1, repositoryService.getRepositoriesBetween(referenceTime.minusHours(3), referenceTime.minusHours(1)).get(0));
	}
	
	@Test
	public void repositoriesBetween3() throws Exception {
		Assertions.assertEquals(2, repositoryService.getRepositoriesBetween(referenceTime.minusHours(3), referenceTime.plusHours(3)).size());
	}
	
	@Test
	public void repositoriesBetween4() throws Exception {
		Assertions.assertTrue(repositoryService.getRepositoriesBetween(referenceTime.minusHours(5), referenceTime.minusHours(3)).isEmpty());
	}
	
}
