package com.company.collection_objects;

public class Location {
    public Location() {
        this.x = (double) 0;
        this.y = 0L;
        this.z = 0;

    }
    public Location(String location) {
        this.x = Double.parseDouble(location.split(";")[0].trim().replace(',', '.'));
        this.y = Long.parseLong(location.split(";")[1].trim());
        this.z = Float.parseFloat(location.split(";")[2].trim().replace(',', '.'));

    }

    private Double x;
    public Double getX() {

        return x;
    }

    private Long y;
    public Long getY() {

        return y;
    }

    private float z;
    public float getZ() {

        return z;
    }
}
