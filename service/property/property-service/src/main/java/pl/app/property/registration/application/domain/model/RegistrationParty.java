package pl.app.property.registration.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationParty {
    private UUID partyId;
    private List<RegistrationGuest> guests;

    public RegistrationParty() {
        this.partyId = UUID.randomUUID();
        this.guests = new ArrayList<>();
    }

    public RegistrationParty(List<RegistrationGuest> guests) {
        this.partyId = UUID.randomUUID();
        this.guests = guests;
    }

    public void addGuest(RegistrationGuest guest) {
        this.guests.add(guest);
    }

    public void removeGuest(RegistrationGuest guest) {
        this.guests.remove(guest);
    }

    public Boolean isGuestInParty(RegistrationGuest guest) {
        return this.guests.stream().anyMatch(g -> g.getGuestId().equals(guest.getGuestId()));
    }

    public Boolean isAllGuestInParty(List<RegistrationGuest> guests) {
        return guests.stream().allMatch(this::isGuestInParty);
    }
}
