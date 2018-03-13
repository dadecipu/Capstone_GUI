import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class View extends JFrame implements ActionListener {
    public static final int width = 1592;
    public static final int height = 1589;

    Controller controller;
    Model model;
    Panel viewPanel;

    Image background;

    public View(Controller c, Model m) {
        this.controller = c;
        this.model = m;
        this.background = m.getGrid().getTerrainImage();

        this.viewPanel = new Panel();
        this.viewPanel.addMouseListener(controller);
        this.getContentPane().add(this.viewPanel);

        this.setSize(height, width);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    private class Panel extends JPanel {
        Panel () {

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, null);
            drawSprites(g);
        }

        private void drawSprites(Graphics g) {
            Fleet fleet = model.getFleet();
            for (Boat b : fleet.Boats) {
                g.drawImage(b.image, b.getXpos(), b.getYpos(), null);
            }

        }
    }
}
