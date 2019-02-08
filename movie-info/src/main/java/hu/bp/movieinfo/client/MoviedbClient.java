package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Converter;
import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.moviedb.Credits;
import hu.bp.movieinfo.data.moviedb.Crew;
import hu.bp.movieinfo.data.moviedb.SearchResult;
import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class MoviedbClient {
	// search: https://api.themoviedb.org/3/search/movie?api_key=b477a38fefdc12d4c2d4e93c519d7b53&query=day
	//credits: https://api.themoviedb.org/3/movie/602/credits?api_key=b477a38fefdc12d4c2d4e93c519d7b53

	private WebClient client;

	public MoviedbClient() {
		this.client = buildWebClient("https://api.themoviedb.org");
	}

	// http://blog.rohrpostix.net/spring-webflux-webclient-using-ssl/
	// TODO: not for production
	private WebClient buildWebClient(String baseUrl) {
		SslProvider sslProvider =
				SslProvider.builder().sslContext(
					SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
				).
				defaultConfiguration(SslProvider.DefaultConfigurationType.NONE).build();

		TcpClient tcpClient = TcpClient.create().secure(sslProvider);
		HttpClient httpClient = HttpClient.from(tcpClient);
		ClientHttpConnector httpConnector = new ReactorClientHttpConnector(httpClient);

		WebClient webClient = WebClient.builder().clientConnector(httpConnector).baseUrl(baseUrl).build();

		return webClient;
	}

	public List<Movie> search(String searchString) {
		log.info("MoviedbClient:" + searchString);
		String search = "/3/search/movie?api_key=b477a38fefdc12d4c2d4e93c519d7b53&query={searchString}";
		//TODO: exception handling, use localhost to test
		SearchResult result = getResultPage(searchString, 1);

		log.info("ResultSize:" + result.getResults().size());

		List<SearchedMovie> movies = new ArrayList<>(result.getResults());

		movies.addAll(
			IntStream.range(2, result.getTotal_pages()).limit(3).boxed().
					map(page -> getResultPage(searchString, page)).
					flatMap(resultPage -> resultPage.getResults().stream()).
					collect(Collectors.toList())
		);

		return movies.stream().
				map(movie -> Converter.MoviedbSearchedMovieToMovie(movie, getDirectors(movie.getId()))).
				collect(Collectors.toList());
	}

	private SearchResult getResultPage(String searchString, Integer page) {
		String search = "/3/search/movie?api_key=b477a38fefdc12d4c2d4e93c519d7b53&query={searchString}&page={page}";
		//TODO: exception handling, use localhost to test

		SearchResult result = client.get().uri(search, searchString, page).
				retrieve().bodyToMono(SearchResult.class).block();

		return result;
	}

	private static void logResponse(ClientResponse response) {
		log.info("logresponse - status:" + response.statusCode().getReasonPhrase());
		response.toEntity(String.class).doOnSuccess(r -> log.info("logresponse - body" + r.getBody()));
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
		String creditsUri = "/3/movie/{movieId}/credits?api_key=b477a38fefdc12d4c2d4e93c519d7b53";

		Credits credits = client.get().uri(creditsUri, movieId).
				exchange().block().bodyToMono(Credits.class).block();

		return credits;
	}

}
