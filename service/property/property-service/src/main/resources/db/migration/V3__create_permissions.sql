CREATE EXTENSION pgcrypto;
INSERT INTO t_permission(permission_id, permission_level,  permission_name, created_by, created_datetime, last_modified_by, last_modified_datetime) VALUES

(gen_random_uuid(),'ROOT', 'ACCOMMODATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ACCOMMODATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'ACCOMMODATION_AVAILABILITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_AVAILABILITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_AVAILABILITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ACCOMMODATION_AVAILABILITY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_AVAILABILITY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_AVAILABILITY_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'ACCOMMODATION_PRICE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_PRICE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_PRICE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ACCOMMODATION_PRICE_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_PRICE_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_PRICE_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'ACCOMMODATION_PRICE_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_PRICE_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_PRICE_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ACCOMMODATION_PRICE_POLICY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_PRICE_POLICY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_PRICE_POLICY_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'ACCOMMODATION_TYPE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_TYPE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_TYPE_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ACCOMMODATION_TYPE_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ACCOMMODATION_TYPE_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ACCOMMODATION_TYPE_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'AMENITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'AMENITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'AMENITY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'AMENITY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'AMENITY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'AMENITY_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'GUEST_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'GUEST_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'GUEST_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'GUEST_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'GUEST_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'GUEST_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'ORGANIZATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ORGANIZATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ORGANIZATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'ORGANIZATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'ORGANIZATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'ORGANIZATION_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'PROPERTY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'PROPERTY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'PROPERTY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'PROPERTY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'PROPERTY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'PROPERTY_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'REGISTRATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'REGISTRATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'REGISTRATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'REGISTRATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'REGISTRATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'REGISTRATION_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'REGISTRATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'REGISTRATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'REGISTRATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'REGISTRATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'REGISTRATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'REGISTRATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'RESERVATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'RESERVATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'RESERVATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_FOLIO_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'RESERVATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_FOLIO_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'RESERVATION_PAYMENT_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_PAYMENT_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_PAYMENT_POLICY_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'RESERVATION_PAYMENT_POLICY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'RESERVATION_PAYMENT_POLICY_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'RESERVATION_PAYMENT_POLICY_WRITE', '-',NOW(),'-', NOW()),

(gen_random_uuid(),'ROOT', 'USER_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'USER_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'USER_READ', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ROOT', 'USER_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'ORGANIZATION', 'USER_WRITE', '-',NOW(),'-', NOW()),
(gen_random_uuid(),'PROPERTY', 'USER_WRITE', '-',NOW(),'-', NOW());