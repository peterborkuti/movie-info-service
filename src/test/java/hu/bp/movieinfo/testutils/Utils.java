package hu.bp.movieinfo.testutils;

import hu.bp.movieinfo.client.ClientFactory;
import hu.bp.movieinfo.client.IMovieClient;
import hu.bp.movieinfo.data.Movie;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
	public static List<Movie> expectedSingletonMovieList = Arrays.asList(new Movie("title", "2000"));
	public static Map<String, List<Movie>> testMap = new HashMap<>();
	public static String expectedApiName = "apiName";
	public static String expectedSearchValue = "searchValue";

	public static ClientFactory getClientFactoryWithMockClient() {
		return getClientFactoryWithMockClient(expectedApiName, expectedSearchValue, expectedSingletonMovieList);
	}

	public static ClientFactory getClientFactoryWithMockClient(String apiName, String searchValue, List<Movie> expectedReturn) {
		IMovieClient client = createMockCient(apiName, searchValue, expectedReturn);

		ClientFactory clientFactory = new ClientFactory(Arrays.asList(client));
		clientFactory.initClientCache();

		return clientFactory;
	}

	public static IMovieClient createMockCient(String apiName, String searchValue, List<Movie> expectedReturn) {
		IMovieClient client = Mockito.mock(IMovieClient.class);

		Mockito.when(client.getApiName()).thenReturn(apiName);
		Mockito.when(client.getMovieList(searchValue)).thenReturn(expectedReturn);

		return client;
	}
}
