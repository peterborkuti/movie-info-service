package hu.bp.movieinfo.data.moviedb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchedMovieTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchedMovie sm = new SearchedMovie();
		assertNotNull(sm.getId());
		assertNotNull(sm.getRelease_date());
		assertNotNull(sm.getTitle());
	}
}
