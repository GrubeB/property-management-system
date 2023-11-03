package pl.app.property.amenity.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAmenityCommand implements Serializable {
    private UUID amenityId;
}
