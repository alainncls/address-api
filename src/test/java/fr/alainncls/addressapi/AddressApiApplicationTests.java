package fr.alainncls.addressapi;

import fr.alainncls.addressapi.controller.AddressController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AddressApiApplicationTests {

    @Autowired
    AddressController addressController;

    @Test
    void contextLoads() {
        assertNotNull(addressController);
    }

}
