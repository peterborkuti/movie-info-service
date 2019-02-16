package hu.bp.movieinfo.client;

import hu.bp.movieinfo.data.Movie;
import hu.bp.movieinfo.testutils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientFactoryTest {
	private final List<Movie> expected = Arrays.asList(new Movie("title", "2000"));
	private IMovieClient client;

	@BeforeEach
	public void setUpClient() {
		client = Utils.createMockCient("apiname", "title", expected);
	}

	@Test
	public void factoryShouldReturnWithClientIfApinameExists() {
		ClientFactory clientFactory = new ClientFactory(Arrays.asList(client));
		clientFactory.initClientCache();

		assertEquals(client, clientFactory.get("apiname"));
	}

	@Test
	public void factoryShouldThrowExceptionWhenApinameNotExists() {
		ClientFactory clientFactory = new ClientFactory(Arrays.asList(client));
		clientFactory.initClientCache();

		RuntimeException runtimeException = Assertions.assertThrows(
				RuntimeException.class, () -> clientFactory.get("notexistingapiname"));

		assertTrue(runtimeException.getMessage().contains("Illegal api name"));
	}
}