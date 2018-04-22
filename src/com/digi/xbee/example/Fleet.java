package com.digi.xbee.example;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;

import com.digi.xbee.api.models.XBee64BitAddress;

public class Fleet {
    ArrayList<Boat> Boats;

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
    
    public void addBoat(int id, int x, int y, Coordinate coord, String remoteAddress) throws IOException {
		Boat b = new Boat(id, x, y, coord);
		b.setAddress(remoteAddress);
		this.Boats.add(b);
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
