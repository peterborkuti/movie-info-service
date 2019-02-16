package hu.bp.movieinfo.client;

import hu.bp.movieinfo.MovieInfoConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OmdbClientTest {
	private static final String API_KEY = "1";
	private static final String BASE_URL = "http://localhost";
	private static final String OMDBAPI_URL = "1";

	@MockBean
	private WebClientHelper helper;

	private IMovieClient client;

	@BeforeEach
	public void setUpClient() {
		MovieInfoConfigurationProperties props = new MovieInfoConfigurationProperties();
		props.setOmdbapi_api_key("1");
		props.setOmdbapi_base_url("http://localhost");

		helper = Mockito.mock(WebClientHelper.class);

		client = new OmdbClient(props, helper);
	}

	@Test
	public void getApiNameShouldReturnWithApiName() {
		assertEquals("omdbapi", client.getApiName());
	}

	@Test
	public void getMovieList() {
	}

	@Test
	public void getMovieFlux() {
	}
}