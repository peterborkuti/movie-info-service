package hu.bp.movieinfo.data.moviedb;

import lombok.Data;

@Data
public class SearchedMovie {
	private Integer id = 0;
	private String title = "";
	private String release_date = "";
}
