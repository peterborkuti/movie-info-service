package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
public class FluxSearchResult {
	@JsonProperty("Search")
	private Flux<SearchedMovie> search;
}
