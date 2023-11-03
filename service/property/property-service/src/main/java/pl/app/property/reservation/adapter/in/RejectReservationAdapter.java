package pl.app.property.reservation.adapter.in;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.app.property.property.service.PropertyQueryService;
import pl.app.property.reservation.application.port.in.RejectNoConfirmedReservationsCommand;
import pl.app.property.reservation.application.port.in.RejectNoConfirmedReservationsUseCase;
import pl.app.property.reservation.application.port.in.RejectNoPaidReservationsCommand;
import pl.app.property.reservation.application.port.in.RejectNoPaidReservationsUseCase;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
class RejectReservationAdapter {
    private final Logger logger = LoggerFactory.getLogger(RejectReservationAdapter.class);
    private final RejectNoConfirmedReservationsUseCase rejectNoConfirmedReservationsUseCase;
    private final RejectNoPaidReservationsUseCase rejectNoPaidReservationsUseCase;
    private final PropertyQueryService propertyQueryService;

    @Scheduled(cron = "0 * * * * *")
    public void rejectNoPaidReservations() {
        logger.debug("Rejecting all no paid reservations");
        List<UUID> propertyIds = propertyQueryService.fetchIdAll();
        propertyIds.forEach(this::rejectNoPaidReservationsForProperty);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void rejectNoConfirmedReservations() {
        logger.debug("Rejecting no confirmed reservations");
        List<UUID> propertyIds = propertyQueryService.fetchIdAll();
        propertyIds.forEach(this::rejectNoConfirmedReservations);
    }

    private void rejectNoPaidReservationsForProperty(UUID propertyId) {
        rejectNoPaidReservationsUseCase.rejectNoPaidReservations(new RejectNoPaidReservationsCommand(propertyId));
    }

    private void rejectNoConfirmedReservations(UUID propertyId) {
        rejectNoConfirmedReservationsUseCase.rejectNoConfirmedReservations(new RejectNoConfirmedReservationsCommand(propertyId));

    }
}
