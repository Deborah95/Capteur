package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.rapplogic.xbee.api.XBeeException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Signal;
import model.XbeeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acquisition de données");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Node createToolbar() {
		return new ToolBar(new Button("New"), new Separator(), new Button("Clear"));
	}

	private Node createMainContent() throws XBeeException, IOException {
		Group g = new Group();

		Button button_start = new Button("Start Acquisition");
		Button button_stop = new Button("Stop");

		g.getChildren().addAll(button_start, button_stop);

		button_start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("mouse click !");
				Signal signal = new Signal();
				XbeeListener acquisition = new XbeeListener(signal);
				try {
					acquisition.acquisition_Xbee();
				} catch (XBeeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		button_stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("mouse click !");
			}
		});

		return g;
	}

}
