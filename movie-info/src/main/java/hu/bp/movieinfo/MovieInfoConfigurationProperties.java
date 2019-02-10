package hu.bp.movieinfo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("movieinfo")
public class MovieInfoConfigurationProperties {
	private String omdbapi_api_key;
	private String themoviedb_api_key;

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
