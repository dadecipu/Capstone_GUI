import java.util.ArrayList;

public class Fleet {
    ArrayList<Boat> Boats;

    public boolean tryMoveFleet(Coordinate c) {
        // Is AI going to determine actual coordinates?
        // Not all boats can go to same coordinate
        for ( Boat b : Boats ) {
            successful = b.trySetPosition(c);
        }
    }
}
