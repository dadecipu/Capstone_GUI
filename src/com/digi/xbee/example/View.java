
package com.digi.xbee.example;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View extends JFrame implements ActionListener {
    Controller controller;
    Model model;
    Panel viewPanel;

    Image background;
    
    ArrayList<Button> buttons;
    
    private static final String SELECT_FLEET_PNG = "images/select_fleet.png";
    private static final String SELECT_FLEET_ON_CLICK_PNG = "images/select_fleet_on_click.png";
    private static final String DESELECT_FLEET_PNG = "images/deselect_fleet.png";
    private static final String DESELECT_FLEET_ON_CLICK_PNG = "images/deselect_fleet_on_click.png";
    
    private static final String MOVE_PNG = "images/move.png";
    private static final String MOVE_ON_CLICK_PNG = "images/move_on_click.png";
    
    private static final String STOP_PNG = "images/stop.png";
    private static final String STOP_ON_CLICK_PNG = "images/stop_on_click.png";
    private static final String GO_PNG = "images/go.png";
    private static final String GO_ON_CLICK_PNG = "images/go_on_click.png";
    
    private static final String SEND_HOME_PNG = "images/send_home.png";
    private static final String SEND_HOME_ON_CLICK_PNG = "images/send_home_on_click.png";
   
    
    public static final int width = 1622;
    public static final int height = 1589; 

    private String selectionBoatText = "Boat 1";
    private String selectionCoordinateText = "36.137N, -94.140E";
    
    Font font;
    Rectangle rect;
    
    public View(Controller c, Model m) throws IOException {
        this.controller = c;
        this.model = m;
        this.background = m.getGrid().getTerrainImage();
        this.buttons = new ArrayList<Button>();
        
        font = new Font("Tw Cen MT Condensed", Font.PLAIN, 45);
        rect = new Rectangle(25, 955, 300, 60);
        
        this.viewPanel = new Panel();
        this.viewPanel.addMouseListener(controller);
        this.getContentPane().add(this.viewPanel);

        this.setSize(height, width);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        try {
        	buttons.add(new Button(SELECT_FLEET_PNG, SELECT_FLEET_ON_CLICK_PNG, DESELECT_FLEET_PNG, DESELECT_FLEET_ON_CLICK_PNG, 25, 1060));
        	buttons.add(new Button(MOVE_PNG, MOVE_ON_CLICK_PNG, 25, 1300));
        	buttons.add(new Button(STOP_PNG, STOP_ON_CLICK_PNG, GO_PNG, GO_ON_CLICK_PNG, 25, 1420));
        	buttons.add(new Button(SEND_HOME_PNG, SEND_HOME_ON_CLICK_PNG, 25, 1500));
        } catch (Error E) {
        	throw E;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }
    
    public void setSelectionText(int boatId, Coordinate boatCoords) {
    		selectionBoatText = boatId != 0 ? "Boat " + Integer.toString(boatId) : "Fleet";
    		selectionCoordinateText = boatId != 0 ? String.format("%.3fN, %.3fE", boatCoords.getLatitude(), boatCoords.getLongitude()): "";
    }

    private class Panel extends JPanel {
        Panel () {

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, null);

            drawSprites(g);
            drawAlerts(g);
            drawButtons(g);
            drawCenteredString(g);
        }

        private void drawAlerts(Graphics g) {
			// TODO Auto-generated method stub
			
		}

		private void drawSprites(Graphics g) {
            Fleet fleet = model.getFleet();
            for (Boat b : fleet.Boats) {
                g.drawImage(b.image, b.getXpos(), b.getYpos(), null);
            }
        }
        
        private void drawButtons(Graphics g) {
        	for (Button b: buttons) {
        		g.drawImage(b.getImage(), b.getX(), b.getY(), null);
        	}
        }
        
        public void drawCenteredString(Graphics g) {
            FontMetrics metrics = g.getFontMetrics(font);
            
            g.setColor(new Color(107, 107, 107));
            g.setFont(font); 
            
            int x = rect.x + (rect.width - metrics.stringWidth(selectionBoatText)) / 2;          
            int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

            g.drawString(selectionBoatText, x, y);
            
            x = rect.x + (rect.width - metrics.stringWidth(selectionCoordinateText)) / 2;    
            g.drawString(selectionCoordinateText, x, y + font.getSize() + 5);
        }
    }
}
