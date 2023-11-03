package pl.app.property.amenity.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAmenityCommand implements Serializable {
    private UUID organizationId;
    private String name;
    private String category;
    private String description;
}
