package com.tacs.ResstApp.model.filters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StarsFilterTest {
	private static final Integer MIN_REFERENCE_STARS_VALUE = 10;
	private static final Integer MAX_REFERENCE_STARS_VALUE = 20;
	private static final String DEFAULT_VALUE = "*";
	private static final String RANGE_SEPARATOR = "..";
	StarsFilter starsFilter = new StarsFilter();
	
	@BeforeEach
	public void before() {
	}
	
	@Test
	public void aStarsFilterWithOnlyMaxStarsReturnsQueryWithDefaultValueInMinValuePosition() {
		starsFilter.setMaxStars(MAX_REFERENCE_STARS_VALUE);
		assertThat(starsFilter.getQueryProperty()).containsSequence(DEFAULT_VALUE, RANGE_SEPARATOR, MAX_REFERENCE_STARS_VALUE.toString());
	}
	
	@Test
	public void aStarsFilterWithOnlyMinStarsReturnsQueryWithDefaultValueInMaxValuePosition() {
		starsFilter.setMinStars(MIN_REFERENCE_STARS_VALUE);
		assertThat(starsFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_STARS_VALUE.toString(), RANGE_SEPARATOR, DEFAULT_VALUE);
	}

	@Test
	public void aStarsFilterWithMaxAndMinValuesReturnsQueryWithBothValuesInRange() {
		starsFilter.setMinStars(MIN_REFERENCE_STARS_VALUE);
		starsFilter.setMaxStars(MAX_REFERENCE_STARS_VALUE);
		assertThat(starsFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_STARS_VALUE.toString(), RANGE_SEPARATOR, MAX_REFERENCE_STARS_VALUE.toString());
	}
}
