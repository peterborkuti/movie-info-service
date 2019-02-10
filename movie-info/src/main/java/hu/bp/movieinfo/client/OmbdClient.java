package hu.bp.movieinfo.client;

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

	public List<Movie> getMovieList(String searchString) {
		return getMovieFlux(searchString).collectList().block();
	}

	public Flux<Movie> getMovieFlux(String searchString) {
		Mono<SearchResult> searchResult = getPage(searchString);

		Flux<SearchedMovie> imdbIds =
				searchResult.map(SearchResult::getSearch).
				flatMapMany(Flux::fromIterable);

		Flux<DetailedMovie> detailedMovieFlux =
				imdbIds.flatMap(this::getMovieDetails);

		Flux<Movie> movieFlux = detailedMovieFlux.map(movie -> movie.toMovie());

		return movieFlux;
	}

	private Mono<SearchResult> getPage(String searchString) {
		Mono<SearchResult> result = Mono.just(new SearchResult());

		try {
			result = client.get().
					uri(ombdUri, searchCommand, searchString).
					retrieve().bodyToMono(SearchResult.class);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

	private Mono<DetailedMovie> getMovieDetails(SearchedMovie movie) {
		Mono<DetailedMovie> dMovie = Mono.just(new DetailedMovie());

		try {
			return client.get().uri(ombdUri, detailCommand, movie.getImdbID()).
					retrieve().bodyToMono(DetailedMovie.class);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dMovie;
	}

}
