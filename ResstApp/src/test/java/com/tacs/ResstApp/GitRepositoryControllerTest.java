package com.tacs.ResstApp;

import com.tacs.ResstApp.controllers.GitRepositoryController;
import com.tacs.ResstApp.controllers.UserController;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.RepositoryMockService;
import com.tacs.ResstApp.services.mock.UserMockService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class GitRepositoryControllerTest {

	@InjectMocks
	GitRepositoryController gitRepositoryController;

	@Mock
	RepositoryMockService repositoryMockService;
	
    @Autowired
    UserMockService userMockService;
    
	@InjectMocks
	UserController userController;

	@BeforeEach
	public void before()  {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getRepositorySuccess() throws Exception{
		Long id = 1L;
		Repository repository = new Repository(1L, "Repo 1");
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenReturn(repository);
		ResponseEntity<Object> response = gitRepositoryController.getRepository(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Repository returnedRepo = (Repository)response.getBody();
		Assertions.assertEquals(1L, returnedRepo.getId());
		Assertions.assertEquals("Repo 1",returnedRepo.getName());
	}

	@Test
	public void getRepositoryError() throws Exception{
		Long id = 1L;
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = gitRepositoryController.getRepository(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		Repository returnedRepo = (Repository)response.getBody();
		Assertions.assertEquals(null, returnedRepo);
	}

	@Test
	public void getRepositories(){
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepositories()).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = gitRepositoryController.getRepositories();
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Repository> returnedRepos = (ArrayList)response.getBody();
		Assertions.assertEquals(3, returnedRepos.size());
		Assertions.assertEquals(1L,returnedRepos.get(0).getId());
		Assertions.assertEquals("repo 1",returnedRepos.get(0).getName());
		Assertions.assertEquals(2L,returnedRepos.get(1).getId());
		Assertions.assertEquals("repo 2",returnedRepos.get(1).getName());
		Assertions.assertEquals(3L,returnedRepos.get(2).getId());
		Assertions.assertEquals("repo 3",returnedRepos.get(2).getName());
	}
	
	@Test
	public void getUsers(){
		List<User> newUsers = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1.setId(1L);
		user1.setUsername("Jose");
		user2.setId(2L);
		user2.setUsername("Pepe");
		user3.setId(3L);
		user3.setUsername("Juan");
		newUsers.add(user1);
		newUsers.add(user2);
		Mockito.when(repositoryMockService.getUsers()).thenReturn(new ArrayList<>(Arrays.asList(user1, user2, user3)));
		ResponseEntity<Object> response = gitRepositoryController.getUsers();
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<User> updatedUsers = (ArrayList) response.getBody();
		Assertions.assertEquals(3,updatedUsers.size());
		Assertions.assertEquals(1L,updatedUsers.get(0).getId());
		Assertions.assertEquals("Jose",updatedUsers.get(0).getUsername());
		Assertions.assertEquals(2L,updatedUsers.get(1).getId());
		Assertions.assertEquals("Pepe",updatedUsers.get(1).getUsername());
		Assertions.assertEquals(3L,updatedUsers.get(2).getId());
		Assertions.assertEquals("Juan",updatedUsers.get(2).getUsername());
	}

}
