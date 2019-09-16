package com.tacs.ResstApp.model;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.tacs.ResstApp.model.filters.CommitsFilter;

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
		Assertions.assertThat(search.filter(repositories)).containsExactly(repo1, repo2, repo3);
	}
	
	@Test
	public void aSearchOfRepositoriesByCommitsReturnsOnlyRepositoryWithLotsOfCommits() {
		CommitsFilter commitsFilter = new CommitsFilter();
		commitsFilter.setTotalCommits(50);
		search.setCommitsFilters(commitsFilter);
		Repository repoWithLotsOfCommits = new Repository(1L, "TACS");
		repoWithLotsOfCommits.setTotalCommits(100);
		Repository repoWithLittleCommits = new Repository(2L, "TADP");
		repoWithLittleCommits.setTotalCommits(5);
		Repository repoWithoutCommits = new Repository(3L, "GDD");
		repoWithoutCommits.setTotalCommits(0);
		List<Repository> repositories = Arrays.asList(repoWithLotsOfCommits, repoWithLittleCommits, repoWithoutCommits);
		Assertions.assertThat(search.filter(repositories)).containsExactly(repoWithLotsOfCommits);
	}

}
