package com.tacs.ResstApp.services.mock;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserMockService {

    private List<User> users;

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

    public List<User> createUsers(List<User> newUsers){
        users.addAll(newUsers);
        return users;
    }

    public User getUser(Long id) throws ServiceException{
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(()-> new ServiceException("Usuario inexistente"));
    }

    public List<Repository> getUserFavouriteRepos(Long id) throws ServiceException{
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(()-> new ServiceException("Usuario inexistente")).getFaveRepos();
    }

    public List<Repository> createFavourites(Long id, List<Repository> repositoriesToFave) throws ServiceException{
        List<Repository> favouriteRepos = getUserFavouriteRepos(id);
        favouriteRepos.addAll(repositoriesToFave);
        getUser(id).setFaveRepos(favouriteRepos);
        return getUserFavouriteRepos(id);
    }

    public List<Repository> deleteFavourite(Long userId, Long id) throws ServiceException{
        List<Repository> favouriteRepos = getUserFavouriteRepos(userId);
        favouriteRepos.removeIf(repository -> repository.getId() == id);
        getUser(id).setFaveRepos(favouriteRepos);
        return getUserFavouriteRepos(id);
    }



}

