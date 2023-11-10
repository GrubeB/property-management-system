package pl.app.property.user.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPropertyToUserCommand implements Serializable {
    private UUID userId;
    private UUID propertyId;
}