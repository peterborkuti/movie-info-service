package hu.bp.movieinfo.data.moviedb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditsTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		Credits credits = new Credits();

		assertNotNull(credits.getCrew());
		assertEquals(0, credits.getCrew().size());
	}

}
