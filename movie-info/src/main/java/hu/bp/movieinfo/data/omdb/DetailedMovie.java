package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import hu.bp.movieinfo.data.Movie;
import lombok.Data;

import java.util.Arrays;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class DetailedMovie {
	private String id = "";
	private String title = "";
	private String year = "";
	private String director = "";
}
