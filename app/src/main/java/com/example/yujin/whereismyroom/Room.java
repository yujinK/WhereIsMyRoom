package com.example.yujin.whereismyroom;

public class Room {
    private int id;
    private int deposit;
    private int rentMonth;
    private int utilities;
    private String includedUtilities;
    private int buildFloor;
    private int myFloor;
    private String direction;
    private String roomType;
    private float roomSizeM;
    private float roomSizeP;
    private String option;
    private String animal;
    private boolean elevator;
    private boolean parking;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getRentMonth() {
        return rentMonth;
    }

    public void setRentMonth(int rentMonth) {
        this.rentMonth = rentMonth;
    }

    public int getUtilities() {
        return utilities;
    }

    public void setUtilities(int utilities) {
        this.utilities = utilities;
    }

    public String getIncludedUtilities() {
        return includedUtilities;
    }

    public void setIncludedUtilities(String includedUtilities) {
        this.includedUtilities = includedUtilities;
    }

    public int getBuildFloor() {
        return buildFloor;
    }

    public void setBuildFloor(int buildFloor) {
        this.buildFloor = buildFloor;
    }

    public int getMyFloor() {
        return myFloor;
    }

    public void setMyFloor(int myFloor) {
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

    public float getRoomSizeM() {
        return roomSizeM;
    }

    public void setRoomSizeM(float roomSizeM) {
        this.roomSizeM = roomSizeM;
    }

    public float getRoomSizeP() {
        return roomSizeP;
    }

    public void setRoomSizeP(float roomSizeP) {
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

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
