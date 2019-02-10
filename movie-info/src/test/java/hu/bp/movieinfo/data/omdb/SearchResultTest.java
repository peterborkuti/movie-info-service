package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.bp.movieinfo.data.Movie;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SearchResultTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		SearchResult sr = new SearchResult();
		assertNotNull(sr.getSearch());
	}

	@Test
	public void shouldUseSearchWhenJsonify() throws NoSuchFieldException {
		JsonProperty annotation = (JsonProperty)
				SearchResult.class.getDeclaredField("search").
						getAnnotation(JsonProperty.class);

		assertEquals("Search", annotation.value());
	}
}
