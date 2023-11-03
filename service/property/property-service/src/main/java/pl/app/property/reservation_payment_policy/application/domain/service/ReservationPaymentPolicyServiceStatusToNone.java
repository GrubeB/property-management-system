package pl.app.property.reservation_payment_policy.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.app.common.core.service.exception.NotFoundException;
import pl.app.property.reservation_payment_policy.application.domain.exception.ReservationPaymentPolicyException;
import pl.app.property.reservation_payment_policy.application.domain.model.ReservationPaymentPolicy;
import pl.app.property.reservation_payment_policy.application.port.in.*;
import pl.app.property.reservation_payment_policy.application.port.out.LoadPolicyPort;
import pl.app.property.reservation_payment_policy.application.port.out.SavePolicyPort;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class ReservationPaymentPolicyServiceStatusToNone implements
        FetchReservationPaymentPolicyUseCase,
        ChangeReservationPaymentPolicyStatusToNoneUseCase,
        ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationUseCase,
        ChangeReservationPaymentPolicyStatusToFirstDayUseCase,
        ChangeReservationPaymentPolicyStatusToFullUseCase,
        CreateReservationPaymentPolicyUseCase {

    private final LoadPolicyPort loadPolicyPort;
    private final SavePolicyPort savePolicyPort;

    @Override
    public ReservationPaymentPolicy fetch(FetchReservationPaymentPolicyCommand command) {
        return loadPolicyPort.loadPolicyByPropertyId(command.getPropertyId());
    }

    @Override
    public void changeStatusToNone(ChangeReservationPaymentPolicyStatusToNoneCommand command) {
        ReservationPaymentPolicy reservationPaymentPolicy = loadPolicyPort.loadPolicyByPropertyId(command.getPropertyId());
        reservationPaymentPolicy.changeTypeToNone();
        savePolicyPort.savePolicy(reservationPaymentPolicy);
    }

    @Override
    public void changeNumberOfDays(ChangeReservationPaymentPolicyNumberOfDaysBeforeRegistrationCommand command) {
        ReservationPaymentPolicy reservationPaymentPolicy = loadPolicyPort.loadPolicyByPropertyId(command.getPropertyId());
        reservationPaymentPolicy.setNumberOfDaysBeforeRegistration(command.getNumberOfDays());
        savePolicyPort.savePolicy(reservationPaymentPolicy);
    }

    @Override
    public void changeStatusToFirstDay(ChangeReservationPaymentPolicyStatusToFirstDayCommand command) {
        ReservationPaymentPolicy reservationPaymentPolicy = loadPolicyPort.loadPolicyByPropertyId(command.getPropertyId());
        reservationPaymentPolicy.changeTypeToFirstDay();
        savePolicyPort.savePolicy(reservationPaymentPolicy);
    }

    @Override
    public void changeStatusToFull(ChangeReservationPaymentPolicyStatusToFullCommand command) {
        ReservationPaymentPolicy reservationPaymentPolicy = loadPolicyPort.loadPolicyByPropertyId(command.getPropertyId());
        reservationPaymentPolicy.changeTypeToFull();
        savePolicyPort.savePolicy(reservationPaymentPolicy);
    }

    @Override
    public UUID createReservationPaymentPolicy(CreateReservationPaymentPolicyCommand command) {
        verifyPolicyDoseNotExistForProperty(command.getPropertyId());
        ReservationPaymentPolicy newReservationPaymentPolicy = new ReservationPaymentPolicy(command.getPropertyId());
        return savePolicyPort.savePolicy(newReservationPaymentPolicy);
    }

    private void verifyPolicyDoseNotExistForProperty(UUID propertyId) {
        try {
            if (loadPolicyPort.loadPolicyByPropertyId(propertyId) != null) {
                throw new ReservationPaymentPolicyException.ReservationPaymentPolicyAlreadyExistsException();
            }
        } catch (NotFoundException ignored) {
        }
    }
}
