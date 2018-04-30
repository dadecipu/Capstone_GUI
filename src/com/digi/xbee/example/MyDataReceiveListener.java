package com.digi.xbee.example;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

public class MyDataReceiveListener implements IDataReceiveListener {
	/*
	* Data reception callback.
	*/
	@Override
	public void dataReceived(XBeeMessage xbeeMessage) {	
		//System.out.println("Happening");
		XBee64BitAddress address = xbeeMessage.getDevice().get64BitAddress();
		String dataString = xbeeMessage.getDataString();
		System.out.println("Received data from " + address.toString() +
				": " + dataString);	
		
		try {
			Controller.messageUpdate(address, dataString, address);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}