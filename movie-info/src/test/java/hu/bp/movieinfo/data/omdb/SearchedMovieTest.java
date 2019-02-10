package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SearchedMovieTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchedMovie sm = new SearchedMovie();
		assertNotNull(sm.getImdbID());
	}
}
