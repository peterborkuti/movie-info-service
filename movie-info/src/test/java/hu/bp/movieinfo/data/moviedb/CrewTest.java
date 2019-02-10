package hu.bp.movieinfo.data.moviedb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CrewTest {
	@Test
	public void shouldInstantiateWithoutNullFields() {
		Crew crew = new Crew();
		assertNotNull(crew.getJob());
		assertNotNull(crew.getName());
	}

}
