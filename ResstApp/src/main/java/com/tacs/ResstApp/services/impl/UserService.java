package com.tacs.ResstApp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class UserService {

	private List<User> users = new ArrayList<User>();

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

	public List<Repository> createFavourite(Long id, Repository repositoryToFave) throws ServiceException {
		List<Repository> favouriteRepos = getUserFavouriteRepos(id);
		favouriteRepos.add(repositoryToFave);
		getUser(id).setFavourites(favouriteRepos);
		return getUserFavouriteRepos(id);
	}

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
