package pl.app.property.property.model;

public enum PropertyType {
    APARTMENT("Apartment"),
    HOLIDAY_HOME("Holiday home"),
    HOTEL("Hotel"),
    HOMESTAY("Homestay"),
    HOME_AND_BREAKFAST("Bed and breakfast"),
    GUEST_HOUSE("Guest house"),
    CHALETS("Chalet"),
    LODGE("Lodge"),
    FARM_STAY("Farm stays"),
    VILLA("Villa"),
    RESORT("Resort"),
    HOSTEL("Hostel"),
    HOLIDAY_PARK("Holiday park"),
    CAMPSITE("Campsite"),
    MOTEL("Motel"),
    COUNTRY_HOUSE("Country house"),
    LUXURY_TENT("Luxury tent"),
    BOAT("Boat"),
    CAPSULE_HOTEL("Capsule hotel"),
    STUDENT_ACCOMMODATION("Student accommodation");

    private final String name;

    PropertyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
