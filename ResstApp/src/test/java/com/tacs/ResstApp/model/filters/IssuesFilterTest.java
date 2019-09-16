package com.tacs.ResstApp.model.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;

@SpringBootTest
public class IssuesFilterTest {
	private static final int REFERENCE_ISSUES_VALUE = 15;
	IssuesFilter issuesFilter = new IssuesFilter();
	
	@BeforeEach
	public void before() {
		issuesFilter.setTotalIssues(REFERENCE_ISSUES_VALUE);
	}
	
	@Test
	public void anIssuesFilterValidatesARepositoryWithMoreIssuesThanReferenceValue() {
		Repository repoWithLotsOfIssues = new Repository(1L, "TACS");
		repoWithLotsOfIssues.setTotalIssues(REFERENCE_ISSUES_VALUE + 1);
		Assertions.assertThat(issuesFilter.filter(repoWithLotsOfIssues)).isTrue();
	}
	
	@Test
	public void anIssuesFilterValidatesARepositoryWithSameIssuesAsTheReferenceValue() {
		Repository repoWithExactAmountOfIssues = new Repository(1L, "TACS");
		repoWithExactAmountOfIssues.setTotalIssues(REFERENCE_ISSUES_VALUE);
		Assertions.assertThat(issuesFilter.filter(repoWithExactAmountOfIssues)).isTrue();
	}

	@Test
	public void anIssuesFilterDoesNotValidateARepositoryWithLessIssuesThanReferenceValue() {
		Repository repoWithLittleCommits = new Repository(1L, "TACS");
		repoWithLittleCommits.setTotalIssues(REFERENCE_ISSUES_VALUE - 1);
		Assertions.assertThat(issuesFilter.filter(repoWithLittleCommits)).isFalse();
	}

}
