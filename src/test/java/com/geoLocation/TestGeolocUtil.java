package com.geoLocation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGeolocUtil {

    @Test
    void testLocationByCityState() {
        GeoLocationService service = new GeoLocationService();
        try {
            Location location = service.getGeoLocation("Madison, WI");
            assertNotNull(location);
            assertEquals("Madison", location.getName());
            assertEquals("US", location.getCountry());
        } catch (Exception e) {
            fail("Exception should not have occurred: " + e.getMessage());
        }
    }

    @Test
    void testLocationByZip() {
        GeoLocationService service = new GeoLocationService();
        try {
            Location location = service.getGeoLocation("90210");
            assertNotNull(location);
            assertEquals("Beverly Hills", location.getName());
        } catch (Exception e) {
            fail("Exception should not have occurred: " + e.getMessage());
        }
    }
}
