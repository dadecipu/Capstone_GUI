package com.digi.xbee.example;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button {
    public static enum buttonNames {
    	Select_Fleet,
    	Move,
    	Stop,
    	Send_Home
    }
    
	private buttonNames buttonName;
	private BufferedImage buttonImage;
	private BufferedImage clickedButtonImage;
    
	private BufferedImage flippedButtonImage = null;
	private BufferedImage clickedFlippedButtonImage = null;

    private int width, height;
    private Point upperLeft, lowerRight;
    
    private boolean clicked = false;
    private boolean flipped = false;
    
    Button(buttonNames name, String regularFile, String clickedFile, int x, int y) throws IOException {	
    	buttonName = name;
        try {
        	buttonImage = ImageIO.read(new File(regularFile));
        	clickedButtonImage = ImageIO.read(new File(clickedFile));
        } catch (IOException e) {
            throw e;
        }       
        setDimensions(buttonImage.getWidth(null), buttonImage.getHeight(null));
    	setPosition(x, y);
    }
    Button(buttonNames name, String regularFile, String clickedFile, String flippedFile, String clickedFlippedFile, int x, int y) throws IOException {
    	buttonName = name;    	 
        try {
        	buttonImage = ImageIO.read(new File(regularFile));
        	clickedButtonImage = ImageIO.read(new File(clickedFile));
        	
        	flippedButtonImage = ImageIO.read(new File(flippedFile));
        	clickedFlippedButtonImage = ImageIO.read(new File(clickedFlippedFile));
        } catch (IOException e) {
            throw e;
        }    
        setDimensions(buttonImage.getWidth(null), buttonImage.getHeight(null));
        setPosition(x, y);
    }
    
    private void setDimensions(int w, int h) {
    	width = w;
    	height = h;
    }
    
    private void setPosition(int x, int y) {
    	upperLeft = new Point(x, y);
    	lowerRight = new Point(x + width, y + height);
    }
    
    public void click() {
    	clicked = !clicked;
    }
    
    public void flip() {
    	flipped = !flipped;
    }
    
    public BufferedImage getImage() {
    	if (flipped && flippedButtonImage != null) return clicked ? clickedFlippedButtonImage : flippedButtonImage;
    	else return clicked ? clickedButtonImage : buttonImage;
    }
    
    public boolean isFlipped() {
    	return flipped;
    }
    
    public buttonNames getName() {
    	return buttonName;
    }
    
    public int getWidth() {
    	return width;
    }
    public int getHeight() {
    	return height;
    }
    
    public Point getUpperLeftPoint() {
    	return upperLeft;
    }
    public Point getLowerRightPoint() {
    	return lowerRight;
    }
}
