CREATE OR REPLACE FUNCTION public.get_category_id_by_name(target_category_name text)
RETURNS uuid AS
$BODY$
    SELECT category_id
    FROM public.t_amenity_category
    WHERE category_name = target_category_name;
$BODY$
LANGUAGE SQL;

ALTER FUNCTION public.get_category_id_by_name(target_category_name text)
    OWNER TO postgres;

ALTER TABLE t_amenity RENAME COLUMN category_name TO category;

INSERT INTO t_amenity(amenity_id, name, category, description, standard,
 created_by, created_datetime, last_modified_by, last_modified_datetime) VALUES
 (gen_random_uuid(), '24-hour front desk', get_category_id_by_name('Check-in & Front-Desk Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Concierge service', get_category_id_by_name('Check-in & Front-Desk Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Wake-up call service', get_category_id_by_name('Check-in & Front-Desk Services'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Complimentary Wi-Fi', get_category_id_by_name('General Guest Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'In-room safe', get_category_id_by_name('General Guest Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Luggage storage', get_category_id_by_name('General Guest Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Currency exchange', get_category_id_by_name('General Guest Services'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Swimming pool', get_category_id_by_name('Pool, Spa, & Wellness'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Spa and wellness facilities', get_category_id_by_name('Pool, Spa, & Wellness'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Sauna and steam room', get_category_id_by_name('Pool, Spa, & Wellness'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Fitness center or gym', get_category_id_by_name('Entertainment & Recreation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Game room', get_category_id_by_name('Entertainment & Recreation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Entertainment or live shows', get_category_id_by_name('Entertainment & Recreation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Outdoor seating areas', get_category_id_by_name('Entertainment & Recreation'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'On-site restaurant', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Room service', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Breakfast buffet', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Bar or lounge', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Coffee shop or cafe', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Mini-bar in rooms', get_category_id_by_name('Food & Beverage'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Airport shuttle service', get_category_id_by_name('Parking & Transportation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Valet parking', get_category_id_by_name('Parking & Transportation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Car rental services', get_category_id_by_name('Parking & Transportation'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Parking facilities', get_category_id_by_name('Parking & Transportation'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Babysitting or childcare services', get_category_id_by_name('Family Services & Activities'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Children''s play area or playground', get_category_id_by_name('Family Services & Activities'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Kid''s club', get_category_id_by_name('Family Services & Activities'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Family rooms or suites', get_category_id_by_name('Family Services & Activities'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Library or reading room', get_category_id_by_name('Common Areas'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Laundry and dry-cleaning services', get_category_id_by_name('Cleaning Services'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Meeting and conference rooms', get_category_id_by_name('Business Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Business center', get_category_id_by_name('Business Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'High-speed internet access', get_category_id_by_name('Business Services'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Fax and photocopy services', get_category_id_by_name('Business Services'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'On-site shopping', get_category_id_by_name('Shopping'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Accessibility features for guests with disabilities', get_category_id_by_name('Accessibility'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'CCTV surveillance', get_category_id_by_name('Safety & Security'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Fire safety systems', get_category_id_by_name('Safety & Security'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Smoke detectors', get_category_id_by_name('Safety & Security'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Security personnel', get_category_id_by_name('Safety & Security'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Eco-friendly initiatives like recycling programs and energy-efficient lighting', get_category_id_by_name('Environment & Sustainability'), '', TRUE, '-', NOW(), '-', NOW()),

 (gen_random_uuid(), 'Toiletries', get_category_id_by_name('Sanitation, Hygiene, & Guest Health'), '', TRUE, '-', NOW(), '-', NOW()),
 (gen_random_uuid(), 'Cleaning and sanitization practices', get_category_id_by_name('Sanitation, Hygiene, & Guest Health'), '', TRUE, '-', NOW(), '-', NOW());