package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mobile;

    private String password;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookingList;

    @OneToOne
    @JoinColumn
    public Cab cab;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public Driver(int id, String mobile, String password, List<TripBooking> tripBookingList) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
    }

    public Driver(int id, String mobile, String password, List<TripBooking> tripBookingList, Cab cab) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
        this.cab = cab;
    }

    public Driver(String mobile, String password, List<TripBooking> tripBookingList, Cab cab) {
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
        this.cab = cab;
    }

    public Driver(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public Driver(){

    }
}