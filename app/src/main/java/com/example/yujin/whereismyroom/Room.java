package com.example.yujin.whereismyroom;

import java.io.Serializable;

public class Room implements Serializable {
    public String id;
    public String deposit;
    public String rentMonth;
    public String rentType;
    public String utilities;
    public String includedUtilities;
    public String buildFloor;
    public String myFloor;
    public String direction;
    public String roomType;
    public String roomSizeM;
    public String roomSizeP;
    public String option;
    public String animal;
    public String elevator;
    public String parking;
    public String detail;

    public Room (String id, String deposit, String rentMonth, String rentType, String utilities, String includedUtilities
                , String buildFloor, String myFloor, String direction, String roomType, String roomSizeM, String roomSizeP
                , String option, String animal, String elevator, String parking, String detail) {
        this.id = id;
        this.deposit = deposit;
        this.utilities = utilities;
        this.rentMonth = rentMonth;
        this.rentType = rentType;
        this.includedUtilities = includedUtilities;
        this.buildFloor = buildFloor;
        this.myFloor = myFloor;
        this.direction = direction;
        this.roomType = roomType;
        this.roomSizeM = roomSizeM;
        this.roomSizeP = roomSizeP;
        this.option = option;
        this.animal = animal;
        this.elevator = elevator;
        this.parking = parking;
        this.detail = detail;
    }
}
