import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Grid {
    public static final double TOP_LATITUDE = 36.137598;
    public static final double LEFT_LONGITUDE = -91.140645;
    public static final double BOTTOM_LATITUDE = 36.135402;
    public static final double RIGHT_LONGITUDE = -91.140645;

    // Water pixel determined in isPixelWater by green component of pixel RGB
    private static final int WATER_GREEN = 218;

    private static HashMap<String, Coordinate> coordinateGrid;
    private static BufferedImage background;

    Coordinate NW;
    Coordinate NE;
    Coordinate SW;
    Coordinate SE;

    Grid() throws IOException {
        try {
            background = ImageIO.read(new File("Background_800ftsq.PNG"));
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
    }

    // calculates the coordinate of the mouse click based on the proportions of the x, y position
    public static Coordinate calculateCoordinate(int mouseX, int mouseY, int windowWidth, int windowHeight) {
        double gridHeight = coordinateGrid.get("NE").longitude - coordinateGrid.get("SE").longitude;
        double gridWidth = -coordinateGrid.get("NE").latitude - coordinateGrid.get("NW").latitude;

        double lat = coordinateGrid.get("NW").latitude - ((mouseX / windowWidth) * gridWidth);
        double lon = coordinateGrid.get("NE").longitude + ((mouseY / windowHeight) * gridHeight);
        return new Coordinate(lat, lon);
    }

    public boolean isPixelWater(int x, int y) {
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
