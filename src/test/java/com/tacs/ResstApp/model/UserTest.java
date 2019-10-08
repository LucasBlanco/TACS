package com.tacs.ResstApp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	
	@BeforeEach
	public void before() {
	}
	
	@Test
	public void calculateFavouriteLanguagesTest() {
		Repository repo1 = new Repository(1L, "TACS");
		repo1.setMainLanguage("C");
		Repository repo2 = new Repository(2L, "TADP");
		repo2.setMainLanguage("JAVA");
		Repository repo3 = new Repository(3L, "GDD");
		repo3.setMainLanguage("C");
		List<Repository> repositories = new ArrayList<Repository>(Arrays.asList(repo1, repo2, repo3));
		User user = new User();
		user.setId(1L);
		user.setFavourites(repositories);
		
		List<String> favLang = user.calculateFavouriteLanguages();
		Assertions.assertEquals(2,favLang.size());
		Assertions.assertTrue(favLang.contains("C"));
		Assertions.assertTrue(favLang.contains("JAVA"));
		
		user.deleteFavourite(repo2);
		favLang = user.calculateFavouriteLanguages();
		
		Assertions.assertEquals(1,favLang.size());
		Assertions.assertTrue(favLang.contains("C"));
		

		Repository repo4 = new Repository(3L, "GDD");
		repo4.setMainLanguage("PYTHON");
		user.addFavourite(repo4);
		favLang = user.calculateFavouriteLanguages();
		
		Assertions.assertEquals(2,favLang.size());
		Assertions.assertTrue(favLang.contains("C"));
		Assertions.assertTrue(favLang.contains("PYTHON"));
	}
}
