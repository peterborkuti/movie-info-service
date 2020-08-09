package hu.bp.movieinfo.data.movie;

import hu.bp.movieinfo.data.Movie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
