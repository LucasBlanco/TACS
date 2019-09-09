package com.tacs.ResstApp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tacs.ResstApp.controllers.GitRepositoryController;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;

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
	public void getRepositorySuccess() throws Exception {
		Long id = 1L;
		Repository repository = new Repository(1L, "Repo 1");
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenReturn(repository);
		ResponseEntity<Object> response = gitRepositoryController.getRepository(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Repository returnedRepo = (Repository) response.getBody();
		Assertions.assertEquals(1L, returnedRepo.getId());
		Assertions.assertEquals(returnedRepo.getName(), "Repo 1");
	}

	@Test
	public void getRepositoryError() throws Exception {
		Long id = 1L;
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = gitRepositoryController.getRepository(id);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Repository returnedRepo = (Repository) response.getBody();
		Assertions.assertNull(returnedRepo);
	}

	/*@Test
	public void getRepositories() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		LocalDateTime since = LocalDateTime.now();
		LocalDateTime to = LocalDateTime.now();
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate(since, to, 1, 1);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<Repository> returnedRepos = (List) response.getBody();
		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}
	
	@Test
	public void getRepositoriesError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate(LocalDateTime.now(), LocalDateTime.now(), 1, 1);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		List<Repository> returnedRepos = (List) response.getBody();
		Assertions.assertNull(returnedRepos);
	}*/
	
	@Test
	public void getRepositories() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		String since = "2019-08-09 00:00:00";
		String to = "2019-08-09 23:59:59";
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate(since, to, 1, 1);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<Repository> returnedRepos = (List) response.getBody();
		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}
	
	@Test
	public void getRepositoriesError() throws Exception {
		Mockito.when(repositoryMockService.getRepositoriesBetween(Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = gitRepositoryController.getRepositoryByDate("2019-08-09 00:00:00", "2019-08-09 23:59:59", 1, 1);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		List<Repository> returnedRepos = (List) response.getBody();
		Assertions.assertNull(returnedRepos);
	}	
	
	
	@Test
	public void getRepositoriesFiltered() throws Exception {
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepositoriesFiltered(Mockito.any(String.class), Mockito.any(Integer.class),
				Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = gitRepositoryController.getRepositoriesFiltered("spanish",1,1,1);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<Repository> returnedRepos = (List) response.getBody();
		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L, returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1", returnedRepos.get(0).getName());
		Assertions.assertEquals(2L, returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2", returnedRepos.get(1).getName());
		Assertions.assertEquals(3L, returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3", returnedRepos.get(2).getName());
	}


}
