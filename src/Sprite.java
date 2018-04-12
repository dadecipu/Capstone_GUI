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
    public void setPosition(int x, int y, Coordinate c) throws Exception {
        XYPosition = new Point(x, y);
        coordinatePosition = c;
    }

    public Coordinate getCoordinatePosition() { return coordinatePosition; }
    public int getXpos() { return XYPosition.x; }
    public int getYpos() { return XYPosition.y; }
	public int getId() { return id; }
}
