import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model {
    private static final int FLEET_BOAT_COUNT = 1;

    private Controller controller;
    private Fleet fleet;
    private ArrayList<Obstacle> obstacles;
    private Grid mapOfLake;

    public void broadcastCoordinates(Coordinate c) {
        // TODO: figure out broadcasting
    }

    public Grid getGrid() {
        return mapOfLake;
    }

    public Fleet getFleet() {
        return fleet;
    }

    Model(Controller c) {
        controller = c;
    }

    public void initialize()  {

    }

    void update() {

    }
}
