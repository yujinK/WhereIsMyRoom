package com.example.yujin.whereismyroom;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Subway {
    private String stationId;
    private String stationName;
    private List<String> routeNameList;

    public Subway(String stationId, String stationName, String routeName) {
        this.stationId = stationId;
        this.stationName = stationName;
        routeNameList = new ArrayList<>();
        routeNameList.add(routeName);
    }

    public Subway(String stationName) {
        this.stationName = stationName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<String> getRouteNameList() {
        return routeNameList;
    }

    public void setRouteNameList(List<String> routeNameList) {
        this.routeNameList = routeNameList;
    }

    public void addRouteNameList(String routeName) {
        routeNameList.add(routeName);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Subway subway = (Subway) obj;

        if (stationName != null && stationName.equals(subway.stationName)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return stationName != null ? stationName.hashCode() : 0;
    }
}
