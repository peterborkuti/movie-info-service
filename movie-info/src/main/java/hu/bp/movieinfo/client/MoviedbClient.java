package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Converter;
import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.moviedb.Credits;
import hu.bp.movieinfo.data.moviedb.Crew;
import hu.bp.movieinfo.data.moviedb.SearchResult;
import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
	private static final int LIMIT_PAGE_REQUESTS = 3;
	private static final int LIMIT_MOVIES = 100;

	private static final String BASE_URL = "https://api.themoviedb.org";
	private static final String API_KEY = "b477a38fefdc12d4c2d4e93c519d7b53";
	private static final String SEARCH_URL = "/3/search/movie?api_key={API_KEY}&query={searchString}&page={page}";
	private static final String CREDITS_URL = "/3/movie/{movieId}/credits?api_key={API_KEY}";
	private WebClient client;

	public MoviedbClient() {
		this.client = buildWebClient(BASE_URL);
	}

	@Override
	public String getApiName() {
		return "themoviedb";
	}

	//TODO: exception handling, use localhost to test
	@Override
	public List<Movie> getMovieList(String searchString) {
		Flux<SearchResult> pages = getPageFlux(searchString);;

		Flux<SearchedMovie> movies = getMovieListFromPages(pages);

		Stream<Movie> moviesWithDirectors =
				movies.toStream().
				map(movie ->
						Converter.MoviedbSearchedMovieToMovie(movie, getDirectors(movie.getId()))
				);

		return moviesWithDirectors.collect(Collectors.toList());
	}


	private Flux<SearchedMovie> getMovieListFromPages(Flux<SearchResult> pages) {
		return pages.
				map(page -> page.getResults()).
				flatMap(movieList -> Flux.fromIterable(movieList));
	}

	@Override
	public Flux<Movie> getMovieFlux(String searchString) {
		Flux<SearchResult> pages = getPageFlux(searchString);;

		Flux<SearchedMovie> movies = getMovieListFromPages(pages);

		Flux<Movie> movieFlux = movies.
				map(movie ->
						Converter.MoviedbSearchedMovieToMovie(
								movie,
								getDirectors(movie.getId())));

		return movieFlux;
	}

	//TODO: exception handling, use localhost to test
	private SearchResult getResultPage(String searchString, Integer page) {
		SearchResult result = new SearchResult();
		try {
			result = client.get().uri(SEARCH_URL, API_KEY, searchString, page).
					retrieve().
					bodyToMono(SearchResult.class).block();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return result;
	}

	private Flux<SearchResult> getPageFlux(String searchString) {
		return integersFromOneToIninite().
				flatMap(i -> getMonoResultPage(searchString, i)).
				takeUntil(sr -> sr.getTotal_pages() > sr.getPage());
	}

	private Mono<SearchResult> getMonoResultPage(String searchString, Integer page) {
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

	private List<String> getDirectors(Integer movieId) {
		Credits credits = getCredits(movieId);

		if (credits == null) {
			log.info("credits is null for " + movieId);
			return new ArrayList<String>();
		}

		List<Crew> crew = credits.getCrew();

		if (crew == null) {
			log.info("crew is null for " + movieId);
			return new ArrayList<String>();
		}

		if (crew.isEmpty()) {
			log.info("crew is empty for " + movieId);
			return new ArrayList<String>();
		}

		List<String> directors = crew.stream().
				filter(person -> "Director".equals(person.getJob())).
				map(director -> director.getName()).
				collect(Collectors.toList());

		return directors;
	}

	private Credits getCredits(Integer movieId) {
		Credits credits = new Credits();

		try {
			credits = client.get().uri(CREDITS_URL, movieId, API_KEY).
					retrieve().bodyToMono(Credits.class).block();
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

	private Flux<Integer> integersFromOneToIninite() {
		Flux<Integer> flux = Flux.generate(
				() -> 1,
				(state, sink) -> {
					sink.next(state);
					if (state == 10) {
						sink.complete();
					}
					return state + 1;
				});

		return flux;
	}

}
