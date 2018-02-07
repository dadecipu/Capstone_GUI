import java.rmi.server.ExportException;
import java.util.ArrayList;

public class Fleet {
    ArrayList<Boat> Boats;

    Fleet(ArrayList<Boat> boats) {
        Boats = boats;
    }

    // TODO: iteration 2: flesh out moving fleet
    public void moveFleet(int mouseX, int mouseY, Coordinate c) throws Exception {
        // Is AI going to determine actual coordinates?
        // Not all boats can go to same coordinate
        for ( Boat b : Boats ) {
            try {
                b.setPosition(mouseX, mouseY, c);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
