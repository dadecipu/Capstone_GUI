package com.digi.xbee.example;

public class Coordinate {
    private double latitude;
    private double longitude;

    Coordinate(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    public Coordinate(Coordinate c) {
		latitude = c.latitude;
		longitude = c.longitude;
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