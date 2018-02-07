import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class View extends JFrame implements ActionListener {
    Controller controller;
    Model model;
    Panel viewPanel;

    public View(Controller c, Model m) {
        this.controller = c;
        this.model = m;

        this.viewPanel = new Panel();
        this.viewPanel.addMouseListener(controller);
        this.getContentPane().add(this.viewPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    private class Panel extends JPanel {
        private void drawSprites(Graphics g) {

        }
    }
}
