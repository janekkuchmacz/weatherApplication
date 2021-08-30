package pl.kuchmaczpogoda.model;

import java.time.ZoneId;
import java.util.ArrayList;

public class Cities {

    public static String SELECTED_CITY;
    public static String SELECTED_TIMEZONE;
    public static String SELECTED_CITY_TWO;
    public static String SELECTED_TIMEZONE_TWO;
    public static ArrayList<String> cities = new ArrayList<String>() {{
        add("Kraków");
        add("Warszawa");
        add("Londyn");
    }};
    public static ArrayList<String>timeZones = new ArrayList<String>(){{
        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            add(zoneId);
        }
    }};



    public static ArrayList<String> getCities() {
        return cities;
    }

    public static ArrayList<String> getTimeZones() {
        return timeZones;
    }

    public static String getSelectedCity() {
        return SELECTED_CITY;
    }

    public static void setSelectedCity(String selectedCity) {
        SELECTED_CITY = selectedCity;
    }

    public static String getSelectedTimezone() {
        return SELECTED_TIMEZONE;
    }

    public static void setSelectedTimezone(String selectedTimezone) {
        SELECTED_TIMEZONE = selectedTimezone;
    }

    public static String getSelectedCityTwo() {
        return SELECTED_CITY_TWO;
    }

    public static void setSelectedCityTwo(String selectedCityTwo) {
        SELECTED_CITY_TWO = selectedCityTwo;
    }

    public static String getSelectedTimezoneTwo() {
        return SELECTED_TIMEZONE_TWO;
    }

    public static void setSelectedTimezoneTwo(String selectedTimezoneTwo) {
        SELECTED_TIMEZONE_TWO = selectedTimezoneTwo;
    }
}
