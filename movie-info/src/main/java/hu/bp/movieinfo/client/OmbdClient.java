package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Converter;
import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import hu.bp.movieinfo.data.omdb.SearchResult;
import hu.bp.movieinfo.data.omdb.SearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OmbdClient {
	private String ombdUri = "/?apikey=c177aaab&{command}={commandParam}";
	private String searchCommand = "s";
	private String detailCommand = "i";
	//http://www.omdbapi.com/?apikey=c177aaab&s=
	private WebClient client = WebClient.builder().
			baseUrl("http://www.omdbapi.com").
			build();

	public List<Movie> search(String searchString) {
		log.info("OmdbapiClient:" + searchString);

		//TODO: exception handling, use localhost to test
		SearchResult result = client.get().uri(ombdUri, searchCommand, searchString).
				exchange().block().bodyToMono(SearchResult.class).block();

		return result.getSearch().stream().
				map(this::getDetailedMovie).
				map(Converter::detailedMovieToMovie).
				collect(Collectors.toList());
	}

	private DetailedMovie getDetailedMovie(SearchedMovie movie) {
		return client.get().uri(ombdUri, detailCommand, movie.getImdbID()).
				exchange().block().bodyToMono(DetailedMovie.class).block();
	}

}
