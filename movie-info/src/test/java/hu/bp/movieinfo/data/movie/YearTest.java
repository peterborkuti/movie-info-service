package hu.bp.movieinfo.data.movie;

import hu.bp.movieinfo.data.Movie;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class YearTest {
	@Test
	public void stringSetterShouldDealWithBasicCases() {
		Movie movie = new Movie();
		assertNotNull("default should be empty", movie.getYear());

		movie.setYear(null);
		assertEquals("", movie.getYear());

		movie.setYear("");
		assertEquals("", movie.getYear());

		movie.setYear("1000");
		assertEquals("1000", movie.getYear());

		movie.setYear("2000-10-10");
		assertEquals("2000", movie.getYear());

		movie.setYear("3000.10.10");
		assertEquals("3000", movie.getYear());
	}
}
