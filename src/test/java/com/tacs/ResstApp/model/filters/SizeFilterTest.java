package com.tacs.ResstApp.model.filters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SizeFilterTest {
	private static final Integer MIN_REFERENCE_SIZE_VALUE = 10;
	private static final Integer MAX_REFERENCE_SIZE_VALUE = 20;
	private static final String DEFAULT_VALUE = "*";
	private static final String RANGE_SEPARATOR = "..";
	SizeFilter sizeFilter = new SizeFilter();
	
	@BeforeEach
	public void before() {
	}
	
	@Test
	public void aSizeFilterWithOnlyMaxSizeReturnsQueryWithDefaultValueInMinValuePosition() {
		sizeFilter.setMaxRepositorySize(MAX_REFERENCE_SIZE_VALUE);
		assertThat(sizeFilter.getQueryProperty()).containsSequence(DEFAULT_VALUE, RANGE_SEPARATOR, MAX_REFERENCE_SIZE_VALUE.toString());
	}
	
	@Test
	public void aSizeFilterWithOnlyMinSizeReturnsQueryWithDefaultValueInMaxValuePosition() {
		sizeFilter.setMinRepositorySize(MIN_REFERENCE_SIZE_VALUE);
		assertThat(sizeFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_SIZE_VALUE.toString(), RANGE_SEPARATOR, DEFAULT_VALUE);
	}

	@Test
	public void aSizeFilterWithMaxAndMinValuesReturnsQueryWithBothValuesInRange() {
		sizeFilter.setMinRepositorySize(MIN_REFERENCE_SIZE_VALUE);
		sizeFilter.setMaxRepositorySize(MAX_REFERENCE_SIZE_VALUE);
		assertThat(sizeFilter.getQueryProperty()).containsSequence(MIN_REFERENCE_SIZE_VALUE.toString(), RANGE_SEPARATOR, MAX_REFERENCE_SIZE_VALUE.toString());
	}

}
