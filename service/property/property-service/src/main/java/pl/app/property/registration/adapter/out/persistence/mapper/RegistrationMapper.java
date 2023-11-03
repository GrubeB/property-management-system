package pl.app.property.registration.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.guest.model.GuestEntity;
import pl.app.property.guest.service.GuestQueryService;
import pl.app.property.property.model.PropertyEntity;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationBookingEntity;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationEntity;
import pl.app.property.registration.adapter.out.persistence.model.RegistrationPartyEntity;
import pl.app.property.registration.application.domain.model.Registration;
import pl.app.property.registration.application.domain.model.RegistrationBooking;
import pl.app.property.registration.application.domain.model.RegistrationGuest;
import pl.app.property.registration.application.domain.model.RegistrationParty;
import pl.app.property.registration_folio.adapter.out.persistence.model.RegistrationFolioEntity;
import pl.app.property.registration_folio.adapter.out.persistence.repository.RegistrationFolioRepository;
import pl.app.property.registration_folio.application.domain.exception.RegistrationFolioException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RegistrationMapper {

    private final GuestQueryService guestQueryService;
    private final PropertyQueryService propertyQueryService;
    private final RegistrationFolioRepository registrationFolioRepository;

    public RegistrationEntity mapToRegistrationEntity(Registration domain) {
        List<GuestEntity> guestEntities = Stream.concat(domain.getParties().stream().map(RegistrationParty::getGuests),
                        domain.getRegistrationBookings().stream().map(RegistrationBooking::getGuests))
                .flatMap(List::stream)
                .map(this::mapToGuestEntity).toList();
        RegistrationEntity entity = RegistrationEntity.builder()
                .registrationId(domain.getRegistrationId())
                .property(this.mapToPropertyEntity(domain.getPropertyId()))
                .status(domain.getStatus())
                .source(domain.getSource())
                .registrationFolio(this.mapToRegistrationFolioEntity(domain.getRegistrationFolioId()))
                .parties(domain.getParties().stream().map(p -> this.mapToRegistrationPartyEntity(p, guestEntities)).collect(Collectors.toSet()))
                .bookings(domain.getRegistrationBookings().stream().map(b -> this.mapToRegistrationBookingEntity(b, guestEntities)).collect(Collectors.toSet()))
                .build();
        entity.getParties().forEach(p -> p.setRegistration(entity));
        entity.getBookings().forEach(b -> b.setRegistration(entity));
        return entity;
    }

    private RegistrationFolioEntity mapToRegistrationFolioEntity(UUID registrationFolioId) {
        return registrationFolioId == null ? null : registrationFolioRepository.findById(registrationFolioId)
                .orElseThrow(() -> RegistrationFolioException.NotFoundRegistrationFolioException.fromId(registrationFolioId));
    }

    private PropertyEntity mapToPropertyEntity(UUID propertyId) {
        return propertyQueryService.fetchById(propertyId);
    }

    private RegistrationBookingEntity mapToRegistrationBookingEntity(RegistrationBooking domain, List<GuestEntity> guestEntities) {
        return RegistrationBookingEntity.builder()
                .bookingId(domain.getBookingId())
                .startDate(domain.getStart())
                .endDate(domain.getEnd())
                .accommodationTypeId(domain.getAccommodationTypeId())
                .accommodationTypeReservationId(domain.getAccommodationTypeReservationId())
                .guests(domain.getGuests().stream().map(g -> this.getGuestEntityById(g.getGuestId(), guestEntities)).collect(Collectors.toSet()))
                .chargeIds(new HashSet<>(domain.getChargeIds()))
                .build();
    }

    private RegistrationPartyEntity mapToRegistrationPartyEntity(RegistrationParty domain, List<GuestEntity> guestEntities) {
        return RegistrationPartyEntity.builder()
                .partyId(domain.getPartyId())
                .guests(domain.getGuests().stream().map(g -> this.getGuestEntityById(g.getGuestId(), guestEntities)).collect(Collectors.toSet()))
                .build();
    }

    private GuestEntity mapToGuestEntity(RegistrationGuest guest) {
        return guestQueryService.fetchById(guest.getGuestId());
    }

    public Registration mapToRegistration(RegistrationEntity entity) {
        List<RegistrationGuest> guests = Stream.concat(entity.getParties().stream().map(RegistrationPartyEntity::getGuests),
                        entity.getBookings().stream().map(RegistrationBookingEntity::getGuests))
                .flatMap(Set::stream)
                .map(this::mapToRegistrationGuest).toList();
        return new Registration(
                entity.getRegistrationId(),
                entity.getProperty().getPropertyId(),
                entity.getStatus(),
                entity.getSource(),
                entity.getParties().stream().map(p -> this.mapToRegistrationParty(p, guests)).collect(Collectors.toList()),
                entity.getRegistrationFolio().getRegistrationFolioId(),
                entity.getBookings().stream().map(b -> this.mapToBooking(b, guests)).collect(Collectors.toList())
        );
    }

    private RegistrationBooking mapToBooking(RegistrationBookingEntity entity, List<RegistrationGuest> guests) {
        return new RegistrationBooking(
                entity.getBookingId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getAccommodationTypeId(),
                entity.getGuests().stream().map(g -> this.getRegistrationGuestById(g.getGuestId(), guests)).collect(Collectors.toList()),
                entity.getAccommodationTypeReservationId(),
                new ArrayList<>(entity.getChargeIds())
        );
    }

    private RegistrationParty mapToRegistrationParty(RegistrationPartyEntity entity, List<RegistrationGuest> guests) {
        return new RegistrationParty(
                entity.getPartyId(),
                entity.getGuests().stream().map(g -> this.getRegistrationGuestById(g.getGuestId(), guests)).collect(Collectors.toList())
        );
    }

    private RegistrationGuest mapToRegistrationGuest(GuestEntity entity) {
        return new RegistrationGuest(entity.getGuestId());
    }

    public RegistrationGuest getRegistrationGuestById(UUID guestId, List<RegistrationGuest> guests) {
        return guests.stream().filter(g -> g.getGuestId().equals(guestId)).findAny().get();
    }

    public GuestEntity getGuestEntityById(UUID guestId, List<GuestEntity> guests) {
        return guests.stream().filter(g -> g.getGuestId().equals(guestId)).findAny().get();
    }
}
