package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.data.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value="movies")
public class MovieInfoController {
	private MovieInfoService service;

	public MovieInfoController(MovieInfoService service) {
		this.service = service;
	}

	@GetMapping(value = "/synchron/{api}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, List<Movie>> getMovieList(
			@PathVariable String api, @RequestParam("title") String title) {

		return service.getMovieList(title, api);
	}

	@GetMapping(value = "/flux/{api}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Movie> getMovieFlux(
			@PathVariable String api, @RequestParam("title") String title) {
		return service.getMovieFlux(title, api);
	}
}
