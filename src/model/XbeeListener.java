package model;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.*;

public class XbeeListener {
	Signal signal;
	XBee xbee;
	PacketListener packetListener;
	
	public XbeeListener(Signal signal, XBee xbee) throws XBeeException {
		this.signal = signal;
		this.xbee = xbee;
		this.xbee.open("COM7",9600);
	}
	
	public void start_acquisition_xbee() throws XBeeException {
		this.packetListener = new PacketListener() {
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
	
	public void stop_acquisition_xbee() {
		xbee.removePacketListener(packetListener);
	}
	
}
