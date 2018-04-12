package com.digi.xbee.example;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button {
	Image buttonImage;
    Image clickedButtonImage;
    
    Image flippedButtonImage = null;
    Image clickedFlippedButtonImage = null;
    
    public int x, y;
    private int width, height;
    
    boolean clicked = false;
    
    Button(String regularFile, String clickedFile, int x, int y) throws IOException {
        try {
        	buttonImage = ImageIO.read(new File(regularFile));
        	clickedButtonImage = ImageIO.read(new File(clickedFile));
        } catch (IOException e) {
            throw e;
        }
    	this.x = x;
    	this.y = y; 
    	
    	width = buttonImage.getWidth(null);
    	height = buttonImage.getHeight(null);
    }
    Button(String regularFile, String clickedFile, String flippedFile, String clickedFlippedFile, int x, int y) throws IOException {
        try {
        	buttonImage = ImageIO.read(new File(regularFile));
        	clickedButtonImage = ImageIO.read(new File(clickedFile));
        	
        	flippedButtonImage = ImageIO.read(new File(flippedFile));
        	clickedFlippedButtonImage = ImageIO.read(new File(clickedFlippedFile));
        } catch (IOException e) {
            throw e;
        }
    	this.x = x;
    	this.y = y; 
    	
    	width = buttonImage.getWidth(null);
    	height = buttonImage.getHeight(null);
    }
    
    public Image getImage() {
    	return clicked ? clickedButtonImage : buttonImage;
    }
    
    public int getWidth() {
    	return width;
    }
    public int getHeight() {
    	return height;
    }
    
    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
}
