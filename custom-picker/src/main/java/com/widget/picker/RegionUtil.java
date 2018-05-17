package com.widget.picker;

import android.content.Context;

import com.widget.picker.bean.RegionSupportBean;
import com.widget.picker.earth.City;
import com.widget.picker.earth.Country1;
import com.widget.picker.earth.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:获取地区数据
 * Created by Kevin.Li on 2018-01-10.
 */
public class RegionUtil {
    private static List<Country1> country1List = new ArrayList<>();

    public static List<RegionSupportBean> getList1Data(Context context) {
        intEarthData(context);
        List<RegionSupportBean> list = new ArrayList<>();
        RegionSupportBean rsb;
        for (int i = 0; i < country1List.size(); i++) {
            rsb = new RegionSupportBean();
            rsb.setName(country1List.get(i).getCountryName());
            rsb.setCode(country1List.get(i).getCountryID());
            list.add(rsb);
        }
        return list;
    }

    public static List<RegionSupportBean> getList2Data(Context context, String code) {
        intEarthData(context);
        List<RegionSupportBean> list = new ArrayList<>();
        RegionSupportBean rsb;
        for (int i = 0; i < country1List.size(); i++) {
            if (code.equals(country1List.get(i).getCountryID())) {
                List<Province> provinces = country1List.get(i).getStation();
                if (provinces == null) return list;
                for (Province province : provinces) {
                    rsb = new RegionSupportBean();
                    rsb.setName(province.getStationName());
                    rsb.setCode(province.getStationID());
                    list.add(rsb);
                }
                return list;
            }
        }
        return list;
    }

    /**
     * @param code  国家id
     * @param code2 省份id
     */
    public static List<RegionSupportBean> getList3Data(Context context, String code, String code2) {
        intEarthData(context);
        List<RegionSupportBean> list = new ArrayList<>();
        RegionSupportBean rsb;
        for (int i = 0; i < country1List.size(); i++) {
            if (code.equals(country1List.get(i).getCountryID())) {
                List<Province> provinces = country1List.get(i).getStation();
                if (provinces == null) return list;
                for (Province province : provinces) {
                    if (code2.equals(province.getStationID())) {
                        if (province.getCity() == null) return list;
                        for (City city : province.getCity()) {
                            rsb = new RegionSupportBean();
                            rsb.setName(city.getCityName());
                            rsb.setCode(city.getCityID());
                            list.add(rsb);
                        }
                        return list;
                    }
                }
            }
        }
        return list;
    }

    /**
     * 初始化数据
     */
    private static void intEarthData(Context context) {
        if (country1List.size() == 0) {
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("earth.json")));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null)
                    sb.append(line);
                br.close();
                JSONObject jb = new JSONObject(sb.toString());
                JSONObject earth = jb.getJSONObject("Earth");
                JSONArray countryArray = earth.getJSONArray("Country");
                Country1 country1;
                for (int i = 0; i < countryArray.length(); i++) {
                    country1 = new Country1();
                    JSONObject country = countryArray.getJSONObject(i);
                    country1.setCountryID(country.getString("CountryID"));
                    country1.setCountryName(country.getString("CountryName"));
                    if (country.has("Station")) {
                        JSONArray stationArray = country.getJSONArray("Station");
                        List<Province> provinceList = new ArrayList<>();
                        Province province;
                        for (int j = 0; j < stationArray.length(); j++) {
                            province = new Province();
                            JSONObject station = stationArray.getJSONObject(j);
                            province.setStationID(station.getString("StationID"));
                            province.setStationName(station.getString("StationName"));
                            if (station.has("City")) {
                                List<City> cityList = new ArrayList<>();
                                City city;
                                JSONObject cityObject;
                                try {
                                    JSONArray cityArray = station.getJSONArray("City");
                                    for (int k = 0; k < cityArray.length(); k++) {
                                        cityObject = cityArray.getJSONObject(k);
                                        city = new City();
                                        city.setCityID(cityObject.getString("CityID"));
                                        city.setCityName(cityObject.getString("CityName"));
                                        cityList.add(city);
                                    }
                                } catch (Exception e) {
                                    cityObject = station.getJSONObject("City");
                                    city = new City();
                                    city.setCityID(cityObject.getString("CityID"));
                                    city.setCityName(cityObject.getString("CityName"));
                                    cityList.add(city);
                                    e.printStackTrace();
                                }
                                province.setCity(cityList);
                            }
                            provinceList.add(province);
                        }
                        country1.setStation(provinceList);
                    }
                    country1List.add(country1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
