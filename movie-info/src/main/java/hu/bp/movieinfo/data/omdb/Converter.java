package hu.bp.movieinfo.data.omdb;

import hu.bp.movieinfo.data.Movie;

public class Converter {
	public static Movie toMovie(DetailedMovie dMovie, Movie movie) {
		movie.setTitle(dMovie.getTitle());
		movie.setYear(dMovie.getYear());
		movie.setDirectors(dMovie.getDirector());
		movie.setId(dMovie.getId());

		return movie;
	}
}
