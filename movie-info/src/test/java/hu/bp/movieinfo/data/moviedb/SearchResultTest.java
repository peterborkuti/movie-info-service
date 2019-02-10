package hu.bp.movieinfo.data.moviedb;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.bp.movieinfo.data.moviedb.SearchResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
