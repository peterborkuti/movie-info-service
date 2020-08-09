package hu.bp.movieinfo.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.bp.movieinfo.data.moviedb.SearchedMovie;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@JsonPropertyOrder({ "title", "year", "director" })
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Movie {
	@JsonIgnore
	private String id;
	private String title = "";

	private String year = "";

	@JsonProperty("Director")
	@JsonSerialize(using = DirectorSerializer.class)
	private List<String> directors = new ArrayList<>();

	public Movie() {}

	public Movie(String title) {
		this(title, "", "");
	}

	public Movie(String title, String year) {
		this(title, year, "");
	}

	public Movie(String title, String year, String director) {
		setTitle(title);
		setYear(year);
		setDirectors(director);
	}

	public Movie(String title, String year, List<String> directors) {
		setTitle(title);
		setYear(year);
		setDirectors(directors);
	}

	public void setTitle(String title) {
		this.title = (title == null) ? "" : title;
	}

	public void setYear(String date) {
		year = "";

		if (date != null) {
			String[] parts = date.split("[^0-9]");
			year = (parts.length > 0) ? parts[0] : date;
		}
	}

	public void setDirectors(String directors) {
		if (directors == null) {
			this.directors = new ArrayList<>();
		}
		else {
			this.directors = Arrays.asList(directors.split(","));
		}
	}

	public Movie setDirectors(List<String> directors) {
		if (directors == null) {
			this.directors = new ArrayList<>();
		}
		else {
			this.directors = directors.stream().collect(Collectors.toList());
		}

		return this;
	}

	//TODO: Flux in a Flux seems to me not working 100%
	//Sometimes directors remains empty sometimes not
	public Mono<Movie> setDirectors(Flux<String> directors) {
		this.directors = new ArrayList<>();
		directors.subscribe(this::addDirector);

		return Mono.just(this);
	}

	public void addDirector(String director) {
		if (this.directors == null) {
			this.directors = new ArrayList<>();
		}

		this.directors.add((director == null) ? "" : director);
	}

}

