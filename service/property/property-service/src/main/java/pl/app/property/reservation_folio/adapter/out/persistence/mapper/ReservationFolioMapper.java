package pl.app.property.reservation_folio.adapter.out.persistence.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.property.reservation.adapter.out.query.ReservationQueryService;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioChargeEntity;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioEntity;
import pl.app.property.reservation_folio.adapter.out.persistence.model.ReservationFolioPaymentEntity;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioCharge;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioPayment;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationFolioMapper {
    private final ReservationQueryService reservationQueryService;

    public ReservationFolioEntity mapToReservationFolioEntity(ReservationFolio domain) {
        ReservationFolioEntity entity = ReservationFolioEntity.builder()
                .reservationFolioId(domain.getReservationFolioId())
                .reservation(reservationQueryService.fetchById(domain.getReservationId()))
                .type(domain.getType())
                .payments(domain.getPayments().stream().map(this::mapToReservationFolioPaymentEntity).collect(Collectors.toSet()))
                .charges(domain.getCharges().stream().map(this::mapToReservationFolioChargeEntity).collect(Collectors.toSet()))
                .build();
        entity.getPayments().forEach(p -> p.setReservationFolio(entity));
        entity.getCharges().forEach(ch -> ch.setReservationFolio(entity));
        return entity;
    }

    private ReservationFolioPaymentEntity mapToReservationFolioPaymentEntity(ReservationFolioPayment domain) {
        return ReservationFolioPaymentEntity.builder()
                .paymentId(domain.getPaymentId())
                .guestId(domain.getGuestId())
                .amount(domain.getAmount())
                .current(domain.getCurrent())
                .date(domain.getDate())
                .build();
    }

    private ReservationFolioChargeEntity mapToReservationFolioChargeEntity(ReservationFolioCharge domain) {
        return ReservationFolioChargeEntity.builder()
                .chargeId(domain.getChargeId())
                .type(domain.getType())
                .name(domain.getName())
                .amount(domain.getAmount())
                .current(domain.getCurrent())
                .date(domain.getDate())
                .shouldByPaidBeforeRegistration(domain.getShouldByPaidBeforeRegistration())
                .build();
    }

    public ReservationFolio mapToReservationFolio(ReservationFolioEntity entity) {
        return new ReservationFolio(
                entity.getReservationFolioId(),
                entity.getReservation().getReservationId(),
                entity.getReservation().getProperty().getPropertyId(),
                entity.getType(),
                entity.getPayments().stream().map(this::mapToReservationFolioPayment).collect(Collectors.toList()),
                entity.getCharges().stream().map(this::mapToReservationFolioCharge).collect(Collectors.toList())
        );
    }

    private ReservationFolioPayment mapToReservationFolioPayment(ReservationFolioPaymentEntity entity) {
        return new ReservationFolioPayment(
                entity.getPaymentId(),
                entity.getGuestId(),
                entity.getAmount(),
                entity.getCurrent(),
                entity.getDate()
        );
    }

    private ReservationFolioCharge mapToReservationFolioCharge(ReservationFolioChargeEntity entity) {
        return new ReservationFolioCharge(
                entity.getChargeId(),
                entity.getType(),
                entity.getName(),
                entity.getAmount(),
                entity.getCurrent(),
                entity.getDate(),
                entity.getShouldByPaidBeforeRegistration()
        );
    }
}
