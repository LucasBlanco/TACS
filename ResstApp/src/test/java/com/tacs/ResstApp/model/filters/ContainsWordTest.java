package com.tacs.ResstApp.model.filters;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContainsWordTest {
	private static final String EXAMPLE_WORDS = "Football interface love";

	ContainsWordFilter containsWordFilter = new ContainsWordFilter();
	
	@BeforeEach
	public void before() {
		containsWordFilter.setWords(EXAMPLE_WORDS);
	}

	@Test
	public void aLanguageFilterValidatesARepositoryWithTheSameLanguage() {
		assertThat(containsWordFilter.getQueryProperty()).containsSequence(EXAMPLE_WORDS);
	}

}
