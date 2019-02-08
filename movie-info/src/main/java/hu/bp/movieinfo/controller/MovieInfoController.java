package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.client.MoviedbClient;
import hu.bp.movieinfo.client.OmbdClient;
import hu.bp.movieinfo.data.Movies;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import hu.bp.movieinfo.data.omdb.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequestMapping("movies")
public class MovieInfoController {

	@Autowired
	private OmbdClient ombdClient;

	@Autowired
	private MoviedbClient moviedbClient;

	@GetMapping(value = "/{movieTitle}")
	public Movies getMovieInfo(@PathVariable String movieTitle, @RequestParam("api") String apiName) {
		log.info("getMovieInfo:" + movieTitle + "," + apiName);
		//return new Movies(ombdClient.search(movieTitle));
		return new Movies(moviedbClient.search(movieTitle));
	}
}
