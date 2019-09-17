package com.tacs.ResstApp.model.filters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tacs.ResstApp.model.Repository;

@SpringBootTest
public class SubscribersFilterTest {
	private static final int REFERENCE_SUBSCRIBER_VALUE = 10;
	SubscribersFilter subscribersFilter = new SubscribersFilter();
	
	@BeforeEach
	public void before() {
		subscribersFilter.setNofsubscribers(REFERENCE_SUBSCRIBER_VALUE);
	}
	
	@Test
	public void aSubscribersFilterValidatesARepositoryWithMoreSubscribersThanReferenceValue() {
		Repository repoWithLotsOfSubscribers = new Repository(1L, "TACS");
		repoWithLotsOfSubscribers.setStars(REFERENCE_SUBSCRIBER_VALUE + 1);
		Assertions.assertThat(subscribersFilter.filter(repoWithLotsOfSubscribers)).isTrue();
	}
	
	@Test
	public void aSubscribersFilterValidatesARepositoryWithSameSubscribersAsTheReferenceValue() {
		Repository repoWithExactAmountOfSubscribers = new Repository(1L, "TACS");
		repoWithExactAmountOfSubscribers.setStars(REFERENCE_SUBSCRIBER_VALUE);
		Assertions.assertThat(subscribersFilter.filter(repoWithExactAmountOfSubscribers)).isTrue();
	}

	@Test
	public void aSubscribersFilterDoesNotValidateARepositoryWithLessSubscribersThanReferenceValue() {
		Repository repoWithLittleCommits = new Repository(1L, "TACS");
		repoWithLittleCommits.setStars(REFERENCE_SUBSCRIBER_VALUE - 1);
		Assertions.assertThat(subscribersFilter.filter(repoWithLittleCommits)).isFalse();
	}

}
