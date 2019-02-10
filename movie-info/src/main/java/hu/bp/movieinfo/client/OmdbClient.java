package hu.bp.movieinfo.client;

import hu.bp.movieinfo.MovieInfoConfigurationProperties;
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
public class OmdbClient implements IMovieClient {
	private static String OMDBAPI_URL = "/?apikey={API_KEY}&{command}={commandParam}";
	private static String SEARCH_COMMAND = "s";
	private static String DETAIL_COMMAND = "i";
	private static String BASE_URL = "http://www.omdbapi.com";

	private final String API_KEY;
	private final WebClient client;

	public OmdbClient(MovieInfoConfigurationProperties properties) {
		API_KEY = properties.getOmdbapi_api_key();

		client = WebClient.builder().
				baseUrl(BASE_URL).
				build();
	}

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
					uri(OMDBAPI_URL, API_KEY, SEARCH_COMMAND, searchString).
					retrieve().bodyToMono(SearchResult.class);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

	private Mono<DetailedMovie> getMovieDetails(SearchedMovie movie) {
		Mono<DetailedMovie> dMovie = Mono.just(new DetailedMovie());

		try {
			return client.get().uri(OMDBAPI_URL, API_KEY, DETAIL_COMMAND, movie.getImdbID()).
					retrieve().bodyToMono(DetailedMovie.class);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return dMovie;
	}

}
