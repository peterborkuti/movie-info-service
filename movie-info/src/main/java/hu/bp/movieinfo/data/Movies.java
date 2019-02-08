package hu.bp.movieinfo.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Data
public class Movies {
	private List<Movie> movies;

	public Movies(List<Movie> movies) {
		this.movies = movies;
	}
}
