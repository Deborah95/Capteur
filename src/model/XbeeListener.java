package model;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.*;

public class XbeeListener {
	public Signal signal;
	XBee xbee;
	PacketListener packetListener;

	public XbeeListener(Signal signal, XBee xbee) throws XBeeException {
		this.signal = signal;
		this.xbee = xbee;
		this.xbee.open("COM7", 9600); // Initialisation de l'�coute du XBee selon le port et le baud rate choisi
	}

	// M�thode pour enregistrer les valeurs de pression obtenues � partir du XBee
	// lorsqu'on lance l'acquisition
	public void start_acquisition_xbee() throws XBeeException {
		this.packetListener = new PacketListener() {
			public void processResponse(XBeeResponse response) {
				System.out.println("Re�u");
				if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample) response;

					for (IoSample sample : ioSample.getSamples()) {

						int valeur = sample.getAnalog0(); // R�cup�ration de la valeur obtenue au niveau du pin DIO0 de
															// XBee reli� � la sortie du capteur (valeur entre 0 et
															// 1023)
						double tension = valeur * 3.3 / 1023; // Conversion de la valeur obtenue sous forme d'une
																// tension
						double pression = 64.64 * tension + Signal._DEFAULT_PRESSURE; // Conversion de la tension en
																						// pression selon les donn�es de
																						// la datasheet du capteur

						signal.add_echantillon_end(pression); // Sauvegarde des donn�es de pression

						System.out.println(pression);

					}
				}

			}
		};
		xbee.addPacketListener(packetListener);
	}

	// M�thode pour arr�ter l'acquisition des donn�es
	public void stop_acquisition_xbee() {
		xbee.removePacketListener(packetListener);
	}

}
