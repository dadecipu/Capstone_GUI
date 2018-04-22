package com.digi.xbee.example;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;
import java.util.ArrayList;

public class Broadcaster {
	XBeeDevice localXBee;
	String port;
	int baudRate;
	ArrayList<RemoteXBeeDevice> remoteDevices;
	MyDataReceiveListener localDayaRecieveListener;
	
	
	Broadcaster(String port, int baudRate){
		localXBee = new XBeeDevice(port, baudRate);
		this.port = port;
		this.baudRate = baudRate;
		remoteDevices = new ArrayList<RemoteXBeeDevice>();
	}
	
	void createNewRemoteDevice(String address) {
		XBee64BitAddress remoteAddress = new XBee64BitAddress(address);
		RemoteXBeeDevice newRemoteDevice = new RemoteXBeeDevice(this.localXBee, remoteAddress);
		remoteDevices.add(newRemoteDevice);
	}
	
	void broadcastCoords(Coordinate coord, Boat target) throws XBeeException {
		int targetIndex = getTargetIndex(target.getAddress());
		
		String message;
		long intBitsLongitude, intBitsLatitude;
		float latitude = coord.getLatitudeAsFloat();
		float longitude = coord.getLongitudeAsFloat();
		intBitsLatitude = Float.floatToIntBits(latitude);
		intBitsLongitude = Float.floatToIntBits(longitude);
		
		System.out.println("Broadcasting coordinates: Lat: " +  coord.getLatitude() + " Long: " + coord.getLongitude() + " to remote address: " + target.getAddress().toString());
		
		message = "$g, " + intBitsLatitude + ", " + intBitsLongitude + "\r\n";
		this.localXBee.sendDataAsync(remoteDevices.get(targetIndex), message.getBytes());
	}
	
	void broadcastStateSwap(Boat target) throws XBeeException {
		int targetIndex = getTargetIndex(target.getAddress());
		
		String message = null;
		if(target.getStoppedState()) {
			message = "$r\r\n";
			target.swapState();
		}else {
			message = "$s\r\n";
			target.swapState();
		}
		this.localXBee.sendDataAsync(remoteDevices.get(targetIndex), message.getBytes());
	}

	int getTargetIndex(XBee64BitAddress target) {
		int index = -1;
		int count = 0;
		for(RemoteXBeeDevice r : this.remoteDevices) {
			if(r.get64BitAddress().equals(target)) {
				index = count;
			}
			count++;
		}
		
		return index;
	}
}
