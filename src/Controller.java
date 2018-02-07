import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.Timer;

public class Controller implements MouseListener{
    private Model model;
    private View view;
    private Boat boatSelected;

    Controller() throws IOException {
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
        if (boatSelected != null) {
            boatSelected.deselect();
        }
        boatSelected = b;
        b.setSelected();
        System.out.println(b.getCoordinatePosition().latitude +" "+ b.getCoordinatePosition().longitude);
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + " " + y);
        // TODO: iteration 2: if we add a sidebar, check which side of gui clicked
        // TODO: iteration 2: Add a select all button to the sidebar

        Boat tempBoatSelection = null;
        // check for boat in x, y click
        for (Boat b: model.getFleet().Boats) {
            int boatRightX = b.getXpos() + b.getBoatImage().getWidth();
            int boatBottomY = b.getYpos() + b.getBoatImage().getHeight();

            System.out.println("boat left corner = " + b.getXpos() + " " + b.getYpos());
            System.out.println("boat sides = " + boatRightX + " " + boatBottomY);


            if ((x > b.getXpos() && x <= boatRightX) &&
                (y > b.getYpos() && y <= boatBottomY)) {
                setBoatSelected(b);
                break;
            }
        }


        if (boatSelected == null && tempBoatSelection == null) {
            // no boat, no action
        }

        if (boatSelected != null && tempBoatSelection == null) {
            // try to move selected boat to position (if water and no obstacle)
        }

        if (boatSelected != null && tempBoatSelection != null) {
            // deselect old boat selected, select new boat
            setBoatSelected(tempBoatSelection);
        }

    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
}
