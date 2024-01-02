package pl.app.property.reservation_folio.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolio;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioCharge;
import pl.app.property.reservation_folio.application.domain.model.ReservationFolioPayment;
import pl.app.property.reservation_folio.application.port.in.*;
import pl.app.property.reservation_folio.application.port.out.LoadReservationFolioPort;
import pl.app.property.reservation_folio.application.port.out.ReservationMailPort;
import pl.app.property.reservation_folio.application.port.out.ReservationPaymentPort;
import pl.app.property.reservation_folio.application.port.out.SaveReservationFolioPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ReservationFolioService implements
        RefundReservationFolioUseCase,
        StartReservationPaymentProcessUseCase,
        FetchReservationFolioUseCase,
        IsReservationFolioPaidUseCase,
        AddChargeFolioUseCase,
        RemoveChargeUseCase,
        AddPaymentUseCase,
        CreateReservationFolioUseCase {

    private final LoadReservationFolioPort loadReservationFolioPort;
    private final SaveReservationFolioPort saveReservationFolioPort;
    private final ReservationPaymentPort reservationPaymentPort;
    private final ReservationMailPort reservationMailPort;

    @Override
    public UUID createReservationFolio(CreateReservationFolioCommand command) {
        ReservationFolio newReservationFolio = new ReservationFolio(command.getReservationId(), command.getType());
        return saveReservationFolioPort.saveRegistrationFolio(newReservationFolio);
    }

    @Override
    public UUID addCharge(AddChargeCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        ReservationFolioCharge newCharge = reservationFolio.addCharge(command.getObjectId(), command.getType(), command.getName(),
                command.getAmount(), command.getCurrent(), command.getShouldByPaidBeforeRegistration());
        saveReservationFolioPort.saveRegistrationFolio(reservationFolio);
        return newCharge.getChargeId();
    }

    @Override
    public void removeCharge(RemoveChargeCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        reservationFolio.removeCharge(command.getChargeId());
        saveReservationFolioPort.saveRegistrationFolio(reservationFolio);
    }

    @Override
    public UUID addPayment(AddPaymentCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        ReservationFolioPayment newPayment = reservationFolio.addPayment(command.getGuestId(), command.getAmount(), command.getCurrent());
        saveReservationFolioPort.saveRegistrationFolio(reservationFolio);
        return newPayment.getPaymentId();
    }

    @Override
    public Boolean isReservationFolioPaid(IsReservationFolioPaidCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        return reservationFolio.isFolioPaid();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationFolio fetch(FetchReservationFolioCommand command) {
        return loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
    }

    @Override
    public UUID startPaymentProcess(StartReservationPaymentProcessCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        UUID paymentId = reservationPaymentPort.createPayment(reservationFolio.getPropertyId(), command.getReservationFolioId(),
                "Reservation: " + reservationFolio.getReservationId(), command.getGuestId(), command.getAmount(), command.getCurrency());
        reservationMailPort.sendMail(paymentId, command.getGuestId(), reservationFolio.getPropertyId());
        reservationFolio.addGlobalPaymentId(paymentId);
        saveReservationFolioPort.saveRegistrationFolio(reservationFolio);
        return paymentId;
    }

    @Override
    public void refund(RefundReservationFolioCommand command) {
        ReservationFolio reservationFolio = loadReservationFolioPort.loadRegistrationFolio(command.getReservationFolioId());
        reservationPaymentPort.refundPayment(reservationFolio.getGlobalPaymentIds());
    }
}
