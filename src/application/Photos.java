package application;



import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * This is the main class where we execute the functionality of the program
 * @author Suryateja Gandhesiri
 * @author Afaq Qamar
 * 
 * */

public class Photos extends Application {
	
	
	/**
	 * mainstage for the start
	 * */
	public Stage mainStage;
	
   /**
    * Main method to start the program 
    * @param primaryStage the primary stage for the program 
    * */
    
    @Override
    public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();

			
			Scene scene = new Scene(root);
			mainStage.setResizable(false);
			mainStage.setTitle("Photo Library");
			mainStage.setScene(scene);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * Main method to start the stage 
     * @param args The primary stage
     * */
    public static void main(String[] args) {
        launch(args);
    }
}