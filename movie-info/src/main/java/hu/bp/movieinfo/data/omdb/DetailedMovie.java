package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import hu.bp.movieinfo.data.Movie;
import lombok.Data;

import java.util.Arrays;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class DetailedMovie {
	private String title;
	private Integer year;
	private String director;

	public Movie toMovie() {
		return new Movie(title, year, Arrays.asList(new String[]{director}));
	}
}
