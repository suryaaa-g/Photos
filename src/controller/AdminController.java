package controller;

import model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * This is the main class where we execute the functionality of the Admin Scene
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 * */


public class AdminController implements LogOutController {
	
	/**
	 * FXML button to Add
	 * */
	@FXML 
	Button mAdd;
	
	/**
	 * FXML button to delete
	 * */
	@FXML
	Button mDelete;
	
	/**
	 * FXML button to logout
	 * */
	@FXML
	Button mLogOut;
	
	/**
	 * FXML textfield to create user
	 * */
	@FXML 
	TextField tfCreateUser;
	
	/*
	 * FXML listview that will display the list of users 
	 * */
	@FXML
	ListView<User> listview;
	
	/**
	 * 
	 * List of Users available to Login
	 * 
	 * */
	private ArrayList<User> userlist = new ArrayList<>();

	
	
	
	

	
	
	
	
	/**
	 * Start Method to start the scene
	 * @param users the List of users using the application so the admin can create display the correct scene
	 * 
	 * */
	public void start(ArrayList<User>users) {
		this.userlist = users;
		listview.setItems(FXCollections.observableArrayList(userlist));
		if(!userlist.isEmpty()) {
			listview.getSelectionModel().select(0);
		}
		
		
		
		
	}
	
	
	
	
	/**
	 * This method controls the log out button 
	 * @param e Takes record of the actionevent 
	 * @throws Exception if user cant log off
	 * */
	
	public void logOff(ActionEvent e) throws Exception {
		
		logUserOut(e);
	}
	
	/**
	 * This method adds the User to the listview so the admin can see what users have an account
	 * @param event ActionEvent that keeps track of the action
	 * @throws Exception if user doesnt add
	 * */
	
	public void addUser(ActionEvent event) throws Exception {
		tfCreateUser.setDisable(false);
		
		if(tfCreateUser.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Error Adding User");
    		alert.setContentText("You must enter a username!");
    		alert.showAndWait();
    		
    		return;
		}
		
		else if(tfCreateUser.getText().trim().equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duplicate Admin Error");
			alert.setContentText("Cannot add 'admin' to Users.");
			alert.showAndWait();
			return;
			
		}
		else {
			String newUser = tfCreateUser.getText();
			
			for(User user: this.userlist) {
				if(user.getUsername().equals(newUser)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This username already exists");
					alert.showAndWait();
					return;
				}
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + newUser + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.setTitle("Add new user");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				
				listview.getItems().add(new User(newUser));
				save();
				tfCreateUser.clear();
				tfCreateUser.setDisable(false);
				

		}
		}
		
		
		
		
		
		
		
	}
	
	/**
	 * Deletes a user from the admin listview
	 * @param e The event that keeps track of the delete event
	 * @throws Exception if user does not delete
	 * 
	 * */
	public void deleteUser(ActionEvent e) throws Exception {
		
		if(listview.getSelectionModel().getSelectedItem() != null) {
			User userSelected = listview.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "delete " + userSelected.getUsername() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.setTitle("Delete user");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				listview.getItems().remove(userSelected);
				save();
				
				

		}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Must select a user to delete!!");
			alert.showAndWait();
		}
		
		
	}
	
	/**
	 * Saves the program to the data file
	 * @throws Exception if user does not save
	 * */
	public void save() throws Exception {
		FileOutputStream fos = new FileOutputStream("data/data.dat");
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    
	   oos.writeObject(new ArrayList<>(Arrays.asList(listview.getItems().toArray())));
	   
	   fos.close();
	   oos.close();
	}
	
	
}
