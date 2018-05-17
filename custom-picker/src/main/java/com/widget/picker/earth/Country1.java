package com.widget.picker.earth;

import java.util.List;

/**
 * Description:国家
 * Created by Kevin.Li on 2018-03-05.
 */
public class Country1 {
    private String CountryID;
    private String CountryName;
    private List<Province> Station;

    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(String countryID) {
        CountryID = countryID;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public List<Province> getStation() {
        return Station;
    }

    public void setStation(List<Province> station) {
        Station = station;
    }
}
