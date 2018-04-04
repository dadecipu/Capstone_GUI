import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Boat extends Sprite {
    private static final String BOAT_IMAGE_PNG = "pirateship.png";
    private static final String SELECTED_BOAT_PNG = "pirateship_selected.png";
    private Direction direction = Direction.NORTH;
    private int speed = 0;
    private int width;
    private int height;
    private BufferedImage unselectedBoat;
    private BufferedImage selectedBoat;
    private boolean isSelected;

    private LinkedList<Alert> alerts = new LinkedList<Alert>();

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

    Boat(int x, int y, Coordinate c) throws IOException {
        super(x, y, c);
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

    public void addAlert(Alert a){
      alerts.add(a);
    }

    public LinkedList<Alert> getAlerts(){
      return alerts;
    }

    public void removeAlert(Alert a){
      alerts.remove(a);
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
