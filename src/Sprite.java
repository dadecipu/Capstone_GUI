import java.awt.Image;

public class Sprite {
    Image image;
    Coordinate position;

    public void setPosition(Coordinate p) throws Exception {
        // check if land coordinate (check terrain color?)
        // if not, throw exception
        position = p;
    }
}
