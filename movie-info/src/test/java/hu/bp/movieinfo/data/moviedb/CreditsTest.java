package hu.bp.movieinfo.data.moviedb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreditsTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		Credits credits = new Credits();

		assertNotNull(credits.getCrew());
		assertEquals(0, credits.getCrew().size());
	}

}
