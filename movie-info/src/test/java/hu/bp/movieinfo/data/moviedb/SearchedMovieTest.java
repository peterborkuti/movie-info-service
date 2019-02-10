package hu.bp.movieinfo.data.moviedb;

import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchedMovieTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchedMovie sm = new SearchedMovie();
		assertNotNull(sm.getId());
		assertNotNull(sm.getRelease_date());
		assertNotNull(sm.getTitle());
	}
}
