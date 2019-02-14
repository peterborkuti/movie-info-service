package hu.bp.movieinfo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("movieinfo")
public class MovieInfoConfigurationProperties {
	private String omdbapi_api_key;
	private String themoviedb_api_key;
	private String omdbapi_base_url;
	private String themoviedb_base_url;
	/**
	 * When searching themoviedb can have a lot of results.
	 * You can limit the number of result pages
	 */
	private Integer themoviedb_max_pages;

	public Integer getThemoviedb_max_pages() {
		return themoviedb_max_pages;
	}

	public void setThemoviedb_max_pages(Integer themoviedb_max_pages) {
		this.themoviedb_max_pages = themoviedb_max_pages;
	}

	public String getOmdbapi_base_url() {
		return omdbapi_base_url;
	}

	public void setOmdbapi_base_url(String omdbapi_base_url) {
		this.omdbapi_base_url = omdbapi_base_url;
	}

	public String getThemoviedb_base_url() {
		return themoviedb_base_url;
	}

	public void setThemoviedb_base_url(String themoviedb_base_url) {
		this.themoviedb_base_url = themoviedb_base_url;
	}

	public String getOmdbapi_api_key() {
		return omdbapi_api_key;
	}

	public void setOmdbapi_api_key(String omdbapi_api_key) {
		this.omdbapi_api_key = omdbapi_api_key;
	}

	public String getThemoviedb_api_key() {
		return themoviedb_api_key;
	}

	public void setThemoviedb_api_key(String themoviedb_api_key) {
		this.themoviedb_api_key = themoviedb_api_key;
	}
}
