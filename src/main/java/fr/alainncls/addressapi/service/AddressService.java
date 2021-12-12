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

    public List<Address> searchAddresses(String address, Integer limit, Boolean autocomplete, Double latitude, Double longitude, String type, Integer zipcode, Integer cityCode) {
        Mono<SearchResponse> request = webClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("search/")
                        .queryParam("q", address.replaceAll("\\s+", "+"))
                        .queryParam("limit", limit)
                        .queryParam("autocomplete", autocomplete)
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("type", type)
                        .queryParam("zipcode", zipcode)
                        .queryParam("cityCode", cityCode)
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
                    .longitude(coordinates[0])
                    .latitude(coordinates[1])
                    .build();
        }).collect(Collectors.toList());
    }
}
