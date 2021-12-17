package fr.alainncls.addressapi.service;

import fr.alainncls.addressapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AddressServiceTest {

    private final Address address1 = Address.builder()
            .label("55 Rue du Faubourg Saint-Honoré, 75008 Paris")
            .score(0.8035054545454545)
            .houseNumber("55")
            .name("55 Rue du Faubourg Saint-Honoré")
            .postCode("75008")
            .cityCode("75108")
            .city("Paris")
            .street("Rue du Faubourg Saint-Honoré")
            .latitude(2.31065)
            .longitude(48.872986)
            .build();
    private final Address address2 = Address.builder()
            .label("55 Rue du Faubourg Saint-Lazare 59600 Maubeuge")
            .score(0.6447664935064934).houseNumber("55")
            .name("55 Rue du Faubourg Saint-Lazare")
            .postCode("59600")
            .cityCode("59392")
            .city("Maubeuge")
            .street("Rue du Faubourg Saint-Lazare")
            .latitude(3.972332)
            .longitude(50.268053)
            .build();

    @Mock
    ApiCallerService apiCallerService;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void setUpMocks() {
        openMocks(this);
    }

    @Test
    void searchAddresses() {
        List<Address> expectedAddress = List.of(address1, address2);
        SearchResponse searchResponse = SearchResponse.builder()
                .features(List.of(
                        Feature.builder()
                                .rawAddress(RawAddress.builder()
                                        .label("55 Rue du Faubourg Saint-Honoré, 75008 Paris")
                                        .score(0.8035054545454545)
                                        .houseNumber("55")
                                        .name("55 Rue du Faubourg Saint-Honoré")
                                        .postCode("75008")
                                        .cityCode("75108")
                                        .city("Paris")
                                        .street("Rue du Faubourg Saint-Honoré")
                                        .build())
                                .geoLocation(GeoLocation.builder().coordinates(new double[]{2.31065, 48.872986}).build())
                                .build(),
                        Feature.builder()
                                .rawAddress(RawAddress.builder()
                                        .label("55 Rue du Faubourg Saint-Lazare 59600 Maubeuge")
                                        .score(0.6447664935064934)
                                        .houseNumber("55")
                                        .name("55 Rue du Faubourg Saint-Lazare")
                                        .postCode("59600")
                                        .cityCode("59392")
                                        .city("Maubeuge")
                                        .street("Rue du Faubourg Saint-Lazare")
                                        .build())
                                .geoLocation(GeoLocation.builder().coordinates(new double[]{3.972332, 50.268053}).build())
                                .build()))
                .build();

        when(apiCallerService.searchAddresses("55 Rue du Faubourg Saint-Honore", null, null, null)).thenReturn(searchResponse);

        List<Address> result = addressService.searchAddresses("55 Rue du Faubourg Saint-Honore", null, null, null);

        assertNotNull(result);
        assertEquals(expectedAddress.size(), result.size());
        assertEquals(expectedAddress, result);
    }
}