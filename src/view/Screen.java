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

		Button button_start = new Button("Démarrer Acquisition");
		Button button_stop = new Button("Arrêter Acquisition");
		Button clear = new Button("Nouvelle acquisition");
		
		button_start.setMinWidth(150);
		button_start.setMinHeight(50);
		button_start.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		button_stop.setMinWidth(150);
		button_stop.setMinHeight(50);
		button_stop.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));

		clear.setMinWidth(150);
		clear.setMinHeight(50);
		clear.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));

		Text intro = new Text("Gonflez le brassard et démarrez l'acquisition");
		intro.setTextAlignment(TextAlignment.JUSTIFY);
		intro.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 40));
		intro.setFill(Color.BLUEVIOLET);
		intro.setX(150);
		intro.setY(-150);

		Text fin_acq = new Text("Fin de l'acquisition");
		fin_acq.setTextAlignment(TextAlignment.JUSTIFY);
		fin_acq.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 40));
		fin_acq.setFill(Color.BLUEVIOLET);
		fin_acq.setX(400);
		fin_acq.setY(-150);

		group.getChildren().add(intro);

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(100, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(button_start, 0, 0);
		gridPane.add(button_stop, 0, 10);
		gridPane.add(clear, 0, 30);

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

		Text start_acq = new Text("Acquisition en cours... Arrêtez l'acquisition quand le brassard est dégonflé.");
		button_start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Start !");
				try {
					data.start_acquisition_xbee();
					group.getChildren().remove(intro);

					start_acq.setTextAlignment(TextAlignment.JUSTIFY);
					start_acq.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 40));
					start_acq.setFill(Color.BLUEVIOLET);
					start_acq.setX(-50);
					start_acq.setY(-150);

					group.getChildren().add(start_acq);
				} catch (XBeeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// DecimalFormat df = new DecimalFormat("##.##");
		Text text4 = new Text();
		Text text5 = new Text();
		Text text6 = new Text();
		button_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Stop !");
				data.stop_acquisition_xbee();

				Signal dpression = new Signal();
				SignalTools signalT = new SignalTools(data.signal);
				dpression = signalT.delta();
				SignalTools dpressionT = new SignalTools(dpression);
				int pos_max = dpressionT.pos_maximum();
				double max_dpression = dpressionT.maximum();
				double pression_moy = data.signal.get_echantillon(pos_max);
				double tab[][] = dpressionT.find_peaks();
				int i = 0;
				while (tab[1][i] != pos_max) {
					i++;
				}
				int j = i;
				while (tab[0][i] > 0.85 * max_dpression) {
					i = i + 1;
				}
				double pression_diast = data.signal.get_echantillon((int) tab[1][i]);
				System.out.println("i :" + tab[1][i]);
				while (tab[0][j] > 0.55 * max_dpression) {
					j = j - 1;
				}
				double pression_syst = data.signal.get_echantillon((int) tab[1][j]);
				System.out.println("j :" + tab[1][j]);

				text4.setText("" + Math.round(pression_moy));
				text4.setTextAlignment(TextAlignment.JUSTIFY);
				text4.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text4.setFill(Color.BLACK);
				text4.setX(870);
				text4.setY(55);

				text5.setText("" + Math.round(pression_syst));
				text5.setTextAlignment(TextAlignment.JUSTIFY);
				text5.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text5.setFill(Color.BLACK);
				text5.setX(880);
				text5.setY(105);

				text6.setText("" + Math.round(pression_diast));
				text6.setTextAlignment(TextAlignment.JUSTIFY);
				text6.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text6.setFill(Color.BLACK);
				text6.setX(890);
				text6.setY(155);

				group.getChildren().addAll(text4, text5, text6);

				group.getChildren().remove(start_acq);

				group.getChildren().add(fin_acq);
			}
		});

		clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Clear");
				group.getChildren().removeAll(text4, text5, text6, fin_acq);
				data.signal.echantillons.clear();
				group.getChildren().add(intro);
			}
		});

		return group;
	}

}
