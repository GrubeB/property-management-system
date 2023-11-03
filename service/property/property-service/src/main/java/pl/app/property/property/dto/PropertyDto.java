package pl.app.property.property.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.property.property.model.PropertyType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private UUID propertyId;
    private PropertyType propertyType;
    private LocalTime checkInFromTime;
    private LocalTime checkInToTime;
    private LocalTime checkOutFromTime;
    private LocalTime checkOutToTime;
}
