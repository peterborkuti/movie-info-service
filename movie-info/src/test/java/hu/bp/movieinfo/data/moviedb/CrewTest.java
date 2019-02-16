package hu.bp.movieinfo.data.moviedb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CrewTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		Crew crew = new Crew();
		assertNotNull(crew.getJob());
		assertNotNull(crew.getName());
	}

}
