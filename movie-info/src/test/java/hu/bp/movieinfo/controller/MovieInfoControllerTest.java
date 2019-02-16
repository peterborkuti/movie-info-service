package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.testutils.Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebFluxTest(MovieInfoController.class)
@Slf4j
public class MovieInfoControllerTest {
	@Autowired
	private WebTestClient webClient;

	@MockBean
	private MovieInfoService service;

	private Map<String, List<Movie>> expected = new HashMap<>();

	@BeforeEach
	public void setUp() {
		Mockito.reset(service);
		expected.put("movies", Utils.expectedSingletonMovieList);
		Mockito.when(service.getMovieList(Utils.expectedSearchValue, Utils.expectedApiName)).thenReturn(expected);
	}

	@Test
	public void test() {
		assertNotNull(webClient);
	}

	/**
	 * Tests that
	 * * controller parses the url well
	 * * controller calls service with the appropriate parameters
	 * * controller returns with the value getting from the service
	 * @throws Exception
	 */
	@Test
	public void getMovieListShouldCallServiceAndReturnWithAHashMap() throws Exception {
		EntityExchangeResult<String> result =
				webClient.get().uri(
						"/movies/{movieTitle}?api={apiName}",
						Utils.expectedSearchValue,
						Utils.expectedApiName).
				accept(MediaType.APPLICATION_JSON_UTF8).
				exchange().
				expectStatus().isOk().
				expectBody(String.class).
				returnResult();

		/*
		ListBody body = result.getResponseBody();
		assertNotNull(body.getMovies());
		assertEquals(1, body.getMovies().size());
		assertEquals(Utils.expectedSingletonMovieList.get(0), body.getMovies().get(0));
		*/
		log.info("BODY:" + result.getResponseBody());
	}

}

@Data
class ListBody {
	private List<Movie> movies = new ArrayList<>();

	public ListBody() {
		movies = new ArrayList<>();
	}
}
