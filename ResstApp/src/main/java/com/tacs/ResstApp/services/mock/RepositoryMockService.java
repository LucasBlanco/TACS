package com.tacs.ResstApp.services.mock;

import com.tacs.ResstApp.model.Repository;
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

    public RepositoryMockService() {

        Repository repository1 = new Repository(1L,"TACS");
        Repository repository2 = new Repository(2L,"TADP");
        Repository repository3 = new Repository(3L,"DDS");
        Repository repository4 = new Repository(4L,"PDP");
        Repository repository5 = new Repository(5L,"SO");
        Repository repository6 = new Repository(6L,"GDD");
        this.repositories = new ArrayList<>(Arrays.asList(repository1, repository2, repository3, repository4, repository5, repository6));
    }

    public Repository getRepository(Long id) throws ServiceException{
        return repositories.stream().filter(repo -> repo.getId() == id).findFirst().orElseThrow(()-> new ServiceException("Repository does not exist"));
    }

    public List<Repository> getRepositories(Date since, Date to){
        return repositories.stream().filter(repository -> !repository.getRegistrationDate().before(since) && !repository.getRegistrationDate().after(to)).collect(Collectors.toList());
    }
    
    public  List<Repository> getRepositoriesFiltered(String language, Integer nofcommits, Integer nofstars, Integer nofissues) throws ServiceException{
       return repositories;
    }
}
    


