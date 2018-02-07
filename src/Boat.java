import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Boat extends Sprite {
    private static final String BOAT_IMAGE_PNG = "pirateship.png";
    private Direction direction = Direction.NORTH;
    private int speed = 0;
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

    Boat(int x, int y) throws IOException {
        super(x, y);
        this.image = ImageIO.read(new File(BOAT_IMAGE_PNG));
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
}
