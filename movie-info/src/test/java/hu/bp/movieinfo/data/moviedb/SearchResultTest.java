package hu.bp.movieinfo.data.moviedb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SearchResultTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchResult sr = new SearchResult();
		assertNotNull(sr.getPage());
		assertNotNull(sr.getResults());
		assertNotNull(sr.getTotal_pages());
		assertEquals(0, sr.getResults().size());
	}

}
