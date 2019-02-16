package hu.bp.movieinfo.data.omdb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchResultTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchResult sr = new SearchResult();
		assertNotNull(sr.getSearch());
	}
}
