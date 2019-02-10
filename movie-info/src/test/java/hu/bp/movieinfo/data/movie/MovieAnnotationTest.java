package hu.bp.movieinfo.data.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.bp.movieinfo.data.DirectorSerializer;
import hu.bp.movieinfo.data.Movie;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieAnnotationTest {
	@Test
	public void movieFieldsShouldBeSerializedInAStrictOrder() {
		JsonPropertyOrder annotation = (JsonPropertyOrder)
				Movie.class.getAnnotation(JsonPropertyOrder.class);

		assertArrayEquals(new String[]{"title", "year", "director"}, annotation.value());
	}

	@Test
	public void directorsFieldShouldBeSerializedAsDirector() throws NoSuchFieldException {
		JsonProperty annotation = (JsonProperty)
				Movie.class.getDeclaredField("directors").
						getAnnotation(JsonProperty.class);

		assertEquals("Director", annotation.value());
	}

	@Test
	public void directorsFieldShouldBeSerializedWithDirectorSerializer() throws NoSuchFieldException {
		JsonSerialize annotation = (JsonSerialize)
				Movie.class.getDeclaredField("directors").
						getAnnotation(JsonSerialize.class);

		assertEquals(DirectorSerializer.class, annotation.using());
	}

	@Test
	public void movieFieldsShouldBeSerializedInCamelCase() {
		JsonNaming annotation = (JsonNaming)
				Movie.class.getAnnotation(JsonNaming.class);

		assertEquals(PropertyNamingStrategy.UpperCamelCaseStrategy.class, annotation.value());
	}

	@Test
	public void idShouldNotBeSerialized() throws NoSuchFieldException {
		JsonIgnore annotation = (JsonIgnore)
				Movie.class.getDeclaredField("id").
				getAnnotation(JsonIgnore.class);

		assertNotNull(annotation);
	}
}
