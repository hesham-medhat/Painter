package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class.
 * 
 * @author H
 *
 */
public class Main extends Application {

	@Override
	public void start(final Stage primaryStage) {
		try {
			final Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
			final Scene scene = new Scene(root);
			primaryStage.setTitle("Painter");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
}
