package com.digi.xbee.example;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Grid {
    public static final double TOP_LATITUDE = 36.137598;
    public static final double LEFT_LONGITUDE = -94.140644;
    public static final double BOTTOM_LATITUDE = 36.135397;
    public static final double RIGHT_LONGITUDE = -94.137925;
    public static final String BACKGROUND_PNG = "images/Background_800ftsq.PNG";

    // Water pixel determined in isPixelWater by green component of pixel RGB
    private static final int WATER_GREEN = 218;

    private static HashMap<String, Coordinate> coordinateGrid;
    private static BufferedImage background;

    public static double gridHeight, gridWidth;
    
    Coordinate NW;
    Coordinate NE;
    Coordinate SW;
    Coordinate SE;

    Grid() throws IOException {
        try {
            background = ImageIO.read(new File(BACKGROUND_PNG));
        } catch (IOException e) {
            throw e;
        }
        coordinateGrid = new HashMap<String, Coordinate>();
        NW = new Coordinate(TOP_LATITUDE, LEFT_LONGITUDE);
        NE = new Coordinate(TOP_LATITUDE, RIGHT_LONGITUDE);
        SW = new Coordinate(BOTTOM_LATITUDE, RIGHT_LONGITUDE);
        SE = new Coordinate(BOTTOM_LATITUDE, LEFT_LONGITUDE);
        coordinateGrid.put("NW", NW);
        coordinateGrid.put("NE", NE);
        coordinateGrid.put("SW", SW);
        coordinateGrid.put("SE", SE);
        gridHeight = TOP_LATITUDE - BOTTOM_LATITUDE;
        gridWidth = RIGHT_LONGITUDE - LEFT_LONGITUDE;
    }

    // calculates the coordinate of the mouse click based on the proportions of the x, y position
    public static Coordinate calculateCoordinate(int mouseX, int mouseY) {
        double lat = TOP_LATITUDE - (((double)mouseY / (double)View.height) * gridHeight);
        double lon = LEFT_LONGITUDE + (((double)mouseX / (double)View.width) * gridWidth);
        return new Coordinate(lat, lon);
    }
    
    public static Point calculatePoint(Coordinate coord) {
		int x = (int)((coord.getLongitude() - LEFT_LONGITUDE) * (View.width / gridWidth));
		int y = (int)(View.height - ((coord.getLatitude() - BOTTOM_LATITUDE) * (View.height / gridHeight)));
		return new Point(x, y);		
    }

    public static boolean isPixelWater(int x, int y) {
        Color pixelColor = new Color(background.getRGB(x, y));
        if (pixelColor.getGreen() == WATER_GREEN) {
            return true;
        }
        return false;
    }

    public BufferedImage getTerrainImage() {
        return background;
    }
}
