package hu.bp.movieinfo.data.omdb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
