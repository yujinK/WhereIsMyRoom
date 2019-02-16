package com.example.yujin.whereismyroom;

import java.util.List;

public class Subway {
    private String stationName;
    private String routeName;

    public Subway(String stationName, String routeName) {
        this.stationName = stationName;
        this.routeName = routeName;
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
}
