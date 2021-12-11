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
public class Feature {

    @JsonProperty("properties")
    private RawAddress rawAddress;

    @JsonProperty("geometry")
    private GeoLocation geoLocation;
}
