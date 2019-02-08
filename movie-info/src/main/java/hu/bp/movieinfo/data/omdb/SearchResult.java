package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
	@JsonProperty("Search")
	private List<SearchedMovie> search;
}
