package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.bp.movieinfo.data.Movie;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

	@Test
	public void toMovieShouldDealWithSimpleCases() throws NoSuchFieldException {
		DetailedMovie dm = new DetailedMovie();

		String director1 = "director1";
		String director2 = "director2";

		String directors = director1 + "," + director2;
		String year = "year";
		String title = "title";
		String id = "id";

		dm.setDirector(directors);
		dm.setTitle(title);
		dm.setYear(year);
		dm.setId(id);

		Movie movie = dm.toMovie();

		assertEquals(title, movie.getTitle());
		assertEquals(year, movie.getYear());
		assertEquals(id, movie.getId());

		assertEquals(2, movie.getDirectors().size());
		assertEquals(director1, movie.getDirectors().get(0));
		assertEquals(director2, movie.getDirectors().get(1));
	}
}
