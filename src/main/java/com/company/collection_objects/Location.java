package com.company.collection_objects;

import java.io.Serializable;

public class Location implements Serializable {
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

    public Location(Location other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
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

    public static void main(String[] args) {
        Location l1 = new Location();
        Location l2 = new Location(l1);
        l1.x = (double) 1;
        l1.y = (long) 2;
        l1.z = 3;
        System.out.println(l2.x + " " + l2.y + " " + l2.z);

    }
}
