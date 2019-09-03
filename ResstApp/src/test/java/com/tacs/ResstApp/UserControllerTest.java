package com.tacs.ResstApp;

import com.tacs.ResstApp.controllers.UserController;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
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

	@BeforeEach
	public void before()  {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void login() throws Exception{
		String response = userController.login();
		Assertions.assertEquals(response, "Login");
	}

	@Test
	public void logout() throws Exception{
		String response = userController.logout();
		Assertions.assertEquals(response, "Logout");
	}

	@Test
	public void createUsers(){
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
		Mockito.when(userMockService.createUsers(Mockito.anyList())).thenReturn(new ArrayList<>(Arrays.asList(user1, user2, user3)));
		ResponseEntity<Object> response = userController.createUsers(newUsers);
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

	@Test
	public void getUserByIdSuccess() throws Exception{
		Long id = 1L;
		User user = new User();
		user.setId(1L);
		user.setUsername("Maria");
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenReturn(user);
		ResponseEntity<Object> response = userController.getUserById(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		User returnedUser = (User)response.getBody();
		Assertions.assertEquals(1L, returnedUser.getId());
		Assertions.assertEquals("Maria",returnedUser.getUsername());
		Assertions.assertEquals(new ArrayList<>(),returnedUser.getFaveRepos());
	}

	@Test
	public void getUserByIdError() throws Exception{
		Long id = 1L;
		User user = new User();
		user.setId(1L);
		user.setUsername("Maria");
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.getUserById(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		User returnedUser = (User)response.getBody();
		Assertions.assertEquals(null, returnedUser);
	}

	@Test
	public void getFavouritesSuccess() throws Exception{
		Long id = 1L;
		Repository repo = new Repository(1L, "repo 1");
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenReturn(new ArrayList<>(Arrays.asList(repo)));
		ResponseEntity<Object> response = userController.getFavourites(id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
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
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		List<Repository> returnedFaves = (ArrayList)response.getBody();
		Assertions.assertEquals(null, returnedFaves);
	}

	@Test
	public void createFavouritesSucess() throws Exception{
		Long id = 1L;
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		List<Repository> repositoriesToFave = new ArrayList<>(Arrays.asList(repo2, repo3));
		Mockito.when(userMockService.createFavourites(Mockito.anyLong(), Mockito.anyList())).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));
		ResponseEntity<Object> response = userController.createFavourites(id,repositoriesToFave);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
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
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		List<Repository> repositoriesToFave = new ArrayList<>(Arrays.asList(repo2, repo3));
		Mockito.when(userMockService.createFavourites(Mockito.anyLong(), Mockito.anyList())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.createFavourites(id,repositoriesToFave);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(null,favourites);
	}

	@Test
	public void deleteFavouriteSucess() throws Exception{
		Long userId = 1L;
		Long repoId = 22L;
		Mockito.when(userMockService.deleteFavourite(Mockito.anyLong(), Mockito.anyLong())).thenReturn(new ArrayList<>());
		ResponseEntity<Object> response = userController.deleteFavourite(userId,repoId);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(0,favourites.size());
	}

	@Test
	public void deleteFavouriteError() throws Exception{
		Long userId = 1L;
		Long repoId = 22L;
		Mockito.when(userMockService.deleteFavourite(Mockito.anyLong(), Mockito.anyLong())).thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.deleteFavourite(userId,repoId);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		List<Repository> favourites = (ArrayList) response.getBody();
		Assertions.assertEquals(null,favourites);
	}

}
