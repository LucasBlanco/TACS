package com.tacs.ResstApp.services.impl;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tacs.ResstApp.model.LoginResponse;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private RepositoryRepository repositoryRepository;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private UserTokenService userTokenService;
	
	@Autowired
	private GitService gitService;

	@Autowired
	private SaltService saltService;

	Logger logger = LoggerFactory.getLogger("SampleLogger");

	public UserService() {}

	public User createUser(User newUser) throws ServiceException {
	    if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
	        throw new ServiceException("Username already taken");
        }
	    String encryptedPass = saltService.encrypt(newUser.getPassword());
	    newUser.setPassword(encryptedPass);
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

	public List<Repository> getUserFavouriteRepos(Long userId) throws ServiceException {
		return getUser(userId).getFavourites();
	}

	public Repository getFavouriteByName(String name) throws ServiceException {
		Optional<Repository> repo = repositoryRepository.findByName(name);
		if(repo.isPresent()){
			return repo.get();
		}
		throw new ServiceException("Favourite does not exist");
	}

	public List<Repository> addFavourite(Long userId, Repository gitRepository) throws ServiceException, IOException {
		synchronized (userId) {
			User user = getUser(userId);
			Repository repoToAdd = repositoryService.getRepository(gitRepository);
			if(hasRepository(user, repoToAdd)) {
				throw new ServiceException("The repository " + repoToAdd.getName() + " was already in favourites");
			}
			repoToAdd.favved();
			gitService.addLanguages(repoToAdd);
			repositoryService.save(repoToAdd);
			user.addFavourite(repoToAdd);
			userRepository.save(user);
			return user.getFavourites();
		}
	}

	public void deleteFavourite(Long userId, Long repoId) throws ServiceException, IOException {
		User user = getUser(userId);
		Repository repoToRemove = repositoryService.getRepositoryForDelete(repoId);
		if(hasRepository(user, repoToRemove)){
			repoToRemove.unfavved();
			repositoryService.save(repoToRemove);
			user.deleteFavourite(repoToRemove);
			userRepository.save(user);
		} else {
			throw new ServiceException("User does not have repository in favourites");
		}
	}

	private boolean sameRepositories(Repository repo, Repository anotherRepo) {
		return anotherRepo.getId().equals(repo.getId());
	}

	private boolean hasRepository(User user, Repository repoToRemove) {
		return user.getFavourites().stream().anyMatch(f -> sameRepositories(repoToRemove, f));
	}
	
	public ComparisonDTO getFavouritesComparison(Long id1, Long id2) throws ServiceException {
		User user1 = userRepository.findById(id1).get();
		User user2 = userRepository.findById(id2).get();
		updateUser(user1);
		updateUser(user2);
		List<Repository> favs1 = user1.getFavourites();
		List<Repository> favs2 = user2.getFavourites();
		List<Repository> commonRepos = favs1==null?null:favs1.stream().filter(favs2::contains).collect(Collectors.toList());
		List<String> favs1Languages = favs1==null?null:favs1.stream().map(Repository::getMainLanguage).filter(x -> x!=null).distinct().collect(Collectors.toList());
		List<String> favs2Languages = favs2==null?null:favs2.stream().map(Repository::getMainLanguage).filter(x -> x!=null).distinct().collect(Collectors.toList());
		List<String> commonLanguages = favs1Languages==null?null:favs1Languages.stream().filter(favs2Languages::contains).collect(Collectors.toList());
		
		return new ComparisonDTO(id1, id2, commonRepos, commonLanguages);
	}

	public void logout(String token) throws ServiceException{
		userTokenService.destroyToken(token);
	}

	public void updateUser(User user) throws ServiceException{
		List<Repository> repositoriesWithNoData = user.getFavourites();
		List<Repository> repositoriesWithData = new ArrayList<>();

		repositoriesWithNoData.forEach(repo -> {
			try {
				repositoriesWithData.add(repositoryService.getRepository(repo));
			} catch (ServiceException | IOException e) {
				//Queria que quede la excepcion lanzada pero no me deja :(
			}
		});

		List<String> languages = new ArrayList<>();
		repositoriesWithData.forEach(repo -> languages.add(repo.getMainLanguage()));
		user.setLanguages(languages);
	}

	public LoginResponse login(User user) throws ServiceException {
		User foundUser = this.getUserByUsername(user.getUsername());
		String decryptedPassword = saltService.decrypt(foundUser.getPassword());
		logger.info("Pass: " + foundUser.getPassword() + " y desencriptada: " + decryptedPassword);
		if(decryptedPassword.equals(user.getPassword())){
			String token = userTokenService.generateToken(foundUser);
			foundUser.setLastLoginDate(LocalDateTime.now());
			userRepository.save(foundUser);

			LoginResponse response = new LoginResponse();
			response.setUserId(foundUser.getId());
			response.setAdmin(foundUser.isAdmin());
			response.setToken(token);

			return response;
		}
		throw new ServiceException("Incorrect password");
	}

	public Long getUserId(User user) throws ServiceException {
		return this.getUserByUsername(user.getUsername()).getId();
	}
	
}
