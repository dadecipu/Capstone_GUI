package com.digi.xbee.example;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;

import com.digi.xbee.api.models.XBee64BitAddress;

public class Fleet {
    private ArrayList<Boat> Boats;
    private boolean isSelected = false;
    
    Fleet(ArrayList<Boat> boats) {
        Boats = boats;
    }

    // TODO: iteration 2: flesh out moving fleet
    public void moveFleet(int mouseX, int mouseY, Coordinate c) throws Exception {
        // Is AI going to determine actual coordinates?
        // Not all boats can go to same coordinate
        for ( Boat b : Boats ) {
            try {
                b.setPosition(mouseX, mouseY, c);
            } catch (Exception e) {
                throw e;
            }
        }
    }
    
    public void moveFleetFromCoordinates(Coordinate c) throws Exception {
    	for ( Boat b : Boats ) {
            try {
                b.setPositionFromCoordinates(c);
            } catch (Exception e) {
                throw e;
            }
        }
    }
    
    public ArrayList<Boat> getBoats() {
    	return Boats;
    }
    
    public boolean isFleetSelected() {
    	return isSelected;
    }
    
    public void addBoat(int id, int x, int y, Coordinate coord, String remoteAddress) throws IOException {
		Boat b = new Boat(id, x, y, coord);
		b.setAddress(remoteAddress);
		this.Boats.add(b);
    }

    public void selectFleet() {
    	for (Boat b: Boats) {
    		b.select();
    	}
    	isSelected = true;
    }
    
    public void deselectFleet() {
    	for (Boat b: Boats) {
    		b.deselect();
    	}
    	isSelected = false;
    }
    
    public void swapAllStates() {
    	for (Boat b: Boats) {
    		b.swapState();
    	}
    }
    
    public void sendAllHome() {
    	for (Boat b: Boats) {
    		b.sendHome();
    	}
    }
    
    public void setFleetPosition(int x, int y) {
    	for (Boat b: Boats) {
    		try {
				b.setPositionFromXY(x, y);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    public int getFleetSize() {
    		return Boats.size();
    }
    
    public void messageUpdate(XBee64BitAddress address, Coordinate newCoordinate) throws Exception {
		//Iterate through all boats, find correct boat by address, update coordinate with dataRecieved
    		for(Boat b : this.Boats) {
    			if(b.getAddress().equals(address)) {
    				b.updateCoordinate(newCoordinate);
    			}
    		}
    }
}