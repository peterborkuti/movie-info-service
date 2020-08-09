package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.testutils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class MovieInfoServiceTest {
	private final List<Movie> expected = Arrays.asList(new Movie("title", "2000"));
	private MovieInfoService service;

	@BeforeEach
	public void setUpLogicFactory() {
		service =
			new MovieInfoService(
				Utils.getClientFactoryWithMockClient("apiname", "title", expected)
			);
	}

	/**
	 * service should call ClientFactory with the given apiname
	 * service should call client.getMovieList with the appropriate search value
	 * service should return with the list got from client.getMovieList in Map
	 * with key "movies"
	 */
	@Test
	public void whenCallingWithAppropriateApiItshouldReturnWithClientsMovieList() {
		Map<String, List<Movie>> returned =
				service.getMovieList("title", "apiname");

		assertEquals(1, returned.keySet().size());
		assertTrue("size", returned.keySet().contains("movies"));
		assertEquals(expected, returned.get("movies"));
	}
}