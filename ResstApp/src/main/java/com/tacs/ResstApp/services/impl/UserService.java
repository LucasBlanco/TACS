package com.tacs.ResstApp.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class UserService {

	private List<User> users = new ArrayList<User>();

	//para mockear
	public UserService() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        Repository repository1 = new Repository(1L,"TACS");
        user1.setId(1L);
        user1.setUsername("Juam");
        user1.setFavourites(new ArrayList<>(Arrays.asList(repository1)));
        user2.setId(2L);
        user2.setUsername("LucasBlanco");
        user2.setFavourites(new ArrayList<>(Arrays.asList(repository1)));
        user3.setId(3L);
        user3.setUsername("LucasMCenturion");
        user4.setId(4L);
        user4.setUsername("LuciaRoldan");
        user5.setId(5L);
        user5.setUsername("MatiGiorda");
        user6.setId(6L);
        user6.setUsername("RocioChipian");
        this.users = new ArrayList<>(Arrays.asList(user1, user2, user3, user4, user5, user6));
    }
	
	@Autowired
	RepositoryService repositoryService;

	public User createUser(User newUser) throws ServiceException {
		users.add(newUser);
		return newUser;
	}

	public List<User> getUsers() throws ServiceException {
		return users;
	}

	public User getUser(Long id) throws ServiceException {
		return users.stream().filter(user -> user.getId() == id).findFirst()
				.orElseThrow(() -> new ServiceException("User does not exist"));
	}
	
	public User getUserByUsername(String username) throws ServiceException {
		return users.stream().filter(u -> Objects.equals(u.getUsername(), username)).findFirst()
				.orElseThrow(() -> new ServiceException("User does not exist"));
	}

	public List<Repository> getUserFavouriteRepos(Long id) throws ServiceException {
		return getUser(id).getFavourites();
	}

	public List<Repository> addFavourite(Long userId, Long repositoryId) throws ServiceException {
		List<Repository> favouriteRepos = getUserFavouriteRepos(userId);
		favouriteRepos.add(repositoryService.getRepository(repositoryId));
		getUser(userId).setFavourites(favouriteRepos);
		return getUserFavouriteRepos(userId);
	}

	public Repository getUserFavouriteRepoById(Long userId, Long id) throws ServiceException {
		return getUserFavouriteRepos(userId).stream().filter(repo -> repo.getId() == id).findFirst()
				.orElseThrow(() -> new ServiceException("Favourite does not exist"));
	}
	
	/*public List<Repository> createFavourite(Long id, Repository repositoryToFave) throws ServiceException {
		List<Repository> favouriteRepos = getUserFavouriteRepos(id);
		favouriteRepos.add(repositoryToFave);
		getUser(id).setFavourites(favouriteRepos);
		return getUserFavouriteRepos(id);
	}*/

	public void deleteFavourite(Long userId, Long id) throws ServiceException {
		List<Repository> favouriteRepos = getUserFavouriteRepos(userId);
		favouriteRepos.removeIf(repository -> repository.getId() == id);
		getUser(id).setFavourites(favouriteRepos);
	}
	
	public List<Repository> getFavouritesComparison(Long id1, Long id2) throws ServiceException {
		List<Repository> favs1 = this.getUserFavouriteRepos(id1);
		List<Repository> favs2 = this.getUserFavouriteRepos(id2);
		return favs1.stream().filter(favs2::contains).collect(Collectors.toList());
	}

}
