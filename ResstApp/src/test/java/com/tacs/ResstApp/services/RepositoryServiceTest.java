package com.tacs.ResstApp.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.tacs.ResstApp.model.Contributor;
import com.tacs.ResstApp.model.ContributorsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.repositories.RepositoryRepository;
import com.tacs.ResstApp.services.exceptions.ServiceException;
import com.tacs.ResstApp.services.impl.GitService;
import com.tacs.ResstApp.services.impl.RepositoryService;
import com.tacs.ResstApp.utils.CryptoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class RepositoryServiceTest {

	@InjectMocks
	RepositoryService repositoryService = new RepositoryService();
	@Mock
	RepositoryRepository repositoryRepository;
	@Mock
	GitService gitService;

	private List<Repository> repositories;
	private Repository repository1 = new Repository(1L, "TACS");
	private Repository repository2 = new Repository(2L, "TADP");
	private LocalDate referenceTime = LocalDate.now();
	private String encryptedPage = CryptoUtils.encrypt("Example");

	@BeforeEach
	public void before() throws Exception {
		repository1.setRegistrationDate(referenceTime.minusDays(2));
		repository2.setRegistrationDate(referenceTime.plusDays(2));
		repository1.setOwner("owner1");
		repository2.setOwner("owner2");
		this.repositories = new ArrayList<>(Arrays.asList(repository1, repository2));
		MockitoAnnotations.initMocks(this);
		Mockito.when(repositoryRepository.findAll()).thenReturn(this.repositories);
	}

	@Test
    public void repositoryServiceDoesNotReturnAnyRepositoriesInOpenIntervalBetweenRepositories() throws Exception {
        List<Repository> repositoriesInOpenInterval = 
        		repositoryService.getRepositoriesBetween(referenceTime.minusDays(1),referenceTime.plusDays(1));
		assertThat(repositoriesInOpenInterval).isEmpty();
    }

	@Test
	public void repositoryServiceReturnsRepository1InIntervalIncludingOnlyThatRepository() throws Exception {
		List<Repository> repositoriesInIntervalContainingRepository1 = 
				repositoryService.getRepositoriesBetween(referenceTime.minusDays(3), referenceTime.plusDays(1));
		assertThat(repositoriesInIntervalContainingRepository1).containsExactly(repository1);
	}

	@Test
	public void repositoryServiceReturnsBothRepositoriesInIntervalIncludingThem() throws Exception {
		List<Repository> repositoriesInClosedInterval = 
				repositoryService.getRepositoriesBetween(referenceTime.minusDays(3), referenceTime.plusDays(3));
		assertThat(repositoriesInClosedInterval).containsExactly(repository1, repository2);
	}

	@Test
	public void repositoryServiceDoesNotReturnAnyRepositoriesInExternalInterval() throws Exception {
		List<Repository> repositoriesInExternalInterval = 
				repositoryService.getRepositoriesBetween(referenceTime.plusDays(5), referenceTime.plusDays(7));
		assertThat(repositoriesInExternalInterval).isEmpty();
	}
	
	@Test
	public void repositoryServiceSearchingByUserAndNamePutNumberOfFavsInARegisteredRepo() throws IOException, ServiceException {
		Repository repoFromGit = new Repository(1L, "Repo1");
		Mockito.when(gitService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenReturn(repoFromGit);
		repository1.setFavs(5);
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(repository1));
		Repository repositoryFound = repositoryService.getRepositoryByUserRepo("user", repoFromGit.getName());
		assertThat(repositoryFound.getFavs()).isEqualTo(repository1.getFavs());
	}
	
	@Test
	public void repositoryServiceSearchingByUserAndNamePutNumberOfFavsTo0InAUnregisteredRepo() throws IOException, ServiceException {
		Repository repoFromGit = new Repository(1L, "Repo1");
		Mockito.when(gitService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenReturn(repoFromGit);
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Repository repositoryFound = repositoryService.getRepositoryByUserRepo("user", repoFromGit.getName());
		assertThat(repositoryFound.getFavs()).isEqualTo(0);
	}
	
	@Test
	public void repositoryServiceReturnsRepositorySearchingForDeleteIfPresent() throws IOException, ServiceException {
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(repository1));
		Repository repositoryFound = repositoryService.getRepositoryForDelete(repository1.getId());
		assertThat(repositoryFound).isEqualTo(repository1);
	}
	
	@Test
	public void repositoryServiceThrowsServiceExceptionSearchingForDeleteIfRepositoryIsNotPresent() throws IOException, ServiceException {
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Throwable thrown = catchThrowable(() -> { 	repositoryService.getRepositoryForDelete(repository1.getId()); });

        assertThat(thrown).isInstanceOf(ServiceException.class)
                .hasMessageContaining("Repo does not exist in database");
	}
	
	@Test
	public void repositoryServiceReturnsRepositorySavedIfPresentAndUpdatesIt() throws IOException, ServiceException {
		Repository savedRepo = new Repository(4L, "Saved repo");
		savedRepo.setOwner("saved owner");
		savedRepo.setFavs(5);
		Repository updatedRepo = new Repository(1L, "Repo1");
		updatedRepo.setMainLanguage("Some language");
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(savedRepo));
		Mockito.when(gitService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenReturn(updatedRepo);

		Repository repositoryFound = repositoryService.getRepository(repository1);
		assertThat(repositoryFound).isEqualTo(savedRepo);
		assertThat(repositoryFound.getMainLanguage()).isEqualTo("Some language");
	}
	
	@Test
	public void repositoryServiceReturnsRemoteRepositoryIfNotPresent() throws IOException, ServiceException {
		Repository remoteRepo = new Repository(1L, "Repo1");
		remoteRepo.setMainLanguage("Some language");
		Mockito.when(repositoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Mockito.when(gitService.getRepositoryByUserRepo(Mockito.anyString(), Mockito.anyString())).thenReturn(remoteRepo);

		Repository repositoryFound = repositoryService.getRepository(repository1);
		assertThat(repositoryFound).isEqualTo(remoteRepo);
		assertThat(repositoryFound.getMainLanguage()).isEqualTo("Some language");
	}
	
	@Test
	public void repositoryServiceReturnsRemoteRepositories() throws IOException, ServiceException {
		Mockito.when(gitService.getRepositories(Mockito.anyString())).thenReturn(repositories);
		
		List<Repository> foundRepositories = repositoryService.getRepositories(encryptedPage);
		assertThat(foundRepositories).containsExactlyElementsOf(repositories);
	}
	
	@Test
	public void repositoryServiceThrowsServiceExceptionWhenRemoteServiceThrowsException() throws IOException, ServiceException {
		Mockito.when(gitService.getRepositories(Mockito.anyString())).thenThrow(IOException.class);
		
		Throwable thrown = catchThrowable(() -> { 	repositoryService.getRepositories(encryptedPage ); });
		assertThat(thrown).isInstanceOf(ServiceException.class);
	}

	@Test
	public void getRepositoriesContributorsReturns2Contributors() throws Exception {
		Repository repo = new Repository(1L, "repo 1");
		repo.setOwner("Owner");
		List<Contributor> contributors = new ArrayList<>();
		Contributor contributor1 = new Contributor();
		contributor1.setLogin("contributor1");
		contributors.add(contributor1);
		Contributor contributor2 = new Contributor();
		contributor2.setLogin("contributor2");
		contributors.add(contributor2);
		Mockito.when(gitService.getContributorsByUserRepo(repo.getOwner(),repo.getName())).thenReturn(contributors);

		ContributorsResponse response = repositoryService.getContributors(repo);
		List<Contributor> returnedContributors = response.getContribuors();

		Assertions.assertEquals(contributors.get(0), returnedContributors.get(0));
		Assertions.assertEquals(contributors.get(1), returnedContributors.get(1));
	}

	@Test
	public void getRepositoriesContributorsReturnsUserError() throws IOException {
		Repository repository = new Repository(1L, "repo1");
		repository.setOwner("owner");
		Mockito.when(gitService.getContributorsByUserRepo(Mockito.anyString(), Mockito.anyString())).thenThrow(IOException.class);

		Throwable thrown = catchThrowable(() -> { 	repositoryService.getContributors(repository ); });
		assertThat(thrown).isInstanceOf(ServiceException.class);
	}
}
