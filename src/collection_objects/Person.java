package collection_objects;

public class Person {
    public Person() {
        this.name = "";
        this.passportID = "";
        this.location = new Location();
    }
    public Person(String name, String passportID, Location location) {
        this.name = name;
        this.passportID = passportID;
        this.location = location;
    }

    private String name;
    private String passportID;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
