-- ORGANIZATION
CREATE TABLE t_organization (
  organization_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255) NOT NULL,
   logo_id UUID,
   CONSTRAINT pk_t_organization PRIMARY KEY (organization_id)
);
CREATE TABLE t_organization_image (
  image_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   file_id VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   organization_id UUID,
   CONSTRAINT pk_t_organization_image PRIMARY KEY (image_id)
);

ALTER TABLE t_organization_image ADD CONSTRAINT FK_T_ORGANIZATION_IMAGE_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES t_organization (organization_id);
ALTER TABLE t_organization ADD CONSTRAINT FK_T_ORGANIZATION_ON_LOGO FOREIGN KEY (logo_id) REFERENCES t_organization_image (image_id);

-- PROPERTY
CREATE TABLE t_property (
  property_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   type VARCHAR(255),
   check_in_from_time TIME WITHOUT TIME ZONE,
   check_in_to_time TIME WITHOUT TIME ZONE,
   check_out_from_time TIME WITHOUT TIME ZONE,
   check_out_to_time TIME WITHOUT TIME ZONE,
   property_details_id UUID,
   organization_id UUID NOT NULL,
   CONSTRAINT pk_t_property PRIMARY KEY (property_id)
);
CREATE TABLE t_property_image (
  image_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   file_id VARCHAR(255) NOT NULL,
   description VARCHAR(255),
   property_id UUID,
   CONSTRAINT pk_t_property_image PRIMARY KEY (image_id)
);
CREATE TABLE t_property_details (
  property_details_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255),
   email VARCHAR(255),
   phone VARCHAR(255),
   website VARCHAR(255),
   description VARCHAR(255),
   address_id UUID NOT NULL,
   CONSTRAINT pk_t_property_details PRIMARY KEY (property_details_id)
);
CREATE TABLE t_property_address (
  address_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   address1 VARCHAR(255),
   address2 VARCHAR(255),
   city VARCHAR(255),
   country VARCHAR(255),
   region VARCHAR(255),
   zip_code VARCHAR(255),
   CONSTRAINT pk_t_property_address PRIMARY KEY (address_id)
);
ALTER TABLE t_property_details ADD CONSTRAINT FK_T_PROPERTY_DETAILS_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES t_property_address (address_id);
ALTER TABLE t_property_image ADD CONSTRAINT FK_T_PROPERTY_IMAGE_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_property ADD CONSTRAINT FK_T_PROPERTY_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES t_organization (organization_id);
ALTER TABLE t_property ADD CONSTRAINT FK_T_PROPERTY_ON_PROPERTY_DETAILS FOREIGN KEY (property_details_id) REFERENCES t_property_details (property_details_id);

-- GUEST
CREATE TABLE t_guest (
  guest_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255),
   phone VARCHAR(255),
   cell_phone VARCHAR(255),
   date_of_birth TIMESTAMP WITHOUT TIME ZONE,
   gender VARCHAR(255),
   address_id UUID,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_guest PRIMARY KEY (guest_id)
);
CREATE TABLE t_guest_address (
  address_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   address1 VARCHAR(255),
   address2 VARCHAR(255),
   city VARCHAR(255),
   country VARCHAR(255),
   region VARCHAR(255),
   zip_code VARCHAR(255),
   CONSTRAINT pk_t_guest_address PRIMARY KEY (address_id)
);
CREATE TABLE t_guest_credit_card (
  credit_card_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   active BOOLEAN,
   number VARCHAR(255),
   name_of_card_holder VARCHAR(255),
   exp TIMESTAMP WITHOUT TIME ZONE,
   guest_id UUID,
   CONSTRAINT pk_t_guest_credit_card PRIMARY KEY (credit_card_id)
);

ALTER TABLE t_guest_credit_card ADD CONSTRAINT FK_T_GUEST_CREDIT_CARD_ON_GUEST FOREIGN KEY (guest_id) REFERENCES t_guest (guest_id);
ALTER TABLE t_guest ADD CONSTRAINT FK_T_GUEST_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES t_guest_address (address_id);
ALTER TABLE t_guest ADD CONSTRAINT FK_T_GUEST_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);

-- USER
CREATE TABLE t_user (
  user_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_user PRIMARY KEY (user_id)
);

CREATE TABLE t_user_organization (
  organization_id UUID NOT NULL,
   user_id UUID NOT NULL,
   CONSTRAINT pk_t_user_organization PRIMARY KEY (organization_id, user_id)
);

CREATE TABLE t_user_property (
  property_id UUID NOT NULL,
   user_id UUID NOT NULL,
   CONSTRAINT pk_t_user_property PRIMARY KEY (property_id, user_id)
);
CREATE TABLE t_privilege (
  privilege_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   domain_object_id UUID,
   permission_id UUID NOT NULL,
   user_id UUID,
   CONSTRAINT pk_t_privilege PRIMARY KEY (privilege_id)
);
CREATE TABLE t_permission (
  permission_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   permission_level VARCHAR(255) NOT NULL,
   permission_name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_permission PRIMARY KEY (permission_id)
);

ALTER TABLE t_permission ADD CONSTRAINT uc_t_permission_permission_name_and_permission_level UNIQUE (permission_name, permission_level);
ALTER TABLE t_privilege ADD CONSTRAINT FK_T_PRIVILEGE_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES t_permission (permission_id);
ALTER TABLE t_privilege ADD CONSTRAINT FK_T_PRIVILEGE_ON_USER FOREIGN KEY (user_id) REFERENCES t_user (user_id);
ALTER TABLE t_user ADD CONSTRAINT uc_t_user_email UNIQUE (email);
ALTER TABLE t_user_organization ADD CONSTRAINT fk_tuseorg_on_organization_entity FOREIGN KEY (organization_id) REFERENCES t_organization (organization_id);
ALTER TABLE t_user_organization ADD CONSTRAINT fk_tuseorg_on_user_entity FOREIGN KEY (user_id) REFERENCES t_user (user_id);
ALTER TABLE t_user_property ADD CONSTRAINT fk_tusepro_on_property_entity FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_user_property ADD CONSTRAINT fk_tusepro_on_user_entity FOREIGN KEY (user_id) REFERENCES t_user (user_id);

-- ACCOMMODATION TYPE

CREATE TABLE t_accommodation_type (
  type_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255),
   abbreviation VARCHAR(255),
   description VARCHAR(255),
   gender_room_type VARCHAR(255),
   room_type VARCHAR(255),
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type PRIMARY KEY (type_id)
);
CREATE TABLE t_accommodation_image (
  image_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   file_id VARCHAR(255) NOT NULL,
   description VARCHAR(255),
   accommodation_type_id UUID,
   CONSTRAINT pk_t_accommodation_image PRIMARY KEY (image_id)
);
CREATE TABLE t_accommodation_type_bed (
  bed_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   number_of_beds INTEGER NOT NULL,
   type VARCHAR(255) NOT NULL,
   accommodation_type_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_bed PRIMARY KEY (bed_id)
);

ALTER TABLE t_accommodation_type_bed ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_BED_ON_ACCOMMODATION_TYPE FOREIGN KEY (accommodation_type_id) REFERENCES t_accommodation_type (type_id);
ALTER TABLE t_accommodation_image ADD CONSTRAINT FK_T_ACCOMMODATION_IMAGE_ON_ACCOMMODATION_TYPE FOREIGN KEY (accommodation_type_id) REFERENCES t_accommodation_type (type_id);
ALTER TABLE t_accommodation_type ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);

-- ACCOMMODATION
CREATE TABLE t_accommodation (
  accommodation_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   accommodation_name VARCHAR(255) NOT NULL,
   description VARCHAR(255),
   accommodation_type UUID NOT NULL,
   CONSTRAINT pk_t_accommodation PRIMARY KEY (accommodation_id)
);

ALTER TABLE t_accommodation ADD CONSTRAINT FK_T_ACCOMMODATION_ON_ACCOMMODATION_TYPE FOREIGN KEY (accommodation_type) REFERENCES t_accommodation_type (type_id);

-- ACCOMMODATION AVAILABILITY
CREATE TABLE t_accommodation_type_availability (
  accommodation_type_availability_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   accommodation_type UUID,
   CONSTRAINT pk_t_accommodation_type_availability PRIMARY KEY (accommodation_type_availability_id)
);
CREATE TABLE t_accommodation_type_reservation (
  accommodation_type_reservation_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   accommodation_type_id UUID NOT NULL,
   start_date date,
   end_date date,
   status VARCHAR(255),
   accommodation_type_availability_id UUID,
   CONSTRAINT pk_t_accommodation_type_reservation PRIMARY KEY (accommodation_type_reservation_id)
);
CREATE TABLE t_accommodation_reservation (
  accommodation_reservation_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   accommodation_id UUID,
   status VARCHAR(255),
   start_date date,
   end_date date,
   accommodation_type_reservation_id UUID,
   accommodation_type_availability_id UUID,
   CONSTRAINT pk_t_accommodation_reservation PRIMARY KEY (accommodation_reservation_id)
);

ALTER TABLE t_accommodation_reservation ADD CONSTRAINT FK_TACCOMMODATIONRESERVATION_ON_ACCOMMODATIONTYPEAVAILABILITY FOREIGN KEY (accommodation_type_availability_id) REFERENCES t_accommodation_type_availability (accommodation_type_availability_id);
ALTER TABLE t_accommodation_reservation ADD CONSTRAINT FK_TACCOMMODATIONRESERVATION_ON_ACCOMMODATIONTYPERESERVATION FOREIGN KEY (accommodation_type_reservation_id) REFERENCES t_accommodation_type_reservation (accommodation_type_reservation_id);
ALTER TABLE t_accommodation_reservation ADD CONSTRAINT FK_T_ACCOMMODATION_RESERVATION_ON_ACCOMMODATION FOREIGN KEY (accommodation_id) REFERENCES t_accommodation (accommodation_id);
ALTER TABLE t_accommodation_type_reservation ADD CONSTRAINT FK_TACCOMMODATIONTYPERESERVATIO_ON_ACCOMMODATIONTYPEAVAILABILIT FOREIGN KEY (accommodation_type_availability_id) REFERENCES t_accommodation_type_availability (accommodation_type_availability_id);
ALTER TABLE t_accommodation_type_availability ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_AVAILABILITY_ON_ACCOMMODATION_TYPE FOREIGN KEY (accommodation_type) REFERENCES t_accommodation_type (type_id);

-- ACCOMMODATION PRICE
CREATE TABLE t_accommodation_type_price (
  accommodation_type_price_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   accommodation_type UUID NOT NULL,
   default_price_per_day DECIMAL NOT NULL,
   CONSTRAINT pk_t_accommodation_type_price PRIMARY KEY (accommodation_type_price_id)
);
CREATE TABLE t_accommodation_type_price_specific_day (
  price_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   date date,
   price DECIMAL,
   accommodation_type_price UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_price_specific_day PRIMARY KEY (price_id)
);
CREATE TABLE t_accommodation_type_price_policy (
  accommodation_type_price_policy_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   property_id UUID NOT NULL,
   policy_type VARCHAR(255) NOT NULL,
   is_active BOOLEAN,
   CONSTRAINT pk_t_accommodation_type_price_policy PRIMARY KEY (accommodation_type_price_policy_id)
);
ALTER TABLE t_accommodation_type_price_specific_day ADD CONSTRAINT FK_TACCOMMODATIONTYPEPRICESPECIFICDAY_ON_ACCOMMODATIONTYPEPRICE FOREIGN KEY (accommodation_type_price) REFERENCES t_accommodation_type_price (accommodation_type_price_id);
ALTER TABLE t_accommodation_type_price ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_PRICE_ON_ACCOMMODATION_TYPE FOREIGN KEY (accommodation_type) REFERENCES t_accommodation_type (type_id);

-- ACCOMMODATION PRICE POLICY
CREATE TABLE t_accommodation_type_price_day_of_the_week_policy (
  policy_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   property_Id UUID,
   CONSTRAINT pk_t_accommodation_type_price_day_of_the_week_policy PRIMARY KEY (policy_id)
);
CREATE TABLE t_accommodation_type_price_day_of_the_week_policy_item (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   constant_value DECIMAL NOT NULL,
   percentage_value DECIMAL NOT NULL,
   day_of_week VARCHAR(255) NOT NULL,
   type VARCHAR(255),
   policy_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_price_day_of_the_week_policy_item PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type_price_day_of_the_week_policy_item ADD CONSTRAINT FK_TACCOMMODATIONTYPEPRICEDAYOFTHEWEEKPOLICYITEM_ON_POLICY FOREIGN KEY (policy_id) REFERENCES t_accommodation_type_price_day_of_the_week_policy (policy_id);

CREATE TABLE t_accommodation_type_price_number_of_days_policy (
  policy_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   property_Id UUID,
   CONSTRAINT pk_t_accommodation_type_price_number_of_days_policy PRIMARY KEY (policy_id)
);

CREATE TABLE t_accommodation_type_price_number_of_days_policy_item (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   number_of_days INTEGER,
   constant_value DECIMAL,
   percentage_value DECIMAL,
   type VARCHAR(255),
   policy_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_price_number_of_days_policy_item PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type_price_number_of_days_policy_item ADD CONSTRAINT FK_TACCOMMODATIONTYPEPRICENUMBEROFDAYSPOLICYITEM_ON_POLICY FOREIGN KEY (policy_id) REFERENCES t_accommodation_type_price_number_of_days_policy (policy_id);

-- AMENITY
CREATE TABLE t_amenity (
  amenity_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255) NOT NULL,
   category_name UUID,
   description VARCHAR(255) NOT NULL,
   standard BOOLEAN,
   CONSTRAINT pk_t_amenity PRIMARY KEY (amenity_id)
);
CREATE TABLE t_amenity_category (
  category_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   category_name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_amenity_category PRIMARY KEY (category_id)
);
CREATE TABLE t_organization_amenity (
  organization_amenity_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   organization_id UUID,
   active BOOLEAN,
   amenity_id UUID,
   CONSTRAINT pk_t_organization_amenity PRIMARY KEY (organization_amenity_id)
);
CREATE TABLE t_property_amenity (
  property_amenity_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   property_id UUID,
   amenity_id UUID,
   CONSTRAINT pk_t_property_amenity PRIMARY KEY (property_amenity_id)
);

ALTER TABLE t_property_amenity ADD CONSTRAINT FK_T_PROPERTY_AMENITY_ON_AMENITY FOREIGN KEY (amenity_id) REFERENCES t_amenity (amenity_id);
ALTER TABLE t_organization_amenity ADD CONSTRAINT FK_T_ORGANIZATION_AMENITY_ON_AMENITY FOREIGN KEY (amenity_id) REFERENCES t_amenity (amenity_id);
ALTER TABLE t_amenity ADD CONSTRAINT FK_T_AMENITY_ON_CATEGORY_NAME FOREIGN KEY (category_name) REFERENCES t_amenity_category (category_id);

-- REGISTRATION

CREATE TABLE t_registration (
  registration_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   status VARCHAR(255) NOT NULL,
   source VARCHAR(255) NOT NULL,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_registration PRIMARY KEY (registration_id)
);


CREATE TABLE t_registration_party (
  party_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   registration_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_party PRIMARY KEY (party_id)
);

CREATE TABLE t_registration_party_guests (
  guest_id UUID NOT NULL,
   party_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_party_guests PRIMARY KEY (guest_id, party_id)
);
CREATE TABLE t_registration_booking (
  booking_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   start_date date NOT NULL,
   end_date date NOT NULL,
   accommodation_type_id UUID NOT NULL,
   accommodation_type_reservation_id UUID,
   registration_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_booking PRIMARY KEY (booking_id)
);

CREATE TABLE t_registration_booking_charge_ids (
  booking_id UUID NOT NULL,
   charge_id UUID NOT NULL
);

CREATE TABLE t_registration_booking_guests (
  booking_id UUID NOT NULL,
   guest_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_booking_guests PRIMARY KEY (booking_id, guest_id)
);

ALTER TABLE t_registration ADD CONSTRAINT FK_T_REGISTRATION_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_registration_booking ADD CONSTRAINT FK_T_REGISTRATION_BOOKING_ON_REGISTRATION FOREIGN KEY (registration_id) REFERENCES t_registration (registration_id);
ALTER TABLE t_registration_booking_guests ADD CONSTRAINT fk_tregboogue_on_guest_entity FOREIGN KEY (guest_id) REFERENCES t_guest (guest_id);
ALTER TABLE t_registration_booking_guests ADD CONSTRAINT fk_tregboogue_on_registration_booking_entity FOREIGN KEY (booking_id) REFERENCES t_registration_booking (booking_id);
ALTER TABLE t_registration_booking_charge_ids ADD CONSTRAINT fk_tregistrationbookingchargeids_on_registrationbookingentity FOREIGN KEY (booking_id) REFERENCES t_registration_booking (booking_id);
ALTER TABLE t_registration_party ADD CONSTRAINT FK_T_REGISTRATION_PARTY_ON_REGISTRATION FOREIGN KEY (registration_id) REFERENCES t_registration (registration_id);
ALTER TABLE t_registration_party_guests ADD CONSTRAINT fk_tregpargue_on_guest_entity FOREIGN KEY (guest_id) REFERENCES t_guest (guest_id);
ALTER TABLE t_registration_party_guests ADD CONSTRAINT fk_tregpargue_on_registration_party_entity FOREIGN KEY (party_id) REFERENCES t_registration_party (party_id);

-- REGISTRATION FOLIO
CREATE TABLE t_registration_folio (
  registration_folio_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   registration_id UUID,
   CONSTRAINT pk_t_registration_folio PRIMARY KEY (registration_folio_id)
);

CREATE TABLE t_registration_party_folio (
  party_folio_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   party_id UUID NOT NULL,
   registration_folio_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_party_folio PRIMARY KEY (party_folio_id)
);
CREATE TABLE t_registration_party_folio_charge (
  charge_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   type VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   amount DECIMAL NOT NULL,
   current VARCHAR(255) NOT NULL,
   date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   party_folio_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_party_folio_charge PRIMARY KEY (charge_id)
);
CREATE TABLE t_registration_party_folio_payment (
  payment_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   guest_id UUID NOT NULL,
   amount DECIMAL NOT NULL,
   current VARCHAR(255) NOT NULL,
   date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   party_folio_id UUID NOT NULL,
   CONSTRAINT pk_t_registration_party_folio_payment PRIMARY KEY (payment_id)
);

ALTER TABLE t_registration_folio ADD CONSTRAINT FK_T_REGISTRATION_FOLIO_ON_REGISTRATION FOREIGN KEY (registration_id) REFERENCES t_registration (registration_id);
ALTER TABLE t_registration_party_folio_payment ADD CONSTRAINT FK_T_REGISTRATION_PARTY_FOLIO_PAYMENT_ON_PARTY_FOLIO FOREIGN KEY (party_folio_id) REFERENCES t_registration_party_folio (party_folio_id);
ALTER TABLE t_registration_party_folio_charge ADD CONSTRAINT FK_T_REGISTRATION_PARTY_FOLIO_CHARGE_ON_PARTY_FOLIO FOREIGN KEY (party_folio_id) REFERENCES t_registration_party_folio (party_folio_id);
ALTER TABLE t_registration_party_folio ADD CONSTRAINT FK_T_REGISTRATION_PARTY_FOLIO_ON_REGISTRATION_FOLIO FOREIGN KEY (registration_folio_id) REFERENCES t_registration_folio (registration_folio_id);


-- RESERVATION
CREATE TABLE t_reservation (
  reservation_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   status VARCHAR(255) NOT NULL,
   source VARCHAR(255),
   main_guest_id UUID NOT NULL,
   reservation_folio_id UUID,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_reservation PRIMARY KEY (reservation_id)
);

CREATE TABLE t_reservation_guest (
  guest_id UUID NOT NULL,
   reservation_id UUID NOT NULL,
   CONSTRAINT pk_t_reservation_guest PRIMARY KEY (guest_id, reservation_id)
);
CREATE TABLE t_reservation_booking (
  reservation_booking_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   start_date date NOT NULL,
   end_date date NOT NULL,
   accommodation_type_id UUID NOT NULL,
   number_of_adults INTEGER NOT NULL,
   number_of_children INTEGER NOT NULL,
   accommodation_type_reservation_type_id UUID,
   reservation_id UUID,
   CONSTRAINT pk_t_reservation_booking PRIMARY KEY (reservation_booking_id)
);

CREATE TABLE t_reservation_booking_charge_ids (
  booking_id UUID NOT NULL,
   charge_id UUID NOT NULL
);


ALTER TABLE t_reservation ADD CONSTRAINT FK_T_RESERVATION_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_reservation_booking ADD CONSTRAINT FK_T_RESERVATION_BOOKING_ON_RESERVATION FOREIGN KEY (reservation_id) REFERENCES t_reservation (reservation_id);
ALTER TABLE t_reservation_booking_charge_ids ADD CONSTRAINT fk_treservationbookingchargeids_on_reservationbookingentity FOREIGN KEY (booking_id) REFERENCES t_reservation_booking (reservation_booking_id);
ALTER TABLE t_reservation ADD CONSTRAINT FK_T_RESERVATION_ON_MAIN_GUEST FOREIGN KEY (main_guest_id) REFERENCES t_guest (guest_id);
ALTER TABLE t_reservation_guest ADD CONSTRAINT fk_tresgue_on_guest_entity FOREIGN KEY (guest_id) REFERENCES t_guest (guest_id);
ALTER TABLE t_reservation_guest ADD CONSTRAINT fk_tresgue_on_reservation_entity FOREIGN KEY (reservation_id) REFERENCES t_reservation (reservation_id);

-- RESERVATION FOLIO

CREATE TABLE t_reservation_folio (
  reservation_folio_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   reservation_id UUID NOT NULL,
   type VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_reservation_folio PRIMARY KEY (reservation_folio_id)
);
CREATE TABLE t_reservation_folio_payment (
  payment_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   guest_id UUID NOT NULL,
   amount DECIMAL NOT NULL,
   current VARCHAR(255) NOT NULL,
   date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   reservation_folio_id UUID,
   CONSTRAINT pk_t_reservation_folio_payment PRIMARY KEY (payment_id)
);
CREATE TABLE t_reservation_folio_charge (
  charge_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   type VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   amount DECIMAL NOT NULL,
   current VARCHAR(255) NOT NULL,
   date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   should_by_paid BOOLEAN NOT NULL,
   reservation_folio_id UUID,
   CONSTRAINT pk_t_reservation_folio_charge PRIMARY KEY (charge_id)
);

ALTER TABLE t_reservation_folio_charge ADD CONSTRAINT FK_T_RESERVATION_FOLIO_CHARGE_ON_RESERVATION_FOLIO FOREIGN KEY (reservation_folio_id) REFERENCES t_reservation_folio (reservation_folio_id);
ALTER TABLE t_reservation_folio_payment ADD CONSTRAINT FK_T_RESERVATION_FOLIO_PAYMENT_ON_RESERVATION_FOLIO FOREIGN KEY (reservation_folio_id) REFERENCES t_reservation_folio (reservation_folio_id);
ALTER TABLE t_reservation_folio ADD CONSTRAINT FK_T_RESERVATION_FOLIO_ON_RESERVATION FOREIGN KEY (reservation_id) REFERENCES t_reservation (reservation_id);

-- RESERVATION PAYMENT POLICY
CREATE TABLE t_reservation_payment_policy (
  policy_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   type VARCHAR(255) NOT NULL,
   fixed_value DECIMAL NOT NULL,
   number_of_days_before_registration INTEGER NOT NULL,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_reservation_payment_policy PRIMARY KEY (policy_id)
);

ALTER TABLE t_reservation_payment_policy ADD CONSTRAINT uc_t_reservation_payment_policy_property_id UNIQUE (property_id);

ALTER TABLE t_reservation_payment_policy ADD CONSTRAINT FK_T_RESERVATION_PAYMENT_POLICY_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);

















