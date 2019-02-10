package hu.bp.movieinfo.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class DirectorSerializer extends JsonSerializer<List<String>> {

	@Override
	public void serialize(List<String> strings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (strings != null && strings.size() == 1) {
			jsonGenerator.writeString(strings.get(0));
		} else {
			jsonGenerator.writeObject(strings);
		}
	}
}
