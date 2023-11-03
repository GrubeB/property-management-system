package pl.app.property.amenity.application.port.in;


import java.util.UUID;

public interface CreateAmenityUseCase {
    UUID create(CreateAmenityCommand command);
}
