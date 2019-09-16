package com.tacs.ResstApp.model.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;

@SpringBootTest
public class ScoreFilterTest {
	private static final Double REFERENCE_SCORE_VALUE = 10D;
	ScoreFilter scoreFilter = new ScoreFilter();
	
	@BeforeEach
	public void before() {
		scoreFilter.setRepositoryScore(REFERENCE_SCORE_VALUE);
	}
	
	@Test
	public void aScoreFilterValidatesARepositoryWithMoreScoreThanReferenceValue() {
		Repository repoWithGreaterScore = new Repository(1L, "TACS");
		repoWithGreaterScore.setScore(REFERENCE_SCORE_VALUE + 0.1D);
		Assertions.assertThat(scoreFilter.filter(repoWithGreaterScore)).isTrue();
	}
	
	@Test
	public void aScoreFilterValidatesARepositoryWithSameScoreAsTheReferenceValue() {
		Repository repoWithSameScore = new Repository(1L, "TACS");
		repoWithSameScore.setScore(REFERENCE_SCORE_VALUE);
		Assertions.assertThat(scoreFilter.filter(repoWithSameScore)).isTrue();
	}

	@Test
	public void aScoreFilterDoesNotValidateARepositoryWithLessScoreThanReferenceValue() {
		Repository repoWithLowerScore = new Repository(1L, "TACS");
		repoWithLowerScore.setScore(REFERENCE_SCORE_VALUE - 0.1D);
		Assertions.assertThat(scoreFilter.filter(repoWithLowerScore)).isFalse();
	}

}
