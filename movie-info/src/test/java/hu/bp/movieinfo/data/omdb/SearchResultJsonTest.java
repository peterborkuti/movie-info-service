package hu.bp.movieinfo.data.omdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
public class SearchResultJsonTest {
	@Autowired
	private JacksonTester<SearchResult> json;

	@Test
	public void testItShouldSerializeWithCamelcaseSearch() throws Exception {
		SearchResult searchResult = new SearchResult();
		JsonContent<SearchResult> jsonResult = this.json.write(searchResult);

		assertEquals("{\"Search\":[]}", jsonResult.getJson());
	}
}
