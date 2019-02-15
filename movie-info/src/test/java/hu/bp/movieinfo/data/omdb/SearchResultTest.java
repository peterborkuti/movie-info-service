package hu.bp.movieinfo.data.omdb;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchResultTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchResult sr = new SearchResult();
		assertNotNull(sr.getSearch());
	}
}
