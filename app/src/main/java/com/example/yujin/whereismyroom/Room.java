package com.example.yujin.whereismyroom;

import java.io.Serializable;

public class Room implements Serializable {
    private String id;
    private String deposit;
    private String rentMonth;
    private String rentType;
    private String utilities;
    private String includedUtilities;
    private String stationName;
    private String routeName;
    private String buildFloor;
    private String myFloor;
    private String direction;
    private String roomType;
    private String roomSizeM;
    private String roomSizeP;
    private String option;
    private String animal;
    private String elevator;
    private String parking;
    private String detail;

    public Room (String id, String deposit, String rentMonth, String rentType, String utilities, String includedUtilities
                , String stationName, String routeName, String buildFloor, String myFloor, String direction, String roomType
                , String roomSizeM, String roomSizeP
                , String option, String animal, String elevator, String parking, String detail) {
        this.id = id;
        this.deposit = deposit;
        this.utilities = utilities;
        this.rentMonth = rentMonth;
        this.rentType = rentType;
        this.includedUtilities = includedUtilities;
        this.stationName = stationName;
        this.routeName = routeName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(String rentMonth) {
        this.rentMonth = rentMonth;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public String getIncludedUtilities() {
        return includedUtilities;
    }

    public void setIncludedUtilities(String includedUtilities) {
        this.includedUtilities = includedUtilities;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBuildFloor() {
        return buildFloor;
    }

    public void setBuildFloor(String buildFloor) {
        this.buildFloor = buildFloor;
    }

    public String getMyFloor() {
        return myFloor;
    }

    public void setMyFloor(String myFloor) {
        this.myFloor = myFloor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomSizeM() {
        return roomSizeM;
    }

    public void setRoomSizeM(String roomSizeM) {
        this.roomSizeM = roomSizeM;
    }

    public String getRoomSizeP() {
        return roomSizeP;
    }

    public void setRoomSizeP(String roomSizeP) {
        this.roomSizeP = roomSizeP;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
