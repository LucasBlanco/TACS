package com.tacs.ResstApp.model;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchTest {
	
	private Search search = new Search();
	
	@BeforeEach
	public void before() {
	}
	

/*	
	@Test
	public void aSearchWithoutFiltersReturnsAllRepositories() {
		Repository repo1 = new Repository(1L, "TACS");
		Repository repo2 = new Repository(2L, "TADP");
		Repository repo3 = new Repository(3L, "GDD");
		List<Repository> repositories = Arrays.asList(repo1, repo2, repo3);
		Assertions.assertThat(search.filter(repositories)).containsExactly(repo1, repo2, repo3);
	}
*/
}
