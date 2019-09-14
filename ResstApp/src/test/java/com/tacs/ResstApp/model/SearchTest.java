package com.tacs.ResstApp.model;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchTest {
	
	private Search search = new Search();
	CommitsFilter commitsFilter;
	
	@BeforeEach
	public void before() {
	}
	
	@Test
	public void aSearchWithoutFiltersReturnsAllRepositories() {
		Repository repo1 = new Repository(1L, "TACS");
		Repository repo2 = new Repository(2L, "TADP");
		Repository repo3 = new Repository(3L, "GDD");
		List<Repository> repositories = Arrays.asList(repo1, repo2, repo3);
		Assertions.assertThat(search.filter(repositories)).contains(repo1, repo2, repo3);
	}
	
	@Test
	public void aSearchOfRepositoriesByCommitsReturnsOnlyRepositoryWithLotsOfCommits() {
		search.setCommitsFilters(Arrays.asList(new CommitsFilter(50)));
		Repository repoWithLotsOfCommits = new Repository(1L, "TACS");
		repoWithLotsOfCommits.setNofCommits(100);
		Repository repoWithLittleCommits = new Repository(2L, "TADP");
		repoWithLittleCommits.setNofCommits(5);
		Repository repoWithoutCommits = new Repository(3L, "GDD");
		repoWithoutCommits.setNofCommits(0);
		List<Repository> repositories = Arrays.asList(repoWithLotsOfCommits, repoWithLittleCommits, repoWithoutCommits);
		Assertions.assertThat(search.filter(repositories)).contains(repoWithLotsOfCommits);
	}

}
