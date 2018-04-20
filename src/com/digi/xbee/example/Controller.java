package com.digi.xbee.example;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.Timer;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;

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
    }

    static void run() throws IOException {
        Controller c = new Controller();
        c.view = new View(c, c.model);
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
    void setBoatSelected (Boat b) {
        if (boatSelected != null) {
            boatSelected.deselect();
        }
        
        boatSelected = b;
        b.setSelected();
        

        System.out.println("Selected Boat Coordinates: " + b.getCoordinatePosition().getLongitude() + " " + b.getCoordinatePosition().getLatitude());
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + ", " + y);

        Coordinate mouseCoordinate = model.getGrid().calculateCoordinate(x, y, view.getWidth(), view.getHeight());
        System.out.println("Mouse Click Coordinate: " + mouseCoordinate.getLongitude() + " " + mouseCoordinate.getLatitude());
        // TODO: iteration 2: if we add a sidebar, check which side of gui clicked
        // TODO: iteration 2: Add a select all button to the sidebar

        Boat tempBoatSelection = null;

        //Used to check if this is first time boat is selected
        //To avoid the stutter when a boat is selected
        boolean newSelection = false;

        // check for boat in x, y click
        for (Boat b: model.getFleet().Boats) {
            int boatRightX = b.getXpos() + b.getBoatImage().getWidth();
            int boatBottomY = b.getYpos() + b.getBoatImage().getHeight();

            if ((x > b.getXpos() && x <= boatRightX) &&
                (y > b.getYpos() && y <= boatBottomY)) {
            	view.setSelectionText(b.getId(), b.getCoordinatePosition());
                setBoatSelected(b);
                newSelection = true;
                break;
            }
        }

        if (boatSelected != null && tempBoatSelection != null) {
            // deselect old boat selected, select new boat
            setBoatSelected(tempBoatSelection);
        } else if (boatSelected != null && tempBoatSelection == null && !newSelection) {
            // try to move selected boat to position (if water and no obstacle)
            try {
              if (model.getGrid().isPixelWater(x, y)) {
                view.setSelectionText(boatSelected.getId(), mouseCoordinate);     
                
                broadcaster.broadcastCoords(mouseCoordinate, boatSelected);
                //boatSelected.setPosition(x, y, mouseCoordinate);		//Uncomment to move the boat when something is clicked
                
                //Output new locaiton of boat (Testing only)
                System.out.println("New Boat Coordinate: " + mouseCoordinate.getLongitude() + " " + mouseCoordinate.getLatitude());
              } else {
                //Report invalid coordinates if pixel isn't water
                System.out.println("Invalid coordinates for the boat's destination.");
              }
            } catch(Exception err) {
              System.out.println("The following error has been caught when changing boats coordinates: " + err);
            }
        }

        if (boatSelected == null && tempBoatSelection == null) {
            // no boat, no action
        }
    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }
}
