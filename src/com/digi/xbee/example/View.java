
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
import java.util.regex.Pattern;

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
    
    public static final int height = 967;
    public static final int width = 930; 
    
    private String selectionBoatText = "Boat 1";
    private String selectionCoordinateText = "36.137N, -94.140E";
    
    Font font;
    Rectangle rect;

    JTextField jLat;
    JTextField jLon;
    
    Rectangle jLatRect;
    Rectangle jLonRect;
    
    public View(Controller c, Model m) throws IOException {
        this.controller = c;
        this.model = m;
        
        this.background = m.getGrid().getTerrainImage();
        
        this.buttons = new ArrayList<Button>();
        
        font = new Font("Tw Cen MT Condensed", Font.PLAIN, 26);
        rect = new Rectangle(15, 563, 180, 27);
        
        jLat = new JTextField(10);
        jLatRect = new Rectangle(118, 686, 60, 28);
        jLat.setBounds(jLatRect); 
        jLat.setBorder(null);
        jLat.setFont(font);
        
        jLon = new JTextField(10);
        jLonRect = new Rectangle(118, 725, 60, 28);
    	jLon.setBounds(jLonRect); 
        jLon.setBorder(null);
        jLon.setFont(font);
             
        this.viewPanel = new Panel();
        this.viewPanel.setLayout(null);
        this.viewPanel.addMouseListener(controller);
        this.getContentPane().add(this.viewPanel);           
        
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        this.viewPanel.add(jLat);
        this.viewPanel.add(jLon);
        
        try {
        	buttons.add(new Button(buttonNames.Select_Fleet, SELECT_FLEET_PNG, SELECT_FLEET_ON_CLICK_PNG, DESELECT_FLEET_PNG, DESELECT_FLEET_ON_CLICK_PNG, 14, 620));
        	buttons.add(new Button(buttonNames.Move, MOVE_PNG, MOVE_ON_CLICK_PNG, 14, 760));
        	buttons.add(new Button(buttonNames.Stop, STOP_PNG, STOP_ON_CLICK_PNG, GO_PNG, GO_ON_CLICK_PNG, 14, 830));
        	buttons.add(new Button(buttonNames.Send_Home, SEND_HOME_PNG, SEND_HOME_ON_CLICK_PNG, 14, 875));
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
    
    public Coordinate getInputCoords() {
    	String AlphaRegex = ".*[a-z].*|.*[A-Z].*";
    	boolean latHasAlphaChars = jLat.getText().matches(AlphaRegex);
    	boolean lonHasAlphaChars = jLon.getText().matches(AlphaRegex);  	
    	if (latHasAlphaChars || lonHasAlphaChars) {
    		return null;
    	}
    	
    	double lat = Double.parseDouble(" 36." + jLat.getText());
    	double lon = Double.parseDouble("-94." + jLon.getText());
    	return new Coordinate(lat, lon);
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
            drawInputStatics(g);
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
        
        private void drawInputStatics(Graphics g) {
	    	g.setColor(new Color(51, 51, 51));
	        
			g.drawString(" 36.", jLatRect.x - 34, jLatRect.y + font.getSize() - 5);
			g.drawString("N", jLatRect.x + 60, jLatRect.y + font.getSize() - 5);
			
			g.drawString("-94.", jLonRect.x - 34, jLonRect.y + font.getSize() - 5);
			g.drawString("E", jLonRect.x + 60, jLonRect.y + font.getSize() - 5);
        }
    }
}
