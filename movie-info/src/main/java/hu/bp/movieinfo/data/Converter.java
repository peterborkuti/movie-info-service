package hu.bp.movieinfo.data;

import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import hu.bp.movieinfo.data.omdb.DetailedMovie;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Converter {
	public static Movie detailedMovieToMovie(DetailedMovie movie) {
		String director = movie.getDirector();

		String[] directors = (director == null) ? new String[]{""} : director.split(",");

		return new Movie(movie.getTitle(), movie.getYear(), Arrays.asList(directors));
	}

	public static Mono<Movie> asyncDetailedMovieToMovie(DetailedMovie detailedMovie) {
		String director = detailedMovie.getDirector();

		Movie movie = new Movie(detailedMovie.getTitle(), detailedMovie.getYear(), director);

		return Mono.just(movie);
	}

	public static Movie MoviedbSearchedMovieToMovie(SearchedMovie movie, List<String> directors) {
		return new Movie(movie.getTitle(), release_dateToYear(movie.getRelease_date()), directors);
	}

	//TODO: check if release_date is localized or not. This code works only with YYYY-...
	public static Integer release_dateToYear(String release_date) {
		if (release_date == null || "".equals(release_date)) {
			return 0;
		}

		String yearPart = release_date.split("-")[0];
		Integer year = 0;

		try {
			year = Integer.parseInt(yearPart);
		} catch (NumberFormatException e){
			log.info(e.getMessage() + " release_date is " + release_date);
		}

		return year;
	}
}
