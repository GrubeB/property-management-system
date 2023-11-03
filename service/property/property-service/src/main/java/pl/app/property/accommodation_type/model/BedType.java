package pl.app.property.accommodation_type.model;

public enum BedType {
    SINGLE_BED(1, 1),
    TWIN_BED(2, 2),
    DOUBLE_BED(2, 2),
    QUEEN_BED(2, 3),
    KING_BED(2, 4),
    HOLLYWOOD_BED(2, 2),
    STUDIO_BED(1, 1),
    DAYBED(2, 2),
    SOFA_BED(1, 1),
    ROLLAWAY_BED(1, 1),
    CRIB(1, 1),
    WATER_BED(1, 1),
    DOUBLE_WATER_BED(2, 2),
    FUTON(1, 1),
    WALL_BED(1, 1),
    DOUBLE_WALL_BED(2, 2);

    BedType(Integer numberOfPeople, Integer maxNumberOfPeople) {
    }
}
