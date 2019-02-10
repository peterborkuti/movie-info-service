package hu.bp.movieinfo.controller;

import hu.bp.movieinfo.client.ClientFactory;
import hu.bp.movieinfo.client.IMovieClient;
import hu.bp.movieinfo.data.Movie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieInfoControllerLogic {
	private ClientFactory clientFactory;

	public MovieInfoControllerLogic(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public Map<String, List<Movie>> getMovieList(String movieTitle, String apiName) {
		IMovieClient client = clientFactory.get(apiName);

		Map<String, List<Movie>> movies = new HashMap<>();

		movies.put("movies", client.getMovieList(movieTitle));

		return movies;
	}

	public Flux<Movie> getMovieFlux(String movieTitle, String apiName) {
		IMovieClient client = clientFactory.get(apiName);

		return client.getMovieFlux(movieTitle);
	}
}

