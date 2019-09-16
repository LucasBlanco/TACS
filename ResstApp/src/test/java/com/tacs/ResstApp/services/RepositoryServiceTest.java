package com.tacs.ResstApp.services;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tacs.ResstApp.services.impl.GitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.impl.RepositoryService;

@SpringBootTest
public class RepositoryServiceTest {

	@InjectMocks
    RepositoryService repositoryService = new RepositoryService();
	@Mock
	GitService gitService = new GitService();

    private List<Repository> repositories;
	private Repository repo1 = new Repository(1L, "TACS");
	private Repository repo2 = new Repository(2L, "TADP");
    private LocalDate referenceTime = LocalDate.now();



    @BeforeEach
    public void before() throws Exception {

        repo1.setRegistrationDate(referenceTime.minusDays(2));
        repo2.setRegistrationDate(referenceTime.plusDays(2));
        this.repositories = new ArrayList<>(Arrays.asList(repo1, repo2));
		Mockito.when(gitService.getRepositories()).thenReturn(this.repositories);
        /*repositoryService.getRepositories().clear();
        repositoryService.getRepositories().addAll(repositories);*/
    }

    @Test
    public void repositoriesBetween1() throws Exception {
        Assertions.assertTrue(
        		repositoryService.getRepositoriesBetween(
                        referenceTime.minusDays(1),
                        referenceTime.plusDays(1)
                ).isEmpty()
		);
    }

    @Test
    public void repositoriesBetween2() throws Exception {
        Assertions.assertEquals(
        		repo1,
				repositoryService.getRepositoriesBetween(
						referenceTime.minusDays(3),
						referenceTime.plusDays(1)
				).get(0));
    }

    @Test
    public void repositoriesBetween3() throws Exception {
        Assertions.assertEquals(
        		2,
				repositoryService.getRepositoriesBetween(
						referenceTime.minusDays(3),
						referenceTime.plusDays(3)
				).size());
    }

    @Test
    public void repositoriesBetween4() throws Exception {
        Assertions.assertTrue(
        		repositoryService.getRepositoriesBetween(
        				referenceTime.plusDays(5),
						referenceTime.plusDays(7)
				).isEmpty());
    }

}
