package com.tacs.ResstApp.model.filters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForksFilterTest {
	private static final Integer MIN_REFERENCE_FORKS_VALUE = 10;
	private static final Integer MAX_REFERENCE_FORKS_VALUE = 20;
	private static final String DEFAULT_VALUE = "*";
	private static final String RANGE_SEPARATOR = "..";
	ForksFilter forksFilter = new ForksFilter();
	
	@BeforeEach
	public void before() {
	}
	
	@Test
	public void aForksFilterWithOnlyMaxForksReturnsQueryWithDefaultValueInMinValuePosition() {
		forksFilter.setMaxForks(MAX_REFERENCE_FORKS_VALUE);
		assertThat(forksFilter.getQueryProperty()).containsSequence(DEFAULT_VALUE, RANGE_SEPARATOR, MAX_REFERENCE_FORKS_VALUE.toString());
	}
	
	@Test
	public void aForksFilterWithOnlyMinForksReturnsQueryWithDefaultValueInMaxValuePosition() {
		forksFilter.setMinForks(MIN_REFERENCE_FORKS_VALUE);
		assertThat(forksFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_FORKS_VALUE.toString(), RANGE_SEPARATOR, DEFAULT_VALUE);
	}

	@Test
	public void aForksFilterWithMaxAndMinValuesReturnsQueryWithBothValuesInRange() {
		forksFilter.setMinForks(MIN_REFERENCE_FORKS_VALUE);
		forksFilter.setMaxForks(MAX_REFERENCE_FORKS_VALUE);
		assertThat(forksFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_FORKS_VALUE.toString(), RANGE_SEPARATOR, MAX_REFERENCE_FORKS_VALUE.toString());
	}
}
