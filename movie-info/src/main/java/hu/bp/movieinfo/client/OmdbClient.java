package hu.bp.movieinfo.client;

import hu.bp.movieinfo.MovieInfoConfigurationProperties;
import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.omdb.Converter;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import hu.bp.movieinfo.data.omdb.SearchResult;
import hu.bp.movieinfo.data.omdb.SearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Gets movie list from omdb.
 * First it searches after a title. Omdb returns with one page with a movie list.
 * Then it gets the detailed movie-data using its imdb id.
 * After converting to the output format it returns with the movie list.
 */
@Slf4j
@Component
public class OmdbClient implements IMovieClient {
	private static String OMDBAPI_URL = "/?apikey={API_KEY}&{command}={commandParam}";
	private static String SEARCH_COMMAND = "s";
	private static String DETAIL_COMMAND = "i";
	private static String BASE_URL = "http://www.omdbapi.com";

	private final String API_KEY;
	private final WebClient client;
	private final WebClientHelper helper;

	public OmdbClient(MovieInfoConfigurationProperties properties, WebClientHelper helper) {
		API_KEY = properties.getOmdbapi_api_key();

		client = WebClient.builder().
				baseUrl(BASE_URL).
				build();

		this.helper = helper;
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

		Flux<Movie> movieFlux = detailedMovieFlux.map(detailedMovie -> Converter.toMovie(detailedMovie, new Movie()));

		return movieFlux;
	}

	private Mono<SearchResult> getPage(String searchString) {
		return helper.webGet(
					client,
					OMDBAPI_URL,
					new String[] {API_KEY, SEARCH_COMMAND, searchString},
					SearchResult.class,
					Mono.just(new SearchResult())
				);
	}

	private Mono<DetailedMovie> getMovieDetails(SearchedMovie movie) {
		return helper.webGet(
				client,
				OMDBAPI_URL,
				new String[] {API_KEY, DETAIL_COMMAND, movie.getImdbID()},
				DetailedMovie.class,
				Mono.just(new DetailedMovie())
		);
	}

}
