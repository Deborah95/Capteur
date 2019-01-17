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

		// Création des boutons
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

		// Création des messages de consignes à l'utilisateur
		Text intro = new Text("Gonflez le brassard et démarrez l'acquisition.");
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

		group.getChildren().add(gridPane);

		Text title = new Text("Capteur de pression artérielle");
		title.setTextAlignment(TextAlignment.JUSTIFY);
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 60));
		title.setFill(Color.MIDNIGHTBLUE);
		title.setX(150);
		title.setY(-250);

		group.getChildren().add(title);

		// Création des textes indiquant la valeur affichée
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

		Text text8 = new Text("Fréquence cardiaque :                battements/min");
		text8.setTextAlignment(TextAlignment.JUSTIFY);
		text8.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
		text8.setFill(Color.BLACK);
		text8.setX(600);
		text8.setY(200);

		group.getChildren().addAll(text1, text2, text3, text8);

		// Rectangles blancs dans lesquels seront inscrites les valeurs de pression et
		// de fréquence cardiaque
		Rectangle rectangle1 = new Rectangle(860, 20, 100, 40);
		Rectangle rectangle2 = new Rectangle(870, 70, 100, 40);
		Rectangle rectangle3 = new Rectangle(880, 120, 100, 40);
		Rectangle rectangle4 = new Rectangle(900, 170, 100, 40);
		rectangle1.setFill(Color.WHITE);
		rectangle2.setFill(Color.WHITE);
		rectangle3.setFill(Color.WHITE);
		rectangle4.setFill(Color.WHITE);

		group.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4);

		// Initialisation de l'écoute du XBee
		XBee xbee = new XBee();
		Signal signal = new Signal();
		XbeeListener data = new XbeeListener(signal, xbee);

		// Démarrage de l'acquisition lorsqu'on appuie sur le bouton "Démarrer
		// acquisition"
		Text start_acq = new Text("Acquisition en cours... Arrêtez l'acquisition lorsque le brassard est dégonflé.");
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
		Text text7 = new Text();

		// Fin de l'acquisition lorsqu'on appuie sur le bouton "Arrêter acquisition" :
		// traitement des données (démodulation puis calcul des valeurs de pression et
		// de fréquence cardiaque)
		button_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Stop !");
				data.stop_acquisition_xbee();

				Signal dpression = new Signal();
				SignalTools signalT = new SignalTools(data.signal);
				dpression = signalT.delta(); // Démodulation du signal de pression
				SignalTools dpressionT = new SignalTools(dpression);

				// Calcul du maximum de démodulation et de sa position pour déterminer la
				// pression moyenne
				int pos_max = dpressionT.pos_maximum();
				double max_dpression = dpressionT.maximum();
				System.out.println("max :" + max_dpression);
				double pression_moy = data.signal.get_echantillon(pos_max);

				// Détection des pics du signal de démodulation pour calculer les pression
				// systolique et diastolique
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
				while (tab[0][j] > 0.55 * max_dpression & j >= 0) {
					j = j - 1;
				}
				double pression_syst = data.signal.get_echantillon((int) tab[1][j]);

				// Détection des pics du signal de pression pour déterminer la fréquence
				// cardiaque
				double tab2[][] = signalT.find_peaks();
				double max[] = new double[tab2.length];
				int k = 0;
				double somme = 0;
				for (k = 0; k < tab2.length - 1; k++) {
					max[k] = tab2[1][k + 1] - tab2[1][k];
				}
				for (k = 0; k < max.length - 1; k++) {
					somme = somme + max[k];
				}
				double moy = somme / max.length;
				double freq_cardiaque = 60 / (moy * Signal._TE); // Fréquence cardiaque en battements/min

				// Affichage des valeurs obtenues
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

				text7.setText("" + Math.round(freq_cardiaque));
				text7.setTextAlignment(TextAlignment.JUSTIFY);
				text7.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 30));
				text7.setFill(Color.BLACK);
				text7.setX(900);
				text7.setY(205);

				group.getChildren().addAll(text4, text5, text6, text7);

				group.getChildren().remove(start_acq);

				group.getChildren().add(fin_acq);
			}
		});

		// Effacement de toutes les données (affichées ou non) pour pouvoir démarrer une
		// nouvelle acquisition
		clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Clear");
				group.getChildren().removeAll(text4, text5, text6, text7, fin_acq);
				data.signal.echantillons.clear();
				group.getChildren().add(intro);
			}
		});

		return group;
	}

}
