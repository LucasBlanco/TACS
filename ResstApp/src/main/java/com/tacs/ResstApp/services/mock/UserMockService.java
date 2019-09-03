package com.tacs.ResstApp.services.mock;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserMockService {

    private List<User> users;
    private List<Repository> repositories;

    public UserMockService() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        user1.setId(1L);
        user1.setUsername("Juam");
        user2.setId(2L);
        user2.setUsername("LucasBlanco");
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


    public User createUser(User newUser) throws ServiceException{
        users.add(newUser);
        return newUser;
    }

    public List<User> getUsers() throws ServiceException{
        return users;
    }

    public User getUser(Long id) throws ServiceException{
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(()-> new ServiceException("User does not exist"));
    }

    public List<Repository> getUserFavouriteRepos(Long id) throws ServiceException{
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(()-> new ServiceException("User does not exist")).getFavourites();
    }


    public List<Repository> addFavourite(Long userId, Long repositoryId) throws ServiceException{
        List<Repository> favouriteRepos = getUserFavouriteRepos(userId);
        favouriteRepos.add(this.findRepository(repositoryId));
        getUser(userId).setFavourites(favouriteRepos);
        return getUserFavouriteRepos(userId);
    }

    public Repository findRepository(Long repositoryId) throws ServiceException {
        return repositories.stream().filter(repo -> repo.getId() == repositoryId).findFirst().orElseThrow(() -> new ServiceException("Repositrio inexistente"));
    }


    public Repository getUserFavouriteRepoById(Long userId, Long id) throws ServiceException{
        return getUserFavouriteRepos(userId).stream().filter(repo -> repo.getId() == id).findFirst().orElseThrow(()-> new ServiceException("Favourite does not exist"));
    }

    public List<Repository> createFavourite(Long id, Repository repositoryToFave) throws ServiceException{
        List<Repository> favouriteRepos = getUserFavouriteRepos(id);
        favouriteRepos.add(repositoryToFave);
        getUser(id).setFavourites(favouriteRepos);
        return getUserFavouriteRepos(id);
    }

    public void deleteFavourite(Long userId, Long id) throws ServiceException{
        List<Repository> favouriteRepos = getUserFavouriteRepos(userId);
        favouriteRepos.removeIf(repository -> repository.getId() == id);
        getUser(id).setFavourites(favouriteRepos);
    }

    public void logout(String token) throws ServiceException{
        //todo
    }

    public List<Repository> getRepositoriesBetween(LocalDateTime inicio, LocalDateTime fin) throws ServiceException{
        return repositories;
        //todo
    }
}

