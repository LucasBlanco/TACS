package com.tacs.ResstApp.model.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;

@SpringBootTest
public class LanguageFilterTest {
	private static final String EXAMPLE_LANGUAGE = "Java";
	LanguageFilter languageFilter = new LanguageFilter();
	
	@BeforeEach
	public void before() {
		languageFilter.setMainLanguage(EXAMPLE_LANGUAGE);
	}
	
	@Test
	public void aLanguageFilterValidatesARepositoryWithTheSameLanguage() {
		Repository javaRepository = new Repository(1L, "TACS");
		javaRepository.setMainLanguage(EXAMPLE_LANGUAGE);
		Assertions.assertThat(languageFilter.filter(javaRepository)).isTrue();
	}
	
	@Test
	public void aLanguageFilterDoesNotValidateARepositoryWithADifferentLanguage() {
		Repository groovyRepository = new Repository(1L, "TACS");
		groovyRepository.setMainLanguage("Groovy");
		Assertions.assertThat(languageFilter.filter(groovyRepository)).isFalse();
	}

	@Test
	public void aLanguageFilterDoesNotValidateARepositoryWithEmptyLanguage() {
		Repository emptyLanguageRepository = new Repository(1L, "TACS");
		Assertions.assertThat(languageFilter.filter(emptyLanguageRepository)).isFalse();
	}

}
