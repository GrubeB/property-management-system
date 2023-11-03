package pl.app.property.accommodation_availability.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IsAccommodationTypeAvailableCommand implements Serializable {
    private UUID accommodationTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfAccommodations;
}