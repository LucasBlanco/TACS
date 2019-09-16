package com.tacs.ResstApp.model.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;

@SpringBootTest
public class CommitsFilterTest {
	private static final int REFERENCE_COMMITS_VALUE = 50;
	CommitsFilter commitsFilter = new CommitsFilter();
	
	@BeforeEach
	public void before() {
		commitsFilter.setTotalCommits(REFERENCE_COMMITS_VALUE);
	}
	
	@Test
	public void aCommitsFilterValidatesARepositoryWithMoreCommitsThanReferenceValue() {
		Repository repoWithLotsOfCommits = new Repository(1L, "TACS");
		repoWithLotsOfCommits.setTotalCommits(REFERENCE_COMMITS_VALUE + 1);
		Assertions.assertThat(commitsFilter.filter(repoWithLotsOfCommits)).isTrue();
	}
	
	@Test
	public void aCommitsFilterValidatesARepositoryWithSameCommitsAsTheReferenceValue() {
		Repository repoWithExactAmountOfCommits = new Repository(1L, "TACS");
		repoWithExactAmountOfCommits.setTotalCommits(REFERENCE_COMMITS_VALUE);
		Assertions.assertThat(commitsFilter.filter(repoWithExactAmountOfCommits)).isTrue();
	}

	@Test
	public void aCommitsFilterDoesNotValidateARepositoryWithLessCommitsThanReferenceValue() {
		Repository repoWithLittleCommits = new Repository(1L, "TACS");
		repoWithLittleCommits.setTotalCommits(REFERENCE_COMMITS_VALUE - 1);
		Assertions.assertThat(commitsFilter.filter(repoWithLittleCommits)).isFalse();
	}

}
