package model;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.*;

public class XbeeListener {
	Signal signal;
	
	public XbeeListener(Signal signal) {
		this.signal = signal;
	}
	
	public void acquisition_Xbee() throws XBeeException {
		XBee xbee = new XBee();
		xbee.open("COM7",9600);
		
		PacketListener packetListener = new PacketListener() {
		    public void processResponse(XBeeResponse response) {
		        System.out.println("Reçu");
		        if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample)response;
					
					for (IoSample sample: ioSample.getSamples()) {
					
						int valeur = sample.getAnalog0();
						double tension = valeur*3.3/1023;
						double pression = 64.64*tension-32.32;
						
						signal.add_echantillon_end(pression);
						
						//System.out.println("Pression : " + pression + "mmHg");
						//System.out.println(signal.get_size());
						//System.out.println("Signal : " + signal.get_echantillon(signal.echantillons.size()));
					}
				}
		        
		        
		    }
		};
		xbee.addPacketListener(packetListener);		
	}
}
