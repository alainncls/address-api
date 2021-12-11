package fr.alainncls.addressapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawAddress {

    private String label;
    private double score;
    @JsonProperty("housenumber")
    private String houseNumber;
    private String name;
    @JsonProperty("postcode")
    private String postCode;
    @JsonProperty("citycode")
    private String cityCode;
    private String city;
    private String street;

}
