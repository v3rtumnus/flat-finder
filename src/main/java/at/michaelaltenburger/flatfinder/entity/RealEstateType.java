package at.michaelaltenburger.flatfinder.entity;

public enum RealEstateType {
    FLAT("flat"),
    HOUSE("house");

    String name;

    RealEstateType(String name) {
        this.name = name;
    }
}
