package hu.bp.movieinfo.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DirectorSerializerTest {
	@Mock
	private JsonGenerator jsonGenerator;

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
