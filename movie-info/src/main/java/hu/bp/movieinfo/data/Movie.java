package hu.bp.movieinfo.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonPropertyOrder({ "title", "year", "director" })
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Movie {
	private String title;

	private Integer year;

	@JsonProperty("Director")
	@JsonSerialize(using = DirectorSerializer.class)
	private List<String> directors;

	public Movie() {}

	public Movie(String title, Integer year, String director) {
		this(
			title,
			year,
			Arrays.asList((director == null) ? new String[]{""} : director.split(","))
		);
	}

	public Movie(String title, Integer year, List<String> directors) {
		this.title = title;
		this.year = year;
		if (directors != null) {
			this.directors = directors.stream().collect(Collectors.toList());
		}
	}
}

class DirectorSerializer extends JsonSerializer<List<String>>{

	@Override
	public void serialize(List<String> strings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (strings != null && strings.size() == 1) {
			jsonGenerator.writeString(strings.get(0));
		} else {
			jsonGenerator.writeObject(strings);
		}
	}
}