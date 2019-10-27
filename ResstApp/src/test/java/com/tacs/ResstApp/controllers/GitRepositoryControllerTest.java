package com.tacs.ResstApp.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tacs.ResstApp.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;

@SpringBootTest
class GitRepositoryControllerTest {

	@InjectMocks
	GitRepositoryController gitRepositoryController;

	@Mock
	RepositoryService repositoryMockService;

	@BeforeEach
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getRepositoryReturnsOneRepositorySuccessfully() throws Exception {
		String repoName = "Repo";
		String userName = "User";
		Repository repository = new Repository(1L, repoName);
		Mockito.when(repositoryMockService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenReturn(repository);

		ResponseEntity<Object> response = gitRepositoryController.getRepository(userName, repoName);
		Repository returnedRepo = (Repository) response.getBody();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1L, returnedRepo.getId());
		Assertions.assertEquals(repoName, returnedRepo.getName());
	}

	@Test
	public void getRepositoryReturnsUserError() throws Exception {
		String repoName = "Repo";
		String userName = "User";
		Mockito.when(repositoryMockService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepository(userName, repoName);
		Repository returnedRepo = (Repository) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(returnedRepo);
	}

	@Test
	public void getRepositoryReturnsServiceError() throws Exception {
		String repoName = "Repo";
		String userName = "User";
		Mockito.when(repositoryMockService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepository(userName, repoName);
		Repository returnedRepo = (Repository) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(returnedRepo);
	}

	@Test
	public void getRepositoriesByDateReturns3RepositoriesSuccessfully() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		String since = "2019-08-09";
		String to = "2019-08-09";
		Mockito.when(
				repositoryMockService.getRepositoriesBetween(
						Mockito.any(LocalDate.class),
						Mockito.any(LocalDate.class)
				)
		).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));

		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate("1", since, to, 0, 10);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		FavouritesResponse body = (FavouritesResponse) response.getBody();
		List<Repository> returnedRepos =  body.getRepositories();

		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}
	
	@Test
	public void getRepositoriesByDateReturnsUserError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class))).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate("1", "2019-08-09", "2019-08-09", 0, 10);
		FavouritesResponse body = (FavouritesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(body);
	}

	@Test
	public void getRepositoriesByDateReturnsServerError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class))).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate("1", "2019-08-09", "2019-08-09", 0, 10);
		FavouritesResponse body = (FavouritesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(body);
	}
	
	@Test
	public void getRepositoriesReturns3RepositoriesSuccessfully() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepositories(Mockito.anyString()))
			.thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));

		ResponseEntity<Object> response = gitRepositoryController.getRepositories("0");
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		GitRepositoriesResponse body = (GitRepositoriesResponse) response.getBody();
		List<Repository> returnedRepos =  body.getRepositories();

		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}
	
	@Test
	public void getRepositoriesReturnsUserError() throws Exception {
		Mockito.when(repositoryMockService.getRepositories(Mockito.anyString())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositories("1");
		GitRepositoriesResponse body = (GitRepositoriesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(body);
	}

	@Test
	public void getRepositoriesReturnsServerError() throws Exception {
		Mockito.when(repositoryMockService.getRepositories(Mockito.anyString())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositories("1");
		FavouritesResponse body = (FavouritesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(body);
	}
	
	@Test
	public void getRepositoriesFilteredReturnsCorrectRepositories() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Search search = new Search();
		GitSearchResponse mockResponse = new GitSearchResponse();
		mockResponse.setRepositories(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		Mockito.when(repositoryMockService.getRepositoriesFiltered(Mockito.any(Search.class), Mockito.anyString())).thenReturn(mockResponse);

		ResponseEntity<Object> response = gitRepositoryController.getRepositoriesFiltered(search, "");
		List<Repository> returnedRepos = ((GitSearchResponse) response.getBody()).getRepositories();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}

	@Test
	public void getRepositoriesFilteredReturnsUserError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesFiltered(Mockito.any(Search.class), Mockito.anyString())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositoriesFiltered(new Search(), "");
		GitRepositoriesResponse returnedRepos = (GitRepositoriesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(returnedRepos);
	}

	@Test
	public void getRepositoriesFilteredReturnsServerError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesFiltered(Mockito.any(Search.class), Mockito.anyString())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = gitRepositoryController.getRepositoriesFiltered(new Search(), "");
		GitRepositoriesResponse returnedRepos = (GitRepositoriesResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(returnedRepos);
	}

	@Test
	public void getRepositoriesContributorsReturns2Contributors() throws Exception {
		Repository repo = new Repository(1L, "repo 1");
		List<Contributor> contributors = new ArrayList<>();
		Contributor contributor1 = new Contributor();
		contributor1.setLogin("contributor1");
		contributors.add(contributor1);
		Contributor contributor2 = new Contributor();
		contributor2.setLogin("contributor2");
		contributors.add(contributor2);
		ContributorsResponse mockResponse = new ContributorsResponse();
		mockResponse.setContribuors(contributors);
		Mockito.when(repositoryMockService.getContributors(repo)).thenReturn(mockResponse);

		ResponseEntity<Object> response = gitRepositoryController.getContributorsFromRepo(repo);
		List<Contributor> returnedContributors = ((ContributorsResponse) response.getBody()).getContribuors();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(contributors.get(0), returnedContributors.get(0));
		Assertions.assertEquals(contributors.get(1), returnedContributors.get(1));
	}

	@Test
	public void getRepositoriesContributorsReturnsUserError() throws Exception {
		Mockito.when(repositoryMockService.getContributors(Mockito.any(Repository.class))).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = gitRepositoryController.getContributorsFromRepo(new Repository());
		ContributorsResponse returnedContributors = (ContributorsResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(returnedContributors);
	}

	@Test
	public void getRepositoriesContributorsReturnsServerError() throws Exception {
		Mockito.when(repositoryMockService.getContributors(Mockito.any(Repository.class))).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = gitRepositoryController.getContributorsFromRepo(new Repository());
		ContributorsResponse returnedContributors = (ContributorsResponse) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(returnedContributors);
	}
}
