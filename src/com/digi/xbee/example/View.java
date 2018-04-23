
package com.digi.xbee.example;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.digi.xbee.example.Button.buttonNames;

import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View extends JFrame implements ActionListener {
    Controller controller;
    Model model;
    Panel viewPanel;

    BufferedImage background;
    AffineTransform at;
    
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
    
    public static final int height = 1070;
    public static final int width = 1039; 
    
    private String selectionBoatText = "Boat 1";
    private String selectionCoordinateText = "36.137N, -94.140E";
    
    Font font;
    Rectangle rect;
    
    public View(Controller c, Model m) throws IOException {
        this.controller = c;
        this.model = m;
        
        //BufferedImage unscaledBackground = m.getGrid().getTerrainImage();
        //this.background = scale(unscaledBackground, unscaledBackground.getWidth(), unscaledBackground.getHeight(), scalingFactor, scalingFactor);
        this.background = m.getGrid().getTerrainImage();
        
        this.buttons = new ArrayList<Button>();
        
        font = new Font("Tw Cen MT Condensed", Font.PLAIN, 28);
        rect = new Rectangle(18, 625, 200, 30);
        
        this.viewPanel = new Panel();
        this.viewPanel.addMouseListener(controller);
        this.getContentPane().add(this.viewPanel);

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        try {
        	buttons.add(new Button(buttonNames.Select_Fleet, SELECT_FLEET_PNG, SELECT_FLEET_ON_CLICK_PNG, DESELECT_FLEET_PNG, DESELECT_FLEET_ON_CLICK_PNG, 16, 687));
        	buttons.add(new Button(buttonNames.Move, MOVE_PNG, MOVE_ON_CLICK_PNG, 16, 846));
        	buttons.add(new Button(buttonNames.Stop, STOP_PNG, STOP_ON_CLICK_PNG, GO_PNG, GO_ON_CLICK_PNG, 16, 925));
        	buttons.add(new Button(buttonNames.Send_Home, SEND_HOME_PNG, SEND_HOME_ON_CLICK_PNG, 16, 975));
        } catch (Error E) {
        	throw E;
        }
        
    }
    
    public static BufferedImage scale(BufferedImage sbi, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, 3);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }
    
    public void setSelectionText(int boatId, Coordinate boatCoords) {
    	if (boatId == -1) {
    		selectionBoatText = selectionCoordinateText = "";
    		return;
    	}
    	selectionBoatText = boatId != 0 ? "Boat " + Integer.toString(boatId) : "Fleet";
    	selectionCoordinateText = boatCoords != null ? String.format("%.3fN, %.3fE", boatCoords.getLatitude(), boatCoords.getLongitude()) : "";
    }

    private class Panel extends JPanel {
        JTextField jLon = new JTextField(10);
        JTextField jLat = new JTextField(10);
        
        Panel () {
        	setLayout(new FlowLayout(FlowLayout.LEFT));
        	
        	jLon.setBounds(137, 1169, 195, 51);
            jLat.setBounds(139, 1236, 195, 51);      
            
        	//add(jLon);
        	//add(jLat);
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        };

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
			
		}

		private void drawSprites(Graphics g) {
            Fleet fleet = model.getFleet();
            for (Boat b : fleet.getBoats()) {
                g.drawImage(b.getBoatImage(), b.getXpos(), b.getYpos(), null);
            }
        }
        
        private void drawButtons(Graphics g) {
        	for (Button b: buttons) {
        		g.drawImage(b.getImage(), b.getUpperLeftPoint().x, b.getUpperLeftPoint().y, null);
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
            g.drawString(selectionCoordinateText, x, y + font.getSize());
        }
    }
}
