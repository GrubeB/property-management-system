ALTER TABLE t_reservation_folio_charge ADD COLUMN object_id UUID;

ALTER TABLE t_reservation_booking_charge_ids DROP CONSTRAINT fk_treservationbookingchargeids_on_reservationbookingentity;
DROP TABLE t_reservation_booking_charge_ids;