package view;

import java.io.IOException;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Signal;
import model.SignalTools;
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
		
		Signal dpression = new Signal();
		SignalTools signalT = new SignalTools(data.signal);
		dpression = signalT.delta();
		SignalTools dpressionT = new SignalTools(dpression);
		double pression_moy = dpressionT.maximum();
		double pression_syst = 0.55*pression_moy;
		double pression_diast = 0.85*pression_moy;
		
		Text text1 = new Text("Pression moyenne : " + pression_moy + "mmHg");
		text1.setTextAlignment(TextAlignment.JUSTIFY);
		text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text1.setFill(Color.BLACK);
		text1.setX(50);
		text1.setY(50);
		
		Text text2 = new Text("Pression systolique : " + pression_syst + "mmHg");
		text2.setTextAlignment(TextAlignment.JUSTIFY);
		text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text2.setFill(Color.BLACK);
		text2.setX(50);
		text2.setY(100);
		
		Text text3 = new Text("Pression diastolique : " + pression_diast + "mmHg");
		text3.setTextAlignment(TextAlignment.JUSTIFY);
		text3.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text3.setFill(Color.BLACK);
		text3.setX(50);
		text3.setY(150);
		
		this.group.getChildren().addAll(text1, text2, text3);

		return group;
	}
	
}
