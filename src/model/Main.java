package model;

import com.rapplogic.xbee.api.*;

public class Main {
	public static void main(String [] args) throws XBeeException {
		Signal signal = new Signal();
		XBee xbee = new XBee();
		XbeeListener test = new XbeeListener(signal, xbee);
		test.start_acquisition_xbee();
	}
}
