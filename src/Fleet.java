import java.rmi.server.ExportException;
import java.util.ArrayList;

public class Fleet {
    ArrayList<Boat> Boats;

    Fleet(ArrayList<Boat> boats) {
        Boats = boats;
    }

    public void moveFleet(Coordinate c) throws Exception {
        // Is AI going to determine actual coordinates?
        // Not all boats can go to same coordinate
        for ( Boat b : Boats ) {
            try {
                b.setPosition(c);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
