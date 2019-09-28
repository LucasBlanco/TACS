package com.tacs.ResstApp.model.filters;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LanguageFilterTest {
	private static final String EXAMPLE_LANGUAGE = "Java";
	private static final String PROPERTY_SEPARATOR = ":";

	LanguageFilter languageFilter = new LanguageFilter();
	
	@BeforeEach
	public void before() {
		languageFilter.setMainLanguage(EXAMPLE_LANGUAGE);
	}

	@Test
	public void aLanguageFilterValidatesARepositoryWithTheSameLanguage() {
		assertThat(languageFilter.getQueryProperty()).containsSequence(languageFilter.getPropertyName(), PROPERTY_SEPARATOR, EXAMPLE_LANGUAGE);
	}

}
