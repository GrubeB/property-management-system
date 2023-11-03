package pl.app.property.amenity.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddStandardAmenitiesToOrganizationCommand {
    private UUID organizationId;
}
