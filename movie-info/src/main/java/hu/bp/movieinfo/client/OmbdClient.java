package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Converter;
import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import hu.bp.movieinfo.data.omdb.SearchResult;
import hu.bp.movieinfo.data.omdb.SearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OmbdClient implements IMovieClient {
	private String ombdUri = "/?apikey=c177aaab&{command}={commandParam}";
	private String searchCommand = "s";
	private String detailCommand = "i";
	//http://www.omdbapi.com/?apikey=c177aaab&s=
	private WebClient client = WebClient.builder().
			baseUrl("http://www.omdbapi.com").
			build();

	@Override
	public String getApiName() {
		return "omdbapi";
	}

	//TODO: exception handling, use localhost to test
	public List<Movie> getMovieList(String searchString) {
		log.info("OmdbapiClient:" + searchString);

		SearchResult result = client.get().uri(ombdUri, searchCommand, searchString).
				exchange().block().bodyToMono(SearchResult.class).block();

		return result.getSearch().stream().
				map(this::getDetailedMovie).
				map(Converter::detailedMovieToMovie).
				collect(Collectors.toList());
	}

	//TODO: exception handling, use localhost to test
	public Flux<Movie> getMovieFlux(String searchString) {
		Mono<SearchResult> searchResult =
				client.get().
				uri(ombdUri, searchCommand, searchString).
				retrieve().bodyToMono(SearchResult.class);

		Flux<SearchedMovie> imdbIds =
				searchResult.map(SearchResult::getSearch).
				flatMapMany(Flux::fromIterable);

		Flux<DetailedMovie> detailedMovieFlux =
				imdbIds.flatMap(this::getMonoDetailedMovie);

		Flux<Movie> movieFlux = detailedMovieFlux.flatMap(Converter::asyncDetailedMovieToMovie);

		return movieFlux;
	}

	private Mono<DetailedMovie> getMonoDetailedMovie(SearchedMovie movie) {
		return client.get().uri(ombdUri, detailCommand, movie.getImdbID()).
				retrieve().bodyToMono(DetailedMovie.class);
	}

	private DetailedMovie getDetailedMovie(SearchedMovie movie) {
		return client.get().uri(ombdUri, detailCommand, movie.getImdbID()).
				exchange().block().bodyToMono(DetailedMovie.class).block();
	}

}
