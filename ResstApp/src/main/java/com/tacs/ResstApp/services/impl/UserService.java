package com.tacs.ResstApp.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.tacs.ResstApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private UserTokenService userTokenService;

	//para mockear
	public UserService() {
    }

	public User createUser(User newUser) throws ServiceException {
	    if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
	        throw new ServiceException("Username already taken");
        }
		User user = userRepository.save(newUser);
		return user;
	}

	public List<User> getUsers() throws ServiceException {
		return userRepository.findAll();
	}

	public User getUser(Long id) throws ServiceException {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()){
			return user.get();
		}
		throw new ServiceException("User does not exist");
	}
	
	public User getUserByUsername(String username) throws ServiceException {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()){
			return user.get();
		}
		throw new ServiceException("User does not exist");
	}

	public List<Repository> getUserFavouriteRepos(Long id) throws ServiceException {
		return getUser(id).getFavourites();
	}

	public List<Repository> addFavourite(Long userId, Repository repository) throws ServiceException {
		User user = getUser(userId);
		user.getFavourites().add(repository);
		userRepository.save(user);
		return user.getFavourites();
	}

	public void deleteFavourite(Long userId, Long id) throws ServiceException {
		User user = getUser(userId);
		Repository repoToRemove = repositoryService.getRepository(id);

		if(user.getFavourites().contains(repoToRemove)){
			user.getFavourites().remove(repoToRemove);
			userRepository.save(user);
		} else {
			throw new ServiceException("User does not have repository in favourites");
		}
	}
	
	public List<Repository> getFavouritesComparison(Long id1, Long id2) throws ServiceException {
		List<Repository> favs1 = this.getUserFavouriteRepos(id1);
		List<Repository> favs2 = this.getUserFavouriteRepos(id2);
		return favs1.stream().filter(favs2::contains).collect(Collectors.toList());
	}

	public void logout(String token) throws ServiceException{
		//TODO
	}

	public void updateUser(User user) throws ServiceException{
		List<Repository> repositoriesWithNoData = user.getFavourites();
		List<Repository> repositoriesWithData = new ArrayList<>();

		repositoriesWithData.forEach(repo -> {
			try {
				repositoriesWithData.add(repositoryService.getRepository(repo.getId()));
			} catch (ServiceException e) {
				//Queria que quede la excepcion lanzada pero no me deja :(
			}
		});

		List<String> languages = new ArrayList<>();
		repositoriesWithData.forEach(repo -> languages.addAll(repo.getLanguages()));
		user.setLanguages(languages);
	}

	public String login(User user) throws ServiceException {
		User foundUser = this.getUserByUsername(user.getUsername());
		if(foundUser.getPassword() == user.getPassword()){
			return userTokenService.generateToken(foundUser);
		}
		throw new ServiceException("Incorrect password");
	}
}
