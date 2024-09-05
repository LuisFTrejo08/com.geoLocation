package com.geoLocation;

import java.io.IOException;
import java.util.List;

public class GeolocUtil {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide location inputs (city/state or zip code).");
            return;
        }

        GeoLocationService geoLocationService = new GeoLocationService();

        for (String location : args) {
            try {
                Location result = geoLocationService.getGeoLocation(location);
                if (result != null) {
                    System.out.printf("Location: %s, Latitude: %f, Longitude: %f\n",
                            result.getName(), result.getLat(), result.getLon());
                } else {
                    System.out.println("No results found for: " + location);
                }
            } catch (IOException e) {
                System.err.println("Error fetching geolocation for " + location + ": " + e.getMessage());
            }
        }
    }
}
