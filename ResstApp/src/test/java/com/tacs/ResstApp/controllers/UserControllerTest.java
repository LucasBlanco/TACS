package com.tacs.ResstApp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

import com.tacs.ResstApp.model.ComparisonDTO;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.services.impl.UserService;


@SpringBootTest
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userMockService;

	@Mock
	RepositoryService repositoryMockService;

	@BeforeEach
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loginSuccessfull() throws ServiceException {
		User user = new User();
		user.setId(1L);
		user.setUsername("Pedro");
		Mockito.when(userMockService.getUserByUsername(user.getUsername())).thenReturn(user);

		ResponseEntity<Object> response = userController.login(user);
		String token = (String) response.getBody();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("token1", token);
	}

	@Test
	public void loginReturnsUserError() throws ServiceException {
		User user = new User();
		user.setId(1L);
		user.setUsername("Pedro");
		Mockito.when(userMockService.getUserByUsername(user.getUsername())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.login(user);
		String token = (String) response.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(token);
	}

	@Test
	public void loginReturnsServerError() throws ServiceException {
		User user = new User();
		user.setId(1L);
		user.setUsername("Pedro");
		Mockito.when(userMockService.getUserByUsername(user.getUsername())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.login(user);
		String token = (String) response.getBody();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(token);
	}

	@Test
	public void logoutSuccessfully() {
		String token = "soyeltoken";

		ResponseEntity<Object> response = userController.logout(token);

		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Assertions.assertEquals("Logout successful", response.getBody());
	}

	@Test
	public void logoutReturnsUserError() throws ServiceException {
		String token = "soyeltoken";
		Mockito.doThrow(ServiceException.class).when(userMockService).logout(Mockito.anyString());

		ResponseEntity<Object> response = userController.logout(token);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void logoutReturnsServerError() throws ServiceException {
		String token = "soyeltoken";
		Mockito.doThrow(RuntimeException.class).when(userMockService).logout(Mockito.anyString());

		ResponseEntity<Object> response = userController.logout(token);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
	}

	@Test
	public void getUsersReturns3UsersSuccessfully() throws Exception {
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
		List<User> updatedUsers = (List) response.getBody();

		Assertions.assertEquals(3, updatedUsers.size());
		Assertions.assertEquals(1L, updatedUsers.get(0).getId());
		Assertions.assertEquals("Jose", updatedUsers.get(0).getUsername());
		Assertions.assertEquals(2L, updatedUsers.get(1).getId());
		Assertions.assertEquals("Pepe", updatedUsers.get(1).getUsername());
		Assertions.assertEquals(3L, updatedUsers.get(2).getId());
		Assertions.assertEquals("Juan", updatedUsers.get(2).getUsername());
	}

	@Test
	public void getUsersReturnsUserError() throws ServiceException {
		Mockito.when(userMockService.getUsers()).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.getUsers();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getUsersReturnsServerError() throws ServiceException {
		Mockito.when(userMockService.getUsers()).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.getUsers();

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void createUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setUsername("Jose");
		Mockito.when(userMockService.createUser(Mockito.any(User.class))).thenReturn(user);

		ResponseEntity<Object> response = userController.createUser(user);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		User createdUser = (User) response.getBody();

		Assertions.assertEquals(1L, createdUser.getId());
		Assertions.assertEquals("Jose", createdUser.getUsername());
	}

	@Test
	public void createUserReturnsUserError() throws ServiceException {
		Mockito.when(userMockService.createUser(Mockito.any(User.class))).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.createUser(new User());

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void createUserReturnsServerError() throws ServiceException {
		Mockito.when(userMockService.createUser(Mockito.any(User.class))).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.createUser(new User());

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getUserByIdSuccessfully() throws Exception {
		Long id = 1L;
		User user = new User();
		user.setId(1L);
		user.setUsername("Maria");
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenReturn(user);

		ResponseEntity<Object> response = userController.getUserById(id);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		User returnedUser = (User) response.getBody();

		Assertions.assertEquals(1L, returnedUser.getId());
		Assertions.assertEquals("Maria", returnedUser.getUsername());
		Assertions.assertEquals(new ArrayList<>(), returnedUser.getFavourites());
	}

	@Test
	public void getUserByIdReturnsUserError() throws ServiceException {
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.getUserById(1L);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getUserByIdReturnsServerError() throws ServiceException {
		Mockito.when(userMockService.getUser(Mockito.anyLong())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.getUserById(1L);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getFavouritesSuccessfully() throws Exception {
		Long id = 1L;
		Repository repo = new Repository(1L, "repo 1");
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenReturn(new ArrayList<>(Arrays.asList(repo)));

		ResponseEntity<Object> response = userController.getFavourites(id);
		List<Repository> favourites = (List) response.getBody();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1, favourites.size());
		Assertions.assertEquals(1L, favourites.get(0).getId());
		Assertions.assertEquals("repo 1", favourites.get(0).getName());
	}

	@Test
	public void getFavouritesReturnsUserError() throws ServiceException {
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.getFavourites(1L);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void getFavouritesReturnsServerError() throws ServiceException {
		Mockito.when(userMockService.getUserFavouriteRepos(Mockito.anyLong())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.getFavourites(1L);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	/*@Test
	public void getFavouriteByIdSuccess() throws Exception { vuela?
		Long userId = 1L;
		Long id = 22L;
		Repository repo = new Repository(22L, "repo 1");
		Mockito.when(userMockService.getUserFavouriteRepoById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(repo);
		ResponseEntity<Object> response = userController.getFavourite(userId, id);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Repository favourite = (Repository) response.getBody();
		Assertions.assertEquals(22L, favourite.getId());
		Assertions.assertEquals("repo 1", favourite.getName());
	}*/

	/*@Test
	public void getFavouriteByIdError() throws Exception { vuela?
		Long userId = 1L;
		Long id = 22L;
		Mockito.when(userMockService.getUserFavouriteRepoById(Mockito.anyLong(), Mockito.anyLong()))
				.thenThrow(ServiceException.class);
		ResponseEntity<Object> response = userController.getFavouriteById(userId, id);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Repository returnedFave = (Repository) response.getBody();
		Assertions.assertNull(returnedFave);
	}*/

	@Test
	public void createFavouritesReturns3RepositoriesSuccessfully() throws Exception {
		Long id = 1L;
		Repository repo1 = new Repository(1L, "repo 1");
		Repository repo2 = new Repository(2L, "repo 2");
		Repository repo3 = new Repository(3L, "repo 3");
		Mockito.when(repositoryMockService.getRepository(Mockito.anyLong())).thenReturn(repo3);
		Mockito.when(userMockService.addFavourite(Mockito.anyLong(), Mockito.anyLong())).thenReturn(new ArrayList<>(Arrays.asList(repo1, repo2, repo3)));

		ResponseEntity<Object> response = userController.createFavourite(id, repo3);
		List<Repository> favourites = (List) response.getBody();

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(3, favourites.size());
		Assertions.assertEquals(1L, favourites.get(0).getId());
		Assertions.assertEquals("repo 1", favourites.get(0).getName());
		Assertions.assertEquals(2L, favourites.get(1).getId());
		Assertions.assertEquals("repo 2", favourites.get(1).getName());
		Assertions.assertEquals(3L, favourites.get(2).getId());
		Assertions.assertEquals("repo 3", favourites.get(2).getName());
	}

	@Test
	public void createFavouritesReturnsUserError() throws ServiceException {
		Long id = 1L;
		Repository repository = new Repository(3L, "repo 3");
		Mockito.when(userMockService.addFavourite(Mockito.anyLong(), Mockito.anyLong())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.createFavourite(id, repository);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void createFavouritesReturnsServerError() throws ServiceException {
		Long id = 1L;
		Repository repository = new Repository(3L, "repo 3");
		Mockito.when(userMockService.addFavourite(Mockito.anyLong(), Mockito.any())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.createFavourite(id, repository);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void deleteFavouriteSuccessfully() throws Exception {
		Long userId = 1L;
		Long repoId = 22L;
		Mockito.doNothing().when(userMockService).deleteFavourite(Mockito.anyLong(), Mockito.anyLong());

		ResponseEntity<Object> response = userController.deleteFavourite(userId, repoId);

		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		Assertions.assertEquals("Element from list of favourites deleted succesfully", response.getBody());
	}

	@Test
	public void deleteFavouritesReturnsUserError() throws ServiceException {
		Mockito.doThrow(ServiceException.class).when(userMockService).deleteFavourite(Mockito.anyLong(), Mockito.anyLong());

		ResponseEntity<Object> response = userController.deleteFavourite(1L, 1L);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void deleteFavouritesReturnsServerError() throws ServiceException {
		Mockito.doThrow(RuntimeException.class).when(userMockService).deleteFavourite(Mockito.anyLong(), Mockito.anyLong());

		ResponseEntity<Object> response = userController.deleteFavourite(1L, 1L);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void compareFavourites() throws Exception {
		Long id1 = 1L;
		Long id2 = 2L;
		Repository repo1 = new Repository(id1, "Repo1");
		List<Repository> commonFavourites = Arrays.asList(repo1);
		List<String> commonLanguages = Stream.of("PYTHON", "C").collect(Collectors.toCollection(ArrayList::new));
		ComparisonDTO favouritesComparison = new ComparisonDTO(id1, id2, commonFavourites, commonLanguages); 
		Mockito.when(userMockService.getFavouritesComparison(id1, id2)).thenReturn(favouritesComparison);

		ResponseEntity<Object> response = userController.compareFavourites(id1, id2);
		ComparisonDTO responseDTO = (ComparisonDTO) response.getBody();

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1, responseDTO.getCommonRepositories().size());
		Assertions.assertEquals(2, responseDTO.getCommonLanguages().size());
	}

	@Test
	public void compareFavouritesReturnsUserError() throws ServiceException {
		Mockito.when(userMockService.getFavouritesComparison(Mockito.anyLong(), Mockito.anyLong())).thenThrow(ServiceException.class);

		ResponseEntity<Object> response = userController.compareFavourites(1L, 1L);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

	@Test
	public void compareFavouritesReturnsServerError() throws ServiceException {
		Mockito.when(userMockService.getFavouritesComparison(Mockito.anyLong(), Mockito.anyLong())).thenThrow(RuntimeException.class);

		ResponseEntity<Object> response = userController.compareFavourites(1L, 1L);

		Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}

}
