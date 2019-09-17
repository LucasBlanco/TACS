package com.tacs.ResstApp.services;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;
import com.tacs.ResstApp.services.impl.GitService;

@SpringBootTest
public class GitServiceTest {
	
	GitService gitService = new GitService();

	@Test
	public void parseRepository() throws Exception {
		String json = "{\r\n" + 
				"    \"id\": 208917040,\r\n" +  
				"    \"name\": \"TACS\",\r\n" +
				"    \"stargazers_count\": 0,\r\n" + 
				"    \"language\": null,\r\n" +  
				"    \"forks_count\": 5,\r\n" + 
				"    \"open_issues_count\": 2,\r\n" + 
				"    \"subscribers_count\": 1\r\n" + 
				"}";
		
		Repository repo = gitService.parseRepository(json);
		
		Assertions.assertEquals(208917040, repo.getId());
		Assertions.assertEquals("TACS", repo.getName());
		Assertions.assertEquals(0, repo.getScore());
		Assertions.assertNull(repo.getMainLanguage());
		Assertions.assertEquals(5, repo.getNofForks());
		Assertions.assertEquals(1, repo.getNofFaved());
		Assertions.assertEquals(2, repo.getTotalIssues());
	}
}
