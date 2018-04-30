package com.digi.xbee.example;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

import java.util.ArrayList;
import java.util.List;

public class Broadcaster {
	XBeeDevice localXBee;
	String port;
	int baudRate;
	ArrayList<RemoteXBeeDevice> remoteDevices;
	MyDataReceiveListener localDataReceiveListener;
	
	
	Broadcaster(String port, int baudRate) throws XBeeException{
		localXBee = new XBeeDevice(port, baudRate);
		localXBee.open();
		localDataReceiveListener = new MyDataReceiveListener();
		localXBee.addDataListener(localDataReceiveListener);
		this.port = port;
		this.baudRate = baudRate;
		remoteDevices = new ArrayList<RemoteXBeeDevice>();
		
		//listenForData(10000);
		//System.out.println("Done");
	}
	
	void listenForData(int time) {
		System.out.println("Reading");
		XBeeMessage message = localXBee.readData(time);
		if(!message.equals(null))
			System.out.println("Recieved: " + new String(message.getData()));
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
		
		message = "$G," + intBitsLatitude + "," + intBitsLongitude + "\r\n";
		this.localXBee.sendDataAsync(remoteDevices.get(targetIndex), message.getBytes());
	}
	
	void broadcastStateSwap(Boat target) throws XBeeException {
		int targetIndex = getTargetIndex(target.getAddress());
		
		String message = null;
		if(target.getStoppedState()) {
			message = "$R\r\n";
			target.swapState();
		}else {
			message = "$S\r\n";
			target.swapState();
		}
		this.localXBee.sendDataAsync(remoteDevices.get(targetIndex), message.getBytes());
	}
	
	void handshake(XBee64BitAddress target) throws XBeeException {
		int index = getTargetIndex(target);
		this.localXBee.sendDataAsync(remoteDevices.get(index), "$H\r\n".getBytes());
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
