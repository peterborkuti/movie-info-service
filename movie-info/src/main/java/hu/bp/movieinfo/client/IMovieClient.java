package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Movie;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IMovieClient {
	public String getApiName();
	public List<Movie> getMovieList(String searchValue);
	public default Flux<Movie> getMovieFlux(String searchValue) {
		return Flux.just(new Movie());
	}
}
