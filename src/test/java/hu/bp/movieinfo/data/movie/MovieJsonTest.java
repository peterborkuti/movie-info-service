package hu.bp.movieinfo.data.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bp.movieinfo.data.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class MovieJsonTest {
	private static final String ONE_DIRECTOR = "{\"Title\":\"title\",\"Year\":\"2000\",\"Director\":\"director\"}";
	private static final String TWO_DIRECTORS = "{\"Title\":\"title\",\"Year\":\"2000\",\"Director\":[\"director1\",\"director2\"]}";

	private JacksonTester<Movie> json;

	@BeforeEach
	public void setUp() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void testSerialize() throws Exception {
		Movie movie = new Movie("title", "2000");
		movie.setDirectors(Arrays.asList("director"));

		JsonContent<Movie> jsonMovie = this.json.write(movie);

		assertEquals(ONE_DIRECTOR, jsonMovie.getJson(), "one director should serialize as string");
		movie.setDirectors(Arrays.asList("director1", "director2"));

		jsonMovie = this.json.write(movie);
		assertEquals(TWO_DIRECTORS, jsonMovie.getJson(), "two directors should serialize as array");
	}
}
