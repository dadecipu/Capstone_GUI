
public const class Grid {
    private Map<Coordinate> coordinateGrid;

    Grid() {
        coordinateGrid['NW'] = new Coordinate(36.137598, -91.140645);
        coordinateGrid['NE'] = new Coordinate(36.137598, -94.137937);
        coordinateGrid['SW'] = new Coordinate(36.135402, -94.137937);
        coordinateGrid['SE'] = new Coordinate(36.135402, -91.140645);
    }

    public Coordinate calculateCoordinate(int mouseX, int mouseY, int windowWidth, int windowHeight) {
        double lon, lat;
        double gridHeight = coordinateGrid['NE'].longitude - coordinateGrid['SE'].longitude;
        double gridWidth = -(coordinateGrid['NE'].latitude - coordinateGrid['NW'].latitude);

        lon = coordinateGrid['NE'].longitude + ((mouseY / windowHeight) * gridHeight);
        lat = coordinateGrid['NE'].latitude - ((mouseX / windowWidth) * gridWidth);
        return new Coordinate(lon, lat);
    }
}
