package com.geoLocation;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GeoLocationService {
    private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239";
    private static final String GEO_URL = "http://api.openweathermap.org/geo/1.0";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Location getGeoLocation(String query) throws IOException {
        if (query.matches("\\d{5}")) {
            return getLocationByZip(query);
        } else {
            return getLocationByCityState(query);
        }
    }

    private Location getLocationByZip(String zip) throws IOException {
        String url = String.format("%s/zip?zip=%s,US&appid=%s", GEO_URL, zip, API_KEY);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), Location.class);
            }
        }
        return null;
    }

    private Location getLocationByCityState(String cityState) throws IOException {
        String[] parts = cityState.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid city, state format. Expected 'City, State'");
        }
        String city = parts[0].trim();
        String state = parts[1].trim();
        String url = String.format("%s/direct?q=%s,%s,US&limit=1&appid=%s", GEO_URL, city, state, API_KEY);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Location[] locations = objectMapper.readValue(response.body().string(), Location[].class);
                if (locations.length > 0) {
                    return locations[0];
                }
            }
        }
        return null;
    }
}

