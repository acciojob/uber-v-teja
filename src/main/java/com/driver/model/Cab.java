package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int perKmRate;

    private boolean available;

    @OneToOne(mappedBy = "cab", cascade = CascadeType.ALL)
    private Driver driver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Cab(int id, int perKmRate, boolean available) {
        this.id = id;
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Cab(int perKmRate, boolean available) {
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Cab(){

    }
}