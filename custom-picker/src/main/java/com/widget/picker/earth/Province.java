package com.widget.picker.earth;

import java.util.List;

/**
 * Description:省份
 * Created by Kevin.Li on 2018-03-05.
 */
public class Province {
    private String StationID;
    private String StationName;
    private List<City> City;

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public List<com.widget.picker.earth.City> getCity() {
        return City;
    }

    public void setCity(List<com.widget.picker.earth.City> city) {
        City = city;
    }
}
