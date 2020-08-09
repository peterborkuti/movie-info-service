package hu.bp.movieinfo.data;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DirectorSerializerTest {
	@Mock
	private JsonGenerator jsonGenerator;

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void oneDirectorShouldBeSerializedAsString() throws IOException {
		DirectorSerializer ds = new DirectorSerializer();
		String directorName = "director";

		List<String> directors = Arrays.asList(directorName);

		ds.serialize(directors, jsonGenerator, null);

		Mockito.verify(jsonGenerator).writeString(directorName);
	}

	@Test
	public void twoDirectorsShouldBeSerializedAsArray() throws IOException {
		DirectorSerializer ds = new DirectorSerializer();

		List<String> directors = Arrays.asList("director1", "director2");

		ds.serialize(directors, jsonGenerator, null);

		Mockito.verify(jsonGenerator).writeObject(directors);
	}
}
