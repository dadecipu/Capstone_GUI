package com.digi.xbee.example;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Boat extends Sprite {
    private static final String BOAT_IMAGE_PNG = "images/pirateship.png";
    private static final String SELECTED_BOAT_PNG = "images/pirateship_selected.png";
    private Direction direction = Direction.NORTH;
    private int speed = 0;
    private int width;
    private int height;
    private BufferedImage unselectedBoat;
    private BufferedImage selectedBoat;
    private boolean isSelected;

    public static enum Direction {
        NORTH,
        NORTHWEST,
        WEST,
        NORTHEAST,
        EAST,
        SOUTHWEST,
        SOUTH,
        SOUTHEAST;
    }

    Boat(int id, int x, int y, Coordinate c) throws IOException {
        super(id, x, y, c);
        unselectedBoat = ImageIO.read(new File(BOAT_IMAGE_PNG));
        selectedBoat = ImageIO.read(new File(SELECTED_BOAT_PNG));

        isSelected = false;
        this.image = unselectedBoat;
    }

    void setSelected() {
        isSelected = true;
        this.image = selectedBoat;
    }

    void deselect() {
        isSelected = false;
        this.image = unselectedBoat;
    }

    public Direction getDirection() {
        return direction;
    }

    public void updateDirection(Direction d) {
        direction = d;
    }
    
    public int getSpeed() {
        return speed;
    }

    public void updateSpeed(int s) {
        speed = s;
    }

    // TODO: check obstacle and other boats (land?) coordinates against boat coordinates
    public boolean checkForCollision(Obstacle o, boolean boatCollision) {
        return false;
    }

    public BufferedImage getBoatImage() {
        return image;
    }
}
