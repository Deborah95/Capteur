package view;

import java.io.IOException;
//import java.text.DecimalFormat;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import model.Signal;
import model.SignalTools;
import model.XbeeListener;

public class Screen {
	Group group;

	public Screen(Group group) {
		this.group = group;
	}

	public Node createMainContent() throws XBeeException, IOException {

		Button button_start = new Button("Start Acquisition");
		Button button_stop = new Button("Stop Acquisition");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(100, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(button_start, 0, 0);
		gridPane.add(button_stop, 0, 10);

		this.group.getChildren().add(gridPane);

		Text title = new Text("Capteur de pression artérielle");
		title.setTextAlignment(TextAlignment.JUSTIFY);
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 60));
		title.setFill(Color.MIDNIGHTBLUE);
		title.setX(150);
		title.setY(-250);

		group.getChildren().add(title);

		Text text1 = new Text("Pression moyenne :                mmHg");
		text1.setTextAlignment(TextAlignment.JUSTIFY);
		text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text1.setFill(Color.BLACK);
		text1.setX(600);
		text1.setY(50);

		Text text2 = new Text("Pression systolique :                mmHg");
		text2.setTextAlignment(TextAlignment.JUSTIFY);
		text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text2.setFill(Color.BLACK);
		text2.setX(600);
		text2.setY(100);

		Text text3 = new Text("Pression diastolique :                mmHg");
		text3.setTextAlignment(TextAlignment.JUSTIFY);
		text3.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text3.setFill(Color.BLACK);
		text3.setX(600);
		text3.setY(150);

		group.getChildren().addAll(text1, text2, text3);

		Rectangle rectangle1 = new Rectangle(860, 20, 100, 40);
		Rectangle rectangle2 = new Rectangle(870, 70, 100, 40);
		Rectangle rectangle3 = new Rectangle(880, 120, 100, 40);
		rectangle1.setFill(Color.WHITE);
		rectangle2.setFill(Color.WHITE);
		rectangle3.setFill(Color.WHITE);

		group.getChildren().addAll(rectangle1, rectangle2, rectangle3);

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

		// DecimalFormat df = new DecimalFormat("##.##");

		button_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Stop !");
				data.stop_acquisition_xbee();

				Signal dpression = new Signal();
				SignalTools signalT = new SignalTools(data.signal);
				dpression = signalT.delta();
				SignalTools dpressionT = new SignalTools(dpression);
				int pos_max = dpressionT.pos_maximum();
				System.out.println("pos max :" + pos_max);
				double max_dpression = dpressionT.maximum();
				double pression_moy = data.signal.get_echantillon(pos_max);
				double tab[][] = dpressionT.find_peaks();
				int i = 0;
				while(tab[1][i] != pos_max) {
					i++;
				}
				int j = i;
				while (tab[0][i] > 0.85 * max_dpression) {
					i = i + 1;
				}
				double pression_diast = data.signal.get_echantillon((int)tab[1][i]);
				System.out.println("i :" + tab[1][i]);
				while (tab[0][j] > 0.55 * max_dpression) {
					j = j - 1;
				}
				double pression_syst = data.signal.get_echantillon((int)tab[1][j]);
				System.out.println("j :" + tab[1][j]);

				Text text4 = new Text("" + Math.round(pression_moy));
				text4.setTextAlignment(TextAlignment.JUSTIFY);
				text4.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text4.setFill(Color.BLACK);
				text4.setX(870);
				text4.setY(55);

				Text text5 = new Text("" + Math.round(pression_syst));
				text5.setTextAlignment(TextAlignment.JUSTIFY);
				text5.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text5.setFill(Color.BLACK);
				text5.setX(880);
				text5.setY(105);

				Text text6 = new Text("" + Math.round(pression_diast));
				text6.setTextAlignment(TextAlignment.JUSTIFY);
				text6.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text6.setFill(Color.BLACK);
				text6.setX(890);
				text6.setY(155);

				group.getChildren().addAll(text4, text5, text6);

			}
		});

		return group;
	}

}
