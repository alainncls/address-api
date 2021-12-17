package fr.alainncls.addressapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.alainncls.addressapi.model.Address;
import fr.alainncls.addressapi.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AddressController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class AddressControllerTest {

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
            .score(0.6447664935064934)
            .houseNumber("55")
            .name("55 Rue du Faubourg Saint-Lazare")
            .postCode("59600")
            .cityCode("59392")
            .city("Maubeuge")
            .street("Rue du Faubourg Saint-Lazare")
            .latitude(3.972332)
            .longitude(50.268053)
            .build();

    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    AddressControllerTest() {
    }

    @Test
    void getAddress() throws Exception {
        List<Address> expectedAddress = List.of(address1, address2);

        when(addressService.searchAddresses("55 Rue du Faubourg Saint-Honoré", 2.31065, 48.872986, 75008)).thenReturn(expectedAddress);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/addresses/search")
                        .param("address", "55 Rue du Faubourg Saint-Honoré")
                        .param("latitude", "2.31065")
                        .param("longitude", "48.872986")
                        .param("postCode", "75008"))
                .andExpect(handler().handlerType(AddressController.class))
                .andExpect(handler().methodName("searchAddresses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAddress)))
                .andDo(document(
                        "searchAddresses",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse(),
                        requestParameters(
                                parameterWithName("address").description("The searched address"),
                                parameterWithName("latitude").optional().description("The latitude if we know it"),
                                parameterWithName("longitude").optional().description("The longitude if we know it"),
                                parameterWithName("postCode").optional().description("The postal code to narrow the search")),
                        responseFields(
                                fieldWithPath("[]").description("The list of results"),
                                fieldWithPath("[].label").description("Full address"),
                                fieldWithPath("[].score").description("Accuracy score (out of 1)"),
                                fieldWithPath("[].houseNumber").description("House number"),
                                fieldWithPath("[].name").description("Number and name of the street"),
                                fieldWithPath("[].postCode").description("Postal code"),
                                fieldWithPath("[].cityCode").description("Technical (INSEE) city code"),
                                fieldWithPath("[].city").description("City name"),
                                fieldWithPath("[].street").description("Street name"),
                                fieldWithPath("[].latitude").description("Latitude"),
                                fieldWithPath("[].longitude").description("Longitude"))));
    }
}