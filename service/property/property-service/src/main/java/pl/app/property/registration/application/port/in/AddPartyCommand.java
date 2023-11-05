package pl.app.property.registration.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddPartyCommand implements Serializable {
    private UUID registrationId;
    private List<Party> parties;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Party {
        private List<UUID> guestIds;
    }
}