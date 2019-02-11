package hu.bp.movieinfo.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class WebClientHelper {
	public <T> Mono<T> webGet(WebClient client, String url, String[] urlArgs, Class bodyClass, final Mono<T> monoObject) {
		Mono<T> mono = monoObject;

		try {
			mono = client.get().uri(url, urlArgs).
					retrieve().
					bodyToMono(bodyClass).
					retryBackoff(10, Duration.ofSeconds(2));

		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}

		return mono;
	}
}
