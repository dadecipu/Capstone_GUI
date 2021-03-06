import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Obstacle extends Sprite {
    public static enum obstacle_types {
        ICEBERG,
        ROCK
    }

    Obstacle(obstacle_types o, int x, int y, Coordinate c) throws IOException {
        super(x, y, c);
        if (o == obstacle_types.ICEBERG) {
            this.image = ImageIO.read(new File("iceberg_obstacle.png"));
        }
    }
}
