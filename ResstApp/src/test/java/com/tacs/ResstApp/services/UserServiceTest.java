package com.tacs.ResstApp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.tacs.ResstApp.repositories.UserRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.RepositoryService;
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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

    @Mock
    RepositoryService repositoryService;

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
	private List<User> users = new ArrayList<>();
	
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

		users.add(user1);
		users.add(user2);
		users.add(user3);
	}
	
	@Test
	public void compareFavourites() throws Exception {
	    when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(userId2)).thenReturn(Optional.of(user2));
		List<Repository> favouritesComparison = userService.getFavouritesComparison(userId1, userId2);
		Assertions.assertEquals(2, favouritesComparison.size());
	}
	
	@Test
	public void compareFavouritesWithEmptyUserList() throws Exception {
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(userId3)).thenReturn(Optional.of(user3));
		List<Repository> favouritesComparison = userService.getFavouritesComparison(userId1, userId3);
		Assertions.assertEquals(0, favouritesComparison.size());
	}

	@Test
	public void addRepoToFavouritesAddsRepoToUsersFavourites() throws ServiceException {
		User user = getUserWithFavourites();
		Repository repository = new Repository(3L, "Third repo");
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		userService.addFavourite(user.getId(),repository);

		assertThat(user.getFavourites().size()).isEqualTo(3);
		verify(userRepository, times(1)).save(user);
	}

    @Test
    public void addRepoToFavouritesThrowserrorBecauseUserDoesNotExist() throws ServiceException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> { userService.addFavourite(1L,new Repository(3L,"")); });

        assertThat(thrown).isInstanceOf(ServiceException.class)
                .hasMessageContaining("User does not exist");
    }

    @Test
    public void removeRepoFromFavouritesRemovesRepoFromUsersFavourites() throws ServiceException, IOException {
        User user = getUserWithFavourites();
        Repository repositoryToRemove = new Repository(3L, "Third repo");
        user.getFavourites().add(repositoryToRemove);
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(repositoryService.getRepository("repo 2")).thenReturn(repositoryToRemove);

        userService.deleteFavourite(user.getId(),"repo 2");

        assertThat(user.getFavourites().size()).isEqualTo(2);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void removeRepoFromFavouritesThrowserrorBecauseRepoDoesNotExist() throws ServiceException, IOException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserWithFavourites()));
        when(repositoryService.getRepository("repo 2")).thenThrow(ServiceException.class);

        Throwable thrown = catchThrowable(() -> { userService.deleteFavourite(3L,"repo 2"); });

        assertThat(thrown).isInstanceOf(ServiceException.class);
    }

    @Test
    public void removeRepoFromFavouritesThrowserrorUserDoesNotHaveRepoInFavouritest() throws ServiceException, IOException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserWithFavourites()));
        when(repositoryService.getRepository("repo 2")).thenReturn(new Repository(4L,""));

        Throwable thrown = catchThrowable(() -> { userService.deleteFavourite(3L,"repo 2"); });

        assertThat(thrown).isInstanceOf(ServiceException.class)
                .hasMessageContaining("User does not have repository in favourites");
    }

    @Test
	public void createUserCreatesUser() throws ServiceException {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);

		User returnedUser = userService.createUser(user);

		assertThat(returnedUser).isEqualTo(user);

	}

	@Test
	public void createUserWithRepetedUsernameThrowsError() throws ServiceException {
		User user = new User();
		user.setUsername("Billie");
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

		Throwable thrown = catchThrowable(() -> { userService.createUser(user); });

		assertThat(thrown).isInstanceOf(ServiceException.class)
				.hasMessageContaining("Username already taken");

	}

	@Test
	public void getUsersReturnsAllUsers() throws ServiceException {
		when(userRepository.findAll()).thenReturn(users);

		List<User> foundUsers = userService.getUsers();

		assertThat(foundUsers).isEqualTo(users);
	}

	@Test
	public void getUserReturnsExistentUser() throws ServiceException {
		User user = new User();
		user.setId(1L);
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		User foundUser = userService.getUser(user.getId());

		assertThat(foundUser).isEqualTo(user);
	}

	@Test
	public void getUserReturnsThrowsEceptionWhenUserDoesNotExist() throws ServiceException {
		User user = new User();
		user.setId(1L);
		when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> { userService.getUser(user.getId()); });

		assertThat(thrown).isInstanceOf(ServiceException.class)
				.hasMessageContaining("User does not exist");
	}

	@Test
	public void getUserByUsernameReturnsExistentUser() throws ServiceException {
		User user = new User();
		user.setUsername("Mariana");
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

		User foundUser = userService.getUserByUsername(user.getUsername());

		assertThat(foundUser).isEqualTo(user);
	}

	@Test
	public void getUserByUsernameReturnsThrowsEceptionWhenUserDoesNotExist() throws ServiceException {
		User user = new User();
		user.setUsername("Mariana");
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> { userService.getUserByUsername(user.getUsername()); });

		assertThat(thrown).isInstanceOf(ServiceException.class)
				.hasMessageContaining("User does not exist");
	}

	@Test
	public void getUserReposReturnUsersRepos() throws ServiceException {
		User user = getUserWithFavourites();
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		List<Repository> foundFavourites = userService.getUserFavouriteRepos(user.getId());

		assertThat(foundFavourites).isEqualTo(user.getFavourites());
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
