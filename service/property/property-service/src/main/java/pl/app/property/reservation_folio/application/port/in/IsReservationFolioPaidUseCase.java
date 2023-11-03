package pl.app.property.reservation_folio.application.port.in;

public interface IsReservationFolioPaidUseCase {
    Boolean isReservationFolioPaid(IsReservationFolioPaidCommand command);
}
