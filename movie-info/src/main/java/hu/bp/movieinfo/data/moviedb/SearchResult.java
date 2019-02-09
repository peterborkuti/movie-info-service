package hu.bp.movieinfo.data.moviedb;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {
	private Integer page = 0;
	private Integer total_pages = 0;
	private List<SearchedMovie> results = new ArrayList<>();
}
