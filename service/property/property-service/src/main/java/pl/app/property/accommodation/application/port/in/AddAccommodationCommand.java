package pl.app.property.accommodation.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddAccommodationCommand implements Serializable {
    private String name;
    private String description;
    private UUID accommodationTypeId;
}