package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.client.ClientFactory;
import hu.bp.movieinfo.client.IMovieClient;
import hu.bp.movieinfo.data.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("movies")
public class MovieInfoController {
	@GetMapping(value = "/{movieTitle}")
	public Map<String, List<Movie>> getMovieList(
			@PathVariable String movieTitle, @RequestParam("api") String apiName) {

		log.info("getMovieInfo:" + movieTitle + "," + apiName);
		IMovieClient client = ClientFactory.get(apiName);

		Map<String, List<Movie>> movies = new HashMap<>();

		movies.put("movies", client.getMovieList(movieTitle));

		return movies;
	}

	@GetMapping(value = "/flux/{movieTitle}")
	public Flux<Movie> getMovieFlux(
			@PathVariable String movieTitle, @RequestParam("api") String apiName) {

		log.info("getFluxMovieInfo:" + movieTitle + "," + apiName);
		IMovieClient client = ClientFactory.get(apiName);

		return client.getMovieFlux(movieTitle);
	}
}
