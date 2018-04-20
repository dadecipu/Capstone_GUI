package com.digi.xbee.example;
import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.digi.xbee.api.models.XBee64BitAddress;


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
    private XBee64BitAddress remoteAddress;			//WILL BE USED TO SEND BROADCASTER COORECT REMOTEADDRESS TO BROADCAST TO
    private boolean isStopped;

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

        isStopped = true;
        isSelected = false;
        this.image = unselectedBoat;
    }
    
    void updateCoordinate(Coordinate newCoord) throws Exception {
    		Point coordPoint = Grid.calculatePoint(newCoord);
    		this.setPosition((int)coordPoint.getX(), (int)coordPoint.getY(), newCoord);
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
    
    public XBee64BitAddress getAddress() {
    		return remoteAddress;
    }
    
    public void setAddress(String address) {
    		remoteAddress = new XBee64BitAddress(address);
    }
    
    public void swapState() {
    		this.isStopped = !isStopped;
    }
    
    public boolean getStoppedState() {
    		return this.isStopped;
    }

    // TODO: check obstacle and other boats (land?) coordinates against boat coordinates
    public boolean checkForCollision(Obstacle o, boolean boatCollision) {
        return false;
    }

    public BufferedImage getBoatImage() {
        return image;
    }
}
