package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.moviedb.Credits;
import hu.bp.movieinfo.data.moviedb.SearchResult;
import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpClient;

import java.util.List;

/**
 * Gets a movie list from themoviedb.org
 *
 * It first searches the movies with a given title.
 * Themoviedb returns with the first page of the results with total number of pages
 * Then this client gets all the remaining pages to collect the movie list.
 * Unfortunately, directors are not on the result pages, so
 * it gets the "credits" list from themoviedb for all the movies to fill in director field.
 */
@Slf4j
@Component
public class MoviedbClient implements IMovieClient {
	private static final String API_KEY = "b477a38fefdc12d4c2d4e93c519d7b53";
	private static final String BASE_URL = "https://api.themoviedb.org";
	private static final String CREDITS_URL = "/3/movie/{movieId}/credits?api_key={API_KEY}";
	private static final String SEARCH_URL = "/3/search/movie?api_key={API_KEY}&query={searchString}&page={page}";

	private WebClient client;

	public MoviedbClient() {
		this.client = buildWebClient(BASE_URL);
	}

	@Override
	public String getApiName() {
		return "themoviedb";
	}

	@Override
	public List<Movie> getMovieList(String searchString) {
		return getMovieFlux(searchString).collectList().block();
	}

	@Override
	public Flux<Movie> getMovieFlux(String searchString) {
		Flux<SearchResult> pages = getAllPages(searchString);

		Flux<SearchedMovie> movies = getMovieListFromPages(pages);

		Flux<Movie> movieFlux = movies.
				map(this::searchedMovieToMovie).
				flatMap(movie -> movie.setDirectors(getDirectors(movie.getId())));

		return movieFlux;
	}


	private Flux<SearchedMovie> getMovieListFromPages(Flux<SearchResult> pages) {
		return pages.
				map(page -> page.getResults()).
				flatMap(movieList -> Flux.fromIterable(movieList));
	}

	private Movie searchedMovieToMovie(SearchedMovie sMovie) {
		Movie movie = new Movie(sMovie.getTitle(), sMovie.getRelease_date());

		movie.setId(sMovie.getId());

		return movie;
	}

	private Flux<SearchResult> getAllPages(String searchString) {
		return integersFromOneToInfinite().
				flatMap(i -> getPage(searchString, i)).
				takeUntil(sr -> sr.getTotal_pages() > sr.getPage());
	}

	private Mono<SearchResult> getPage(String searchString, Integer page) {
		Mono<SearchResult> result = Mono.just(new SearchResult());
		try {
			result = client.get().uri(SEARCH_URL, API_KEY, searchString, page).
					retrieve().
					bodyToMono(SearchResult.class);

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

	private Flux<String> getDirectors(String movieId) {
		Mono<Credits> credits = getCredits(movieId);

		Flux<String> directors =
				credits.flatMapMany(c -> Flux.fromIterable(c.getCrew())).
				filter(person -> "Director".equals(person.getJob())).
				map(director -> director.getName());

		return directors;
	}

	private Mono<Credits> getCredits(String movieId) {
		Mono<Credits> credits = Mono.just(new Credits());

		try {
			credits = client.get().uri(CREDITS_URL, movieId, API_KEY).
					retrieve().bodyToMono(Credits.class);

		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}

		return credits;
	}

	/**
	 * Creating an ssl client, so the baseUrl can start with https://
	 * @param baseUrl the base url of the service
	 * @return an ssl-enabled webclient
	 */
	// http://blog.rohrpostix.net/spring-webflux-webclient-using-ssl/
	// TODO: not for production
	private WebClient buildWebClient(String baseUrl) {
		SslProvider sslProvider =
				SslProvider.builder().sslContext(
						SslContextBuilder.forClient().
								trustManager(InsecureTrustManagerFactory.INSTANCE)
				).
		defaultConfiguration(SslProvider.DefaultConfigurationType.NONE).build();

		TcpClient tcpClient = TcpClient.create().secure(sslProvider);

		HttpClient httpClient = HttpClient.from(tcpClient);

		ClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);

		WebClient webClient =
				WebClient.builder().clientConnector(httpConnector).baseUrl(baseUrl).build();

		return webClient;
	}

	private Flux<Integer> integersFromOneToInfinite() {
		Flux<Integer> flux = Flux.generate(
				() -> 1,
				(state, sink) -> {
					sink.next(state);
					return state + 1;
				});

		return flux;
	}

}
