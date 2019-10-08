package com.tacs.ResstApp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.impl.GitService;

@SpringBootTest
public class GitServiceTest {
	
	GitService gitService = new GitService();
	
	@BeforeEach
	public void before() {
	}

	private final String jsonRepositoryExample = "{\r\n" + 
			"    \"id\": 208917040,\r\n" +  
			"    \"name\": \"TACS\",\r\n" +
			"    \"stargazers_count\": 1,\r\n" +
			"    \"language\": \"Java\",\r\n" +  
			"    \"forks_count\": 5,\r\n" + 
			"    \"open_issues_count\": 2,\r\n" + 
			"    \"owner\": {\r\n" +
			"    \"login\": \"owner\"\r\n" +
			"    },\r\n" +
			"    \"score\": 200,\r\n" +
			"    \"size\": 800,\r\n" +
			"    \"subscribers_count\": 1\r\n" +
			"}";
	private String jsonRepositoryWithNullValues = "{\r\n" + 
			"    \"id\": 208917040,\r\n" +  
			"    \"name\": \"TACS\",\r\n" +
			"    \"stargazers_count\": null,\r\n" +
			"    \"language\": null,\r\n" +  
			"    \"forks_count\": null,\r\n" + 
			"    \"open_issues_count\": null,\r\n" + 
			"    \"owner\": null,\r\n" +
			"    \"score\": null,\r\n" +
			"    \"size\": null,\r\n" +
			"    \"subscribers_count\": null\r\n" +
			"}";
	
	@Test
	public void gitServiceConvertsAJsonRepositoryToARepository() throws Exception {
		
		Repository repo = gitService.parseRepository(jsonRepositoryExample);
		
		Assertions.assertEquals(208917040, repo.getId());
		Assertions.assertEquals("TACS", repo.getName());
		Assertions.assertEquals("Java", repo.getMainLanguage());
		Assertions.assertEquals(5, repo.getNofForks());
		Assertions.assertEquals(1, repo.getStars());
		Assertions.assertEquals(2, repo.getTotalIssues());
		Assertions.assertEquals(200D, repo.getScore());
		Assertions.assertEquals(800, repo.getSize());
		Assertions.assertEquals("owner", repo.getOwner());
	}
	
	@Test
	public void gitServiceConvertsAJsonRepositoryWithNullValuesToARepository() throws Exception {
		
		Repository repo = gitService.parseRepository(jsonRepositoryWithNullValues);
		
		Assertions.assertEquals(0, repo.getScore());
		Assertions.assertNull(repo.getMainLanguage());
		Assertions.assertNull(repo.getNofForks());
		Assertions.assertEquals(0, repo.getStars());
		Assertions.assertNull(repo.getTotalIssues());
		Assertions.assertNull(repo.getSize());
		Assertions.assertNull(repo.getOwner());
	}

	
}
