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
		this.xbee.open("COM7", 9600); // Initialisation de l'écoute du XBee selon le port et le baud rate choisi
	}

	// Méthode pour enregistrer les valeurs de pression obtenues à partir du XBee
	// lorsqu'on lance l'acquisition
	public void start_acquisition_xbee() throws XBeeException {
		this.packetListener = new PacketListener() {
			public void processResponse(XBeeResponse response) {
				System.out.println("Reçu");
				if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample) response;

					for (IoSample sample : ioSample.getSamples()) {

						int valeur = sample.getAnalog0(); // Récupération de la valeur obtenue au niveau du pin DIO0 de
															// XBee relié à la sortie du capteur (valeur entre 0 et
															// 1023)
						double tension = valeur * 3.3 / 1023; // Conversion de la valeur obtenue sous forme d'une
																// tension
						double pression = 64.64 * tension + Signal._DEFAULT_PRESSURE; // Conversion de la tension en
																						// pression selon les données de
																						// la datasheet du capteur

						signal.add_echantillon_end(pression); // Sauvegarde des données de pression

						System.out.println(pression);

					}
				}

			}
		};
		xbee.addPacketListener(packetListener);
	}

	// Méthode pour arrêter l'acquisition des données
	public void stop_acquisition_xbee() {
		xbee.removePacketListener(packetListener);
	}

}
