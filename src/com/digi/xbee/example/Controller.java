package com.digi.xbee.example;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.Timer;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.example.Button.buttonNames;

import android.graphics.Point;

public class Controller implements MouseListener{
    private static Model model;
    private View view;
    private Boat boatSelected;
    private Broadcaster broadcaster;
    private String port = "/dev/tty.usbserial-DN01J2MD";
    private int baudRate = 9600;

    Controller() throws IOException {
        model = new Model(this);
        broadcaster = new Broadcaster(port, baudRate);
        
        //For now we will hardcode remote devices in here
        String address1 = "0013A20040EB823D";
        model.getFleet().addBoat(1, 100, 100, model.getMapOfLake().calculateCoordinate(100, 100), address1);
        model.getFleet().addBoat(2, 200, 100, model.getMapOfLake().calculateCoordinate(100, 100), address1);
        broadcaster.createNewRemoteDevice(address1);
    }

    static void run() throws IOException {
        Controller c = new Controller();
        c.view = new View(c, model);
        new Timer(20, c.view).start();
    }

    void update() {
        model.update();
    }

    //THIS METHOD NEEDS TO BE TESTED TO BE PROPERLY IMPLEMENT
    static void messageUpdate(XBee64BitAddress address, String dataRecieved) throws Exception {
		//Parse dataRecieved to get latitude and longitude values
	
		double lat, lon;
		lat = 1.2;
		lon = 2.2;
		Coordinate newCoord = new Coordinate(lat, lon);
	
		model.getFleet().messageUpdate(address, newCoord);
    }

    // click boat to select
    private void setBoatSelected (Boat b) {
        if (boatSelected != null) {
            boatSelected.deselect();
        }
        
        boatSelected = b;
        b.select();       

        System.out.println("Selected Boat Coordinates: " + b.getCoordinatePosition().getLongitude() + " " + b.getCoordinatePosition().getLatitude());
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + " " + y);
        if (!model.getGrid().isPixelWater(x, y)) {
            for (Button b: view.buttons) {
            	if ((x > b.getUpperLeftPoint().x && x < b.getLowerRightPoint().x)
            		&& (y > b.getUpperLeftPoint().y && y < b.getLowerRightPoint().y)) {
            		System.out.println("Clicking the " + b.getName() + " button");
            		b.click();
            	}
            }     
            return;
        } 
        
		Coordinate mouseCoordinate = Grid.calculateCoordinate(x, y);
        System.out.println("Mouse Click Coordinate: Lat: " + mouseCoordinate.getLatitude() + " Long: " + mouseCoordinate.getLongitude());

        java.awt.Point calculatedPoint = Grid.calculatePoint(mouseCoordinate);
        System.out.println(calculatedPoint.x + " " + calculatedPoint.y); 
        
        Boat tempBoatSelection = null;
        //Used to check if this is first time boat is selected
        //To avoid the stutter when a boat is selected
        boolean newSelection = false;

        // check for boat in x, y click
        for (Boat b: model.getFleet().getBoats()) {
            int boatRightX = b.getXpos() + b.getBoatImage().getWidth();
            int boatBottomY = b.getYpos() + b.getBoatImage().getHeight();

            if ((x > b.getXpos() && x <= boatRightX) 
            	&& (y > b.getYpos() && y <= boatBottomY)) {
            	view.setSelectionText(b.getId(), b.getCoordinatePosition());
                setBoatSelected(b);
                newSelection = true;
                break;
            }
        }

        if (model.getFleet().isFleetSelected()) {  
        	view.setSelectionText(0, mouseCoordinate);
        	model.getFleet().setFleetPosition(x, y);
        } else {
	        if (boatSelected != null && tempBoatSelection == null && !newSelection) {
	            try {
	                view.setSelectionText(boatSelected.getId(), mouseCoordinate);     
	                
	                //broadcaster.broadcastCoords(mouseCoordinate, boatSelected);
	                boatSelected.setPosition(x, y, mouseCoordinate);		//Uncomment to move the boat when something is clicked
	                
	                //Output new locaiton of boat (Testing only)
	                System.out.println("New Boat Coordinate: Lat: " + mouseCoordinate.getLatitude() + " Long: " + mouseCoordinate.getLongitude());
	            } catch(Exception err) {
	              System.out.println("The following error has been caught when changing boats coordinates: " + err);
	            }
	        }
        }
    }

    public void mouseReleased(MouseEvent e) { 
        int x = e.getX();
        int y = e.getY();
        if (!model.getGrid().isPixelWater(x, y)) {
	        for (Button b: view.buttons) {
	        	if ((x > b.getUpperLeftPoint().x && x < b.getLowerRightPoint().x)
	        		&& (y > b.getUpperLeftPoint().y && y < b.getLowerRightPoint().y)) {
	        		b.click();
	        		clickButton(b);
	        	}
	        }
        }
    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
    
    private void clickButton(Button b) { 	
    	switch (b.getName()) {
    	case Select_Fleet: 		
    		if (!b.isFlipped()) {
            	view.setSelectionText(0, null);
            	this.boatSelected = null;
    			model.getFleet().selectFleet();
    		} else {
            	view.setSelectionText(-1, null);
    			model.getFleet().deselectFleet();
    		}
    		b.flip();
    		break;
    	case Move:
    		Coordinate moveTo = view.getInputCoords();
    		if (moveTo != null) {
	    		System.out.println(moveTo.getLatitude() + " " + moveTo.getLongitude());
	    		if (model.getFleet().isFleetSelected()) {
	    			try {
						model.getFleet().moveFleetFromCoordinates(moveTo);
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		} else if (boatSelected != null) {  			
	    			try {
						boatSelected.setPositionFromCoordinates(moveTo);
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}
    		}
    		break;
    	case Stop: 
    		if (model.getFleet().isFleetSelected()) {
    			model.getFleet().swapAllStates();
    		} else if (boatSelected != null) {  			
    			boatSelected.swapState();
    		}
    		b.flip();
    		break;
    	case Send_Home: 
    		if (model.getFleet().isFleetSelected()) {
            	view.setSelectionText(-1, null);
    			model.getFleet().sendAllHome();
    		} else if (boatSelected != null) {
    			boatSelected.sendHome();
    		}
    		break;  		
    	}
    }
}