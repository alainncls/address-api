package fr.alainncls.addressapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String label;
    private double score;
    private String houseNumber;
    private String name;
    private String postCode;
    private String cityCode;
    private String city;
    private String street;
    private double longitude;
    private double latitude;

}
