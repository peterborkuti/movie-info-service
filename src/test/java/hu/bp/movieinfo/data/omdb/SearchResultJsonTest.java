package hu.bp.movieinfo.data.omdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class SearchResultJsonTest {
	private JacksonTester<SearchResult> json;

	@BeforeEach
	public void setUp() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void testItShouldSerializeWithCamelcaseSearch() throws Exception {
		SearchResult searchResult = new SearchResult();
		JsonContent<SearchResult> jsonResult = this.json.write(searchResult);

		assertEquals("{\"Search\":[]}", jsonResult.getJson());
	}
}
