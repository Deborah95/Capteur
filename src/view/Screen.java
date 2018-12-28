package view;

import java.io.IOException;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.Signal;
import model.XbeeListener;


public class Screen{
	Group group;
	
	public Screen(Group group) {
		this.group = group;
	}
	
	public Node createMainContent() throws XBeeException, IOException {

		Button button_start = new Button("Start Acquisition");
		Button button_stop = new Button("Stop Acquisition");

		this.group.getChildren().addAll(button_start, button_stop);
		
		XBee xbee = new XBee();
		Signal signal = new Signal();
		XbeeListener data = new XbeeListener(signal, xbee);

		button_start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Start !");
				try {
					data.start_acquisition_xbee();
				} catch (XBeeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		button_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Stop !");
				data.stop_acquisition_xbee();
			}
		});

		return group;
	}
	
}
