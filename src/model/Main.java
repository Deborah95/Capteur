package model;

import java.util.*;

import com.rapplogic.xbee.api.*;

import java.io.*;

public class Main {
	public static void main(String [] args) throws XBeeException {
		Signal signal = new Signal();
		XbeeListener test = new XbeeListener(signal);
		test.acquisition_Xbee();
	}
}
