package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Screen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Group group = new Group();
			Screen screen = new Screen(group);
			root.setCenter(screen.createMainContent());
			Scene scene = new Scene(root, 1500, 800, Color.LIGHTSKYBLUE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acquisition de donn�es");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
