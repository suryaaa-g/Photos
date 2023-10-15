package controller;
import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This interface enables the use of the log out button so users can login without losing save results
 * 
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * */
public interface LogOutController {
	
	/**
	 * Log Out interface for all the logout buttons 
	 * @param e enables actionevent
	 * @throws IOException if user doesnt log out
	 * */
	
	public default void logUserOut(ActionEvent e) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log Out?");
		alert.setContentText("Do you want to log out?");
		
		Optional<ButtonType> res = alert.showAndWait();
		
		if(res.get() == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent sceneManager = (Parent) loader.load();
			Scene adminScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			appStage.setScene(adminScene);
			appStage.show();	      
		} 
		else {
			return;
		}
			
			
		}
		

	}
	

	
	

