package com.example.db.locationhelper.beans;

/**
 * Created by db on 8/23/15.
 */
public class LocationInfo {

    /*
    Alt+Insert快捷键快速生成parcelable
     */

    public String location;
    public String date;
    public String ways;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWays() {
        return ways;
    }

    public void setWays(String ways) {
        this.ways = ways;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
