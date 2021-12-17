package fr.alainncls.addressapi.service;

import fr.alainncls.addressapi.model.Address;
import fr.alainncls.addressapi.model.RawAddress;
import fr.alainncls.addressapi.model.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AddressService {

    private final WebClient webClient;

    public List<Address> searchAddresses(String address, Double latitude, Double longitude, Integer postcode) {
        Mono<SearchResponse> request = webClient
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
                .bodyToMono(SearchResponse.class);

        SearchResponse searchResponse = request.block();

        if (searchResponse == null) {
            return new ArrayList<>();
        }

        return searchResponse.getFeatures().stream().map(feat -> {
            RawAddress rawAddress = feat.getRawAddress();
            double[] coordinates = feat.getGeoLocation().getCoordinates();

            return Address.builder()
                    .label(rawAddress.getLabel())
                    .score(rawAddress.getScore())
                    .houseNumber(rawAddress.getHouseNumber())
                    .name(rawAddress.getName())
                    .postCode(rawAddress.getPostCode())
                    .cityCode(rawAddress.getCityCode())
                    .city(rawAddress.getCity())
                    .street(rawAddress.getStreet())
                    .latitude(coordinates[0])
                    .longitude(coordinates[1])
                    .build();
        }).collect(Collectors.toList());
    }
}
