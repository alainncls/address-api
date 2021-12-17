package fr.alainncls.addressapi.controller;

import fr.alainncls.addressapi.model.Address;
import fr.alainncls.addressapi.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressServiceService;

    @GetMapping("/search")
    public List<Address> searchAddresses(@RequestParam("address") String address,
                                         @RequestParam(value = "latitude", required = false) Double latitude,
                                         @RequestParam(value = "longitude", required = false) Double longitude,
                                         @RequestParam(value = "postCode", required = false) Integer postCode) {
        return addressServiceService.searchAddresses(address,  latitude, longitude, postCode);
    }
}
