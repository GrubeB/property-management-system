CREATE TABLE t_payment_stripe (
  stripe_payment_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   payment_intent VARCHAR(255),
   session_checkout_id VARCHAR(255),
   payment_id UUID NOT NULL,
   CONSTRAINT pk_t_payment_stripe PRIMARY KEY (stripe_payment_id)
);

ALTER TABLE t_payment_stripe ADD CONSTRAINT FK_T_PAYMENT_STRIPE_ON_PAYMENT
    FOREIGN KEY (payment_id) REFERENCES t_payment (payment_id);


CREATE TABLE t_reservation_folio_global_payment (
   reservation_folio_id UUID NOT NULL,
   global_payment_id UUID NOT NULL
);

ALTER TABLE t_reservation_folio_global_payment ADD CONSTRAINT fk_treservationfolioglobalpayment_on_reservationfolioentity
    FOREIGN KEY (reservation_folio_id) REFERENCES t_reservation_folio (reservation_folio_id);


CREATE TABLE t_registration_folio_global_payment (
   party_folio_id UUID NOT NULL,
   global_payment_id UUID NOT NULL
);

ALTER TABLE t_registration_folio_global_payment ADD CONSTRAINT fk_t_registration_folio_global_payment_on_party_folio_entity
    FOREIGN KEY (party_folio_id) REFERENCES t_registration_party_folio (party_folio_id);





