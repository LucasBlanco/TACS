package com.tacs.ResstApp.services.mock;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepositoryMockService {

    private List<Repository> repositories;
    
    private List<User> users;

    public RepositoryMockService() {

        Repository repository1 = new Repository(1L,"TACS");
        Repository repository2 = new Repository(2L,"TADP");
        Repository repository3 = new Repository(3L,"DDS");
        Repository repository4 = new Repository(4L,"PDP");
        Repository repository5 = new Repository(5L,"SO");
        Repository repository6 = new Repository(6L,"GDD");
        this.repositories = new ArrayList<>(Arrays.asList(repository1, repository2, repository3, repository4, repository5, repository6));
        
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

    public Repository getRepository(Long id) throws ServiceException{
        return repositories.stream().filter(repo -> repo.getId() == id).findFirst().orElseThrow(()-> new ServiceException("Repositorio inexistente"));
    }

    public List<Repository> getRepositories(Date since, Date to){
        return repositories.stream().filter(repository -> !repository.getRegistrationDate().before(since) && !repository.getRegistrationDate().after(to)).collect(Collectors.toList());
    }
    
    public List<User> getUsers(){
    	return users;
    }
}

