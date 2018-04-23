package com.digi.xbee.example;

public class Coordinate {
    private double longitude;
    private double latitude;

    Coordinate(double lon, double lat) {
        longitude = lon;
        latitude = lat;
    }

    public Coordinate(Coordinate c) {
		longitude = c.longitude;
		latitude = c.latitude;
	}

	void setLongitude(double l) {
        longitude = l;
    }

    double getLongitude() {
        return longitude;
    }

    void setLatitude(double l) {
        latitude = l;
    }

    double getLatitude() {
        return latitude;
    }
    
    float getLongitudeAsFloat() {
    		return (float)longitude;
    }
    
    float getLatitudeAsFloat() {
    		return (float)latitude;
    }
}