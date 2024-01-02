package pl.app.property.accommodation.adapter.out.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.core.web.dto.BaseDto;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDto implements Serializable {
    private UUID accommodationId;
    private String name;
    private String description;
    private BaseDto accommodationType;
}
