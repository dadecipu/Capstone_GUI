import java.util.Map;

public class Grid {
    public static final double TOP_LATITUDE = 36.137598;
    public static final double LEFT_LONGITUDE = -91.140645;
    public static final double BOTTOM_LATITUDE = 36.135402;
    public static final double RIGHT_LONGITUDE = -91.140645;

    private static Map<String, Coordinate> coordinateGrid;


    Grid() {
        coordinateGrid.put("NW", new Coordinate(TOP_LATITUDE, LEFT_LONGITUDE));
        coordinateGrid.put("NE", new Coordinate(TOP_LATITUDE, RIGHT_LONGITUDE));
        coordinateGrid.put("SW", new Coordinate(BOTTOM_LATITUDE, RIGHT_LONGITUDE));
        coordinateGrid.put("SE", new Coordinate(BOTTOM_LATITUDE, LEFT_LONGITUDE));
    }

    public Coordinate calculateCoordinate(int mouseX, int mouseY, int windowWidth, int windowHeight) {
        double gridHeight = coordinateGrid.get("NE").longitude - coordinateGrid.get("SE").longitude;
        double gridWidth = -coordinateGrid.get("NE").latitude - coordinateGrid.get("NW").latitude;

        double lat = coordinateGrid.get("NW").latitude - ((mouseX / windowWidth) * gridWidth);
        double lon = coordinateGrid.get("NE").longitude + ((mouseY / windowHeight) * gridHeight);
        return new Coordinate(lat, lon);
    }
}
