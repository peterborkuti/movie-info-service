package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.data.Movie;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(MovieInfoController.class)
@Slf4j
public class MovieInfoControllerTest {
	@Autowired
	private WebTestClient webClient;

	@MockBean
	private MovieInfoControllerLogic logic;

	@Before
	public void setUp() {
		reset(logic);
	}

	@Test
	public void getMovieListShouldCallLogicAndReturnWithAHashMap() throws Exception {
		String movieTitle = "title";
		String apiName = "api";

		Movie movie1 = new Movie("title1", "1000");
		Movie movie2 = new Movie("title2", "2000");

		Map<String, List<Movie>> map = new HashMap<>();
		map.put("movies", Arrays.asList(movie1, movie2));

		when(logic.getMovieList(movieTitle, apiName)).thenReturn(map);

		EntityExchangeResult<ListBody> result =
				webClient.get().uri("/movies/{movieTitle}?api={apiName}", movieTitle, apiName).
				accept(MediaType.APPLICATION_JSON).
				exchange().
				expectStatus().isOk().
				expectBody(ListBody.class).
				returnResult();

		ListBody body = result.getResponseBody();
		assertNotNull(body.getMovies());
		assertEquals(2, body.getMovies().size());
		assertEquals(movie1, body.getMovies().get(0));
		assertEquals(movie2, body.getMovies().get(1));
	}

}

@Data
class ListBody {
	private List<Movie> movies = new ArrayList<>();
}
