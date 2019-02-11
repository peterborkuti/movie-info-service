package hu.bp.movieinfo.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WebClientHelper {
	public <T> Mono<T> webGet(WebClient client, String url, String[] urlArgs, Class bodyClass, final Mono<T> monoObject) {
		Mono<T> mono = monoObject;

		try {
			mono = client.get().uri(url, urlArgs).
					exchange().
					flatMap(response -> {
						if (response.statusCode().is4xxClientError()) {
							log.error(response.statusCode().getReasonPhrase());
							return monoObject;
						}
						else {
							return response.bodyToMono(bodyClass);
						}
					});

		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
		}

		return mono;
	}
}
