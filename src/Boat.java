
enum Direction {
    NORTH,
    NORTHWEST,
    WEST,
    NORTHEAST,
    EAST,
    SouthWest,
    South,
    SouthEast;
}

public class Boat extends Sprite {
    private Direction direction = Direction.NORTH;
    private int speed = 0;

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

    // TODO: check obstacle and other boats coordinates against boat coordinates
    public boolean checkForCollision(Obstacle o, boolean boatCollision) {
        return false;
    }
}
