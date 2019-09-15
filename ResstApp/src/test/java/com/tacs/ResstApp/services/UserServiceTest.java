package com.tacs.ResstApp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tacs.ResstApp.repositories.UserRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.impl.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	private Long userId1 = 1L;
	private Long userId2 = 2L;
	private Long userId3 = 3L;
	private Repository repo1;
	private Repository repo2;
	private Repository repo3;
	private Repository repo4;
	private User user1;
	private User user2;
	private User user3;
	
	@BeforeEach
	public void before() throws Exception {
		repo1 = new Repository(1L, "repo 1");
		repo2 = new Repository(2L, "repo 2");
		repo3 = new Repository(3L, "repo 3");
		repo4 = new Repository(4L, "repo 4");
		user1 = new User();
		user2 = new User();
		user3 = new User();

		user1.setId(userId1);
		user2.setId(userId2);
		user3.setId(userId3);
		List<Repository> favourites1 = Arrays.asList(repo1, repo2, repo3);
		List<Repository> favourites2 = Arrays.asList(repo2, repo3, repo4);
		user1.setFavourites(favourites1);
		user2.setFavourites(favourites2);
		user3.setFavourites(Arrays.asList());

		userService.getUsers().clear();
		userService.getUsers().addAll(Arrays.asList(user1, user2, user3));
	}
	
	@Test
	public void compareFavourites() throws Exception {
		List<Repository> favouritesComparison = userService.getFavouritesComparison(userId1, userId2);
		Assertions.assertEquals(2, favouritesComparison.size());
	}
	
	@Test
	public void compareFavouritesWithEmptyUserList() throws Exception {
		List<Repository> favouritesComparison = userService.getFavouritesComparison(userId1, userId3);
		Assertions.assertEquals(0, favouritesComparison.size());
	}

	@Test
	public void addRepoToFavouritesAddsRepoToUsersFavourites() throws ServiceException {
		User user = getUserWithFavourites();
		Repository repository = new Repository(3L, "Third repo");
		when(userRepository.getOne(user.getId())).thenReturn(user);

		userService.addFavourite(user.getId(),repository);

		assertThat(user.getFavourites().size()).isEqualTo(3);
		verify(userRepository, times(1)).save(user);
	}




	private User getUserWithFavourites() {
		Repository repository1 = new Repository(1L,"First repo");
		List<Repository> repositories = new ArrayList<>();
		Repository repository2 = new Repository(2L,"Second repo");
		List<Repository> someRepositories = new ArrayList<>();
		someRepositories.add(repository1);
		someRepositories.add(repository2);
		User user = new User();
		user.setFavourites(someRepositories);
		return user;
	}
}
