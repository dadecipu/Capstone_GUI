import java.awt.Image;

public class Sprite {
    Image image;
    Coordinate position;

    public boolean trySetPosition(Coordinate p) {
        // check if land coordinate
        position = p;
    }

    void update(Model m) {

    }
}
