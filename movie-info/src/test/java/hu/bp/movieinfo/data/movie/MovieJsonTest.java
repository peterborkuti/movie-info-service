package hu.bp.movieinfo.data.movie;

import hu.bp.movieinfo.data.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
public class MovieJsonTest {
	private static final String ONE_DIRECTOR = "{\"Title\":\"title\",\"Year\":\"2000\",\"Director\":\"director\"}";
	private static final String TWO_DIRECTORS = "{\"Title\":\"title\",\"Year\":\"2000\",\"Director\":[\"director1\",\"director2\"]}";
	@Autowired
	private JacksonTester<Movie> json;

	@Test
	public void testSerialize() throws Exception {
		Movie movie = new Movie("title", "2000");
		movie.setDirectors(Arrays.asList("director"));

		JsonContent<Movie> jsonMovie = this.json.write(movie);

		assertEquals("one director should serialize as string", ONE_DIRECTOR, jsonMovie.getJson());
		movie.setDirectors(Arrays.asList("director1", "director2"));

		jsonMovie = this.json.write(movie);
		assertEquals("two directors should serialize as array", TWO_DIRECTORS, jsonMovie.getJson());
	}
}
