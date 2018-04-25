package com.digi.xbee.example;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    protected BufferedImage image;
    protected Point XYPosition;
    protected Coordinate coordinatePosition;
	private int id;

    Sprite() {}
    Sprite(int id, int x, int y, Coordinate c) {
    	this.id = id;
        XYPosition = new Point(x, y);
        coordinatePosition = c;
    }

    // before calling this, check pixel color for water
    public void setPosition(int x, int y, Coordinate c) {
    	if (Grid.isPixelWater(x, y)) {
	        XYPosition = new Point(x, y);
	        coordinatePosition = c;
    	} else {
    		System.out.println("Outside of valid area!");
    	}
    }
    
    public void setPositionFromCoordinates(Coordinate c) {
    	Point pos = Grid.calculatePoint(c);
    	if ((pos.x < 0 || pos.x > View.width) 
    		|| (pos.y < 0 || pos.y > View.height)) {
    		System.out.println("Point outside of valid range!");
    		System.out.println("Valid range of coordinates: " + Grid.BOTTOM_LATITUDE + " - " + Grid.TOP_LATITUDE + "N, " + Grid.LEFT_LONGITUDE + " - " + Grid.RIGHT_LONGITUDE + "E");
    	} else {
	    	if (Grid.isPixelWater(pos.x, pos.y)) {
		        XYPosition = pos;
		        coordinatePosition = c;
	    	} else {
	    		System.out.println("Outside of valid area!");
	    	}
    	}
    }
    
    public void setPositionFromXY(int x, int y) {
    	if (Grid.isPixelWater(x, y)) {
	        XYPosition = new Point(x, y);;
	        coordinatePosition = Grid.calculateCoordinate(x, y);
    	} else {
    		System.out.println("Outside of valid area!");
    	}
    }

    public Coordinate getCoordinatePosition() { return coordinatePosition; }
    public int getXpos() { return XYPosition.x; }
    public int getYpos() { return XYPosition.y; }
	public int getId() { return id; }
}
