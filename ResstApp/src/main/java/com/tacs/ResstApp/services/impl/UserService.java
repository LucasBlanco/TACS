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

	//para mockear
	public UserService() {
       /*User user1 = new User();
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
        this.users = new ArrayList<>(Arrays.asList(user1, user2, user3, user4, user5, user6));*/
    }

	public User createUser(User newUser) throws ServiceException {
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

	public Repository getUserFavouriteRepoById(Long userId, Long id) throws ServiceException {
		return getUserFavouriteRepos(userId).stream().filter(repo -> repo.getId() == id).findFirst()
				.orElseThrow(() -> new ServiceException("Favourite does not exist"));
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
}
