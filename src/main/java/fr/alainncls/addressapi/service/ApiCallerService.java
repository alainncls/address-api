package fr.alainncls.addressapi.service;

import fr.alainncls.addressapi.model.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class ApiCallerService {

    private final WebClient webClient;

    public SearchResponse searchAddresses(String address, Double latitude, Double longitude, Integer postcode) {
        return webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("search/")
                        .queryParam("q", address.replaceAll("\\s+", "+"))
                        .queryParam("limit", 10)
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("postcode", postcode)
                        .build())
                .retrieve()
                .bodyToMono(SearchResponse.class).block();
    }
}
