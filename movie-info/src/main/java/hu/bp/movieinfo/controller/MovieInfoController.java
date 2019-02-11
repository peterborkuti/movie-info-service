package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.client.ClientFactory;
import hu.bp.movieinfo.client.IMovieClient;
import hu.bp.movieinfo.data.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value="movies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MovieInfoController {
	private MovieInfoControllerLogic logic;

	public MovieInfoController(MovieInfoControllerLogic logic) {
		this.logic = logic;
	}

	@GetMapping(value = "/{movieTitle}")
	public Map<String, List<Movie>> getMovieList(
			@PathVariable String movieTitle, @RequestParam("api") String apiName) {

		return logic.getMovieList(movieTitle, apiName);
	}

	@GetMapping(value = "/flux/{movieTitle}")
	public Flux<Movie> getMovieFlux(
			@PathVariable String movieTitle, @RequestParam("api") String apiName) {
		return logic.getMovieFlux(movieTitle, apiName);
	}
}
