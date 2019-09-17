package com.tacs.ResstApp.services.impl;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacs.ResstApp.model.ComparisonDTO;
import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.repositories.UserRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RepositoryService repositoryService;



	private List<User> users = new ArrayList<User>();

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

	public void deleteFavourite(Long userId, String repoName) throws ServiceException, IOException {
		User user = getUser(userId);
		Repository repoToRemove = repositoryService.getRepository(repoName);

		if(user.getFavourites().contains(repoToRemove)){
			user.getFavourites().remove(repoToRemove);
			userRepository.save(user);
		} else {
			throw new ServiceException("User does not have repository in favourites");
		}
	}
	
	public ComparisonDTO getFavouritesComparison(Long id1, Long id2) throws ServiceException {
		
		List<Repository> favs1 = this.getUserFavouriteRepos(id1);
		List<Repository> favs2 = this.getUserFavouriteRepos(id2);
		List<Repository> commonRepos = favs1==null?null:favs1.stream().filter(favs2::contains).collect(Collectors.toList());
		List<String> favs1Languages = favs1==null?null:favs1.stream().map(Repository::getLanguages).filter(x -> x!=null).flatMap(List::stream).distinct().collect(Collectors.toList());
		List<String> favs2Languages = favs2==null?null:favs2.stream().map(Repository::getLanguages).filter(x -> x!=null).flatMap(List::stream).distinct().collect(Collectors.toList());
		List<String> commonLanguages = favs1Languages==null?null:favs1Languages.stream().filter(favs2Languages::contains).collect(Collectors.toList());
		
		return new ComparisonDTO(id1, id2, commonRepos, commonLanguages);
	}

	public void logout(String token) throws ServiceException{
		userTokenService.destroyToken(token);
	}

	public void updateUser(User user) throws ServiceException{
		List<Repository> repositoriesWithNoData = user.getFavourites();
		List<Repository> repositoriesWithData = new ArrayList<>();

		repositoriesWithData.forEach(repo -> {
			try {
				repositoriesWithData.add(repositoryService.getRepository(repo.getName()));
			} catch (ServiceException | IOException e) {
				//Queria que quede la excepcion lanzada pero no me deja :(
			}
		});

		List<String> languages = new ArrayList<>();
		repositoriesWithData.forEach(repo -> languages.add(repo.getMainLanguage()));
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
