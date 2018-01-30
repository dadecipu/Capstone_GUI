import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private Model model;
    private Panel viewPanel;

    public View(Controller c, Model m) {
        this.controller = c;
        this.model = m;
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    private class Panel extends JPanel {
        private void drawSprites(Graphics g) {

        }
    }
}
