import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private static final int FLEET_BOAT_COUNT = 1;

    private Controller controller;
    private Fleet fleet;
    private ArrayList<Obstacle> obstacles;
    private Grid mapOfLake;

    public void broadcastCoordinate(Coordinate c) {
        // TODO: figure out broadcasting
    }

    Model(Controller c) {
        controller = c;
    }

    public void initialize() throws IOException {
        BufferedImage background;
        try {
            background = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            throw e;
        }
    }

    void update() {

    }
}
