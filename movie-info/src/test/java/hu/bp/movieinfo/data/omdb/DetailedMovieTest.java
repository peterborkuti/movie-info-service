package hu.bp.movieinfo.data.omdb;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DetailedMovieTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		DetailedMovie dm = new DetailedMovie();
		assertNotNull(dm.getDirector());
		assertNotNull(dm.getYear());
		assertNotNull(dm.getTitle());
		assertNotNull(dm.getId());
	}

}
