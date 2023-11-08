package pl.app.property.guest.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.guest.model.GuestAddressEntity;
import pl.app.property.property.model.PropertyType;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestDto implements Serializable {
    private UUID guestId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cellPhone;
    private Instant dateOfBirth;
    private String gender;
    private GuestAddressDto address;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GuestAddressDto implements Serializable {
        private UUID addressId;
        private String address1;
        private String address2;
        private String city;
        private String country;
        private String region;
        private String zipCode;
    }
}
