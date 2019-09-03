package com.tacs.ResstApp;

import com.tacs.ResstApp.controllers.UserController;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.mock.LoggerMockService;
import com.tacs.ResstApp.services.mock.RepositoryMockService;
import com.tacs.ResstApp.services.mock.UserMockService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserMockService userMockService;

	@Mock
	RepositoryMockService repositoryMockService;

	@Mock
	LoggerMockService loggerMockService;

	@BeforeEach
	public void before()  {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void login(){
		User user = new User();
		user.setId(1L);
		user.setUsername("Pedro");
		Mockito.when(loggerMockService.login()).thenReturn("soyeltoken");
		ResponseEntity<Object> response = userController.login(user);
		String token = (String) response.getBody();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("e2385gf54875a", token);

	}

	@Test
	public void logout() throws Exception{
		String token = "soyeltoken";
		Mockito.doNothing().when(loggerMockService).logout(Mockito.anyString());
		ResponseEntity<Object> response = userController.logout(token);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Assertions.assertEquals("Logout successful", response.getBody());
	}

	@Test
	public void getUsers() throws Exception{
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
		Mockito.when(userMockService.getUsers()).thenReturn(new ArrayList<>(Arrays.asList(user1, user2, user3)));
		ResponseEntity<Object> response = userController.getUsers();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		List<User> updatedUsers = (ArrayList) response.getBody();
		Assertions.assertEquals(3,updatedUsers.size());
		Assertions.assertEquals(1L,updatedUsers.get(0).getId());
		Assertions.assertEquals("Jose",updatedUsers.get(0).getUsername());
		Assertions.assertEquals(2L,updatedUsers.get(1).getId());
		Assertions.assertEquals("Pepe",updatedUsers.get(1).getUsername());
		Assertions.assertEquals(3L,updatedUsers.get(2).getId());
		Assertions.assertEquals("Juan",updatedUsers.get(2).getUsername());
	}

	@Test
	public void createUser() throws Exception{
		User user = new User();
		user.setId(1L);
		user.setUsername("Jose");
		Mockito.when(userMockService.createUser(Mockito.any(User.class))).thenReturn(user);
		ResponseEntity<Object> response = userController.createUser(user);
		Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
		User createdUser = (User) response.getBody();
		Assertions.assertEquals(1L,createdUser.getId());
		Assertions.assertEquals("Jose", createdUser.getUsername());
	}

	@Test
	public void getUserByIdSuccess() throws Exception{
		Long id = 1L;
		User user = new User();
		user.setId(1L);
		user.setUsername("Maria");
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenReturn(user);
		ResponseEntity<Object> response = userController.getUserById(id);
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
		User returnedUser = (User)response.getBody();
		Assertions.assertEquals(1L, returnedUser.getId());
		Assertions.assertEquals("Maria",returnedUser.getUsername());
		Assertions.assertEquals(new ArrayList<>(),returnedUser.getFavourites());
	}

	@Test
	public void getUserByIdError() throws Exception{
		Long id = 1L;
		User user = new User();
		user.setId(1L);
		user.setUsername("Maria");
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.getUserById(id);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		User returnedUser = (User)response.getBody();
		Assertions.assertEquals(null, returnedUser);
	}

	@Test
	public void getFavouritesSuccess() throws Exception{
		Long id = 1L;
		Repository repo = new Repository(1L, "repo 1");
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenReturn(new ArrayList<>(Arrays.asList(repo)));
		ResponseEntity<Object> response = userController.getFavourites(id);
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
		List<Repository> favourites = (ArrayList)response.getBody();
		Assertions.assertEquals(1, favourites.size());
		Assertions.assertEquals(1L, favourites.get(0).getId());
		Assertions.assertEquals("repo 1",favourites.get(0).getName());
	}

	@Test
	public void getFavouritesError() throws Exception{
		Long id = 1L;
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.getFavourites(id);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		List<Repository> returnedFaves = (ArrayList)response.getBody();
		Assertions.assertEquals(null, returnedFaves);
	}

	@Test
	public void getFavouriteByIdSuccess() throws Exception{
		Long userId = 1L;
		Long id = 22L;
		Repository repo = new Repository(22L, "repo 1");
		Mockito.when(userMockService.getUserFavouriteRepoById(Mockito.anyLong(),Mockito.anyLong())).thenReturn(repo);
		ResponseEntity<Object> response = userController.getFavouriteById(userId,id);
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
		Repository favourite = (Repository)response.getBody();
		Assertions.assertEquals(22L, favourite.getId());
		Assertions.assertEquals("repo 1",favourite.getName());
	}

	@Test
	public void getFavouriteByIdError() throws Exception{
		Long userId = 1L;
		Long id = 22L;
		Mockito.when(userMockService.getUserFavouriteRepoById(Mockito.anyLong(),Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.getFavouriteById(userId,id);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		Repository returnedFave = (Repository)response.getBody();
		Assertions.assertEquals(null, returnedFave);
	}

	@Test
	public void createFavouritesSucess() throws Exception{
		Long id = 1L;
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenReturn(repo3);
		Mockito.when(userMockService.createFavourite(Mockito.anyLong(), Mockito.any(Repository.class))).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = userController.createFavourite(id,3L);
		Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(3,favourites.size());
		Assertions.assertEquals(1L,favourites.get(0).getId());
		Assertions.assertEquals("repo 1",favourites.get(0).getName());
		Assertions.assertEquals(2L,favourites.get(1).getId());
		Assertions.assertEquals("repo 2",favourites.get(1).getName());
		Assertions.assertEquals(3L,favourites.get(2).getId());
		Assertions.assertEquals("repo 3",favourites.get(2).getName());
	}

	@Test
	public void createFavouritesError() throws Exception{
		Long id = 1L;
		Long repositoryIdToFave = 3L;
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenReturn(repo3);
		Mockito.when(userMockService.createFavourite(Mockito.anyLong(), Mockito.any(Repository.class))).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.createFavourite(id,repositoryIdToFave);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(null,favourites);
	}

	@Test
	public void deleteFavouriteSucess() throws Exception{
		Long userId = 1L;
		Long repoId = 22L;
		Mockito.doNothing().when(userMockService).deleteFavourite(Mockito.anyLong(), Mockito.anyLong());
		ResponseEntity<Object> response = userController.deleteFavourite(userId,repoId);
		Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
		Assertions.assertEquals("Element from list of favourites deleted succesfully",response.getBody());
	}

	@Test
	public void deleteFavouriteError() throws Exception{
		Long userId = 1L;
		Long repoId = 22L;
		Mockito.doThrow(ServiceException.class).when(userMockService).deleteFavourite(Mockito.anyLong(), Mockito.anyLong());
		ResponseEntity<Object> response = userController.deleteFavourite(userId,repoId);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(null,favourites);
	}

	@Test
	public void compareFavourites() throws Exception{
		Long id1 = 1L;
		Long id2 = 2L;
		List<Repository> favourites1 = new ArrayList<>();
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		favourites1.add(repo1);
		favourites1.add(repo2);
		favourites1.add(repo3);
		List<Repository> favourites2 = new ArrayList<>();
		Repository cloneRepo2 = new Repository(1L, "repo 2");
		Repository cloneRepo3 = new Repository(2L, "repo 3");
		Repository repo4 = new Repository(3L, "repo 4");
		favourites2.add(cloneRepo2);
		favourites2.add(cloneRepo3);
		favourites2.add(repo4);
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenReturn(favourites1, favourites2);
		ResponseEntity<Object> response = userController.compareFavourites(id1,id2);
		Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(3,favourites1.size());
	}

}
