public class Alert {
    private Direction direction = Direction.NORTH;//Direction in relation to boat (Can change later)
    private int magnitude = 1; //Value from 1 to 5 indicating how serious the alert is
    private Boat associatedBoat;

    enum Direction {
        NORTH,
        NORTHWEST,
        WEST,
        NORTHEAST,
        EAST,
        SOUTHWEST,
        SOUTH,
        SOUTHEAST;
    }

    //Test
    
    public Direction getDirection() {
        return direction;
    }

    public void updateDirection(Direction d) {
        direction = d;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public void updateMagnitude(int m) {
        magnitude = m;
    }

    public Boat getAssociatedBoat(){
      return associatedBoat;
    }

    public void updateAssociatedBoat(Boat b){
      associatedBoat = b;
    }

}
