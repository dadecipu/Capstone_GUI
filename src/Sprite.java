import java.awt.*;
import java.io.IOException;

public class Sprite {
    protected Image image;
    protected Point XYPosition;
    protected Coordinate coordinatePosition;

    Sprite() {}
    Sprite(int x, int y) {
        this.XYPosition.x = x;
        this.XYPosition.y = y;
    }

    // before calling this, check pixel color for water
    public void setPosition(int x, int y) throws Exception {
        XYPosition = new Point(x, y);
        coordinatePosition = (Grid.calculateCoordinate(x, y, View.width, View.height));
    }

    public Coordinate getCoordinatePosition() { return coordinatePosition; }
    public int getXpos() { return XYPosition.x; }
    public int getYpos() { return XYPosition.y; }
}
