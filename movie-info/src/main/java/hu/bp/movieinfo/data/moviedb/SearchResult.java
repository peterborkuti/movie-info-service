package hu.bp.movieinfo.data.moviedb;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
	private Integer total_pages;
	private List<SearchedMovie> results;
}
