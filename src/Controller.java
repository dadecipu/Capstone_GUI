import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.Timer;

public class Controller implements MouseListener{
    private Model model;
    private View view;
    private Boat boatSelected;

    Controller() {
        model = new Model(this);
    }

    static void run() throws IOException {
        Controller c = new Controller();
        c.view = new View(c, c.model);
        new Timer(20, c.view).start();
    }

    void update() {
        model.update();
    }



    // click boat to select
    void setBoatSelected (Boat b) {
        boatSelected = b;
    }

    public void mousePressed(MouseEvent e) {     }
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
}
