package hu.bp.movieinfo.client;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://stackoverflow.com/questions/6390810/implement-a-simple-factory-pattern-with-spring-3-annotations
@Service
public class ClientFactory {
	private List<IMovieClient> clients;

	private static final Map<String, IMovieClient> clientCache = new HashMap<>();

	public ClientFactory(List<IMovieClient> clients) {
		this.clients = clients;
	}

	@PostConstruct
	public void initClientCache() {
		clients.stream().forEach(client -> clientCache.put(client.getApiName(), client));
	}

	public IMovieClient get(String apiName) {
		IMovieClient client = clientCache.get(apiName);

		if (client == null) {
			throw new RuntimeException("Illegal api name");
		}

		return client;
	}
}
