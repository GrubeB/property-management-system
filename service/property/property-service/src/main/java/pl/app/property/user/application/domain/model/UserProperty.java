package pl.app.property.user.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserProperty {
    private UUID propertyId;
}
