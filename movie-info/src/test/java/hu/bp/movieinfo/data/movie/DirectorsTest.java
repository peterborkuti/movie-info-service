package hu.bp.movieinfo.data.movie;

import hu.bp.movieinfo.data.Movie;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DirectorsTest {
	@Test
	public void stringSetterShouldDealWithBasicCases() {
		Movie movie = new Movie();
		assertNotNull(movie.getDirectors());

		movie.setDirectors((String)null);
		assertNotNull(movie.getDirectors());
		assertEquals(0, movie.getDirectors().size());

		movie.setDirectors("");
		assertEquals(1, movie.getDirectors().size());
		assertEquals("", movie.getDirectors().get(0));

		String director = "director";
		movie.setDirectors(director);
		assertEquals(1, movie.getDirectors().size());
		assertEquals(director, movie.getDirectors().get(0));

		String director1 = "director1";
		String director2 = "director2";
		movie.setDirectors(director1 + "," + director2);
		assertEquals(2, movie.getDirectors().size());
		assertEquals(director1, movie.getDirectors().get(0));
		assertEquals(director2, movie.getDirectors().get(1));
	}

	@Test
	public void listSetterShouldDealWithBasicCases() {
		Movie movie = new Movie();

		movie.setDirectors((List<String>)null);
		assertNotNull(movie.getDirectors());
		assertEquals(0, movie.getDirectors().size());

		movie.setDirectors(Arrays.asList(""));
		assertEquals(1, movie.getDirectors().size());
		assertEquals("", movie.getDirectors().get(0));

		String director = "director";
		movie.setDirectors(Arrays.asList(director));
		assertEquals(1, movie.getDirectors().size());
		assertEquals(director, movie.getDirectors().get(0));

		String director1 = "director1";
		String director2 = "director2";
		List<String> list = Arrays.asList(director1, director2);
		movie.setDirectors(list);
		assertEquals(2, movie.getDirectors().size());
		assertEquals(director1, movie.getDirectors().get(0));
		assertEquals(director2, movie.getDirectors().get(1));

		list.set(0, "other");
		assertEquals(
				director1, movie.getDirectors().get(0), "Movie should copy list-values not only references");
	}

	@Test
	public void fluxSetterShouldDealWithBasicCases() {
		Movie movie = new Movie();

		movie.setDirectors(Flux.empty());
		assertNotNull(movie.getDirectors());
		assertEquals(0, movie.getDirectors().size());

		movie.setDirectors(Flux.just(""));
		assertEquals(1, movie.getDirectors().size());
		assertEquals("", movie.getDirectors().get(0));

		String director = "director";
		movie.setDirectors(Flux.just(director));
		assertEquals(1, movie.getDirectors().size());
		assertEquals(director, movie.getDirectors().get(0));

		String director1 = "director1";
		String director2 = "director2";
		Flux<String> flux = Flux.just(director1, director2);
		movie.setDirectors(flux);
		assertEquals(2, movie.getDirectors().size());
		assertEquals(director1, movie.getDirectors().get(0));
		assertEquals(director2, movie.getDirectors().get(1));
	}

	@Test
	public void addDirectorCases() {
		Movie movie = new Movie();

		movie.addDirector(null);
		assertEquals(1, movie.getDirectors().size());
		assertEquals("", movie.getDirectors().get(0));

		String director = "director";
		movie.addDirector(director);
		assertEquals(2, movie.getDirectors().size());
		assertEquals("", movie.getDirectors().get(0));
		assertEquals(director, movie.getDirectors().get(1));
	}
}
