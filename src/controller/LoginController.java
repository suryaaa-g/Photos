package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 * This is the main class where we execute the functionality of the Login Scene,
 * it implements the LogOut interface to enable the log out button
 * 
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 */

public class LoginController implements LogOutController {

	/**
	 * FXML button for login
	 */
	@FXML
	public Button mLogIn;

	/**
	 * FXML TextField for login username
	 */
	@FXML
	public TextField tfUsername;

	/**
	 * admin string
	 */
	String admin = "admin";

	/**
	 * Login function it controls what happens when the textfield has input
	 * 
	 * @param e Allows event to take place
	 * @throws IOException if login fails
	 */
	public void login(ActionEvent e) throws IOException {

		String username = tfUsername.getText().trim();

		File data = new File("data/data.dat");

		if (!data.exists()) {
			try {
				data.createNewFile();
				User stock = new User("stock");
				Album stockAlbum = new Album("stockAlbum");
				// for (int i = 0; i <5; i++) {
				File photoFile = new File("data/stockphotos/stockImage1.jpeg");
				photoSave(photoFile, stockAlbum);

				File photoFile2 = new File("data/stockphotos/stockImage2.jpeg");
				photoSave(photoFile2, stockAlbum);

				File photoFile3 = new File("data/stockphotos/stockImage3.jpeg");
				photoSave(photoFile3, stockAlbum);

				File photoFile4 = new File("data/stockphotos/stockImage4.jpeg");
				photoSave(photoFile4, stockAlbum);

				File photoFile5 = new File("data/stockphotos/stockImage5.jpeg");
				photoSave(photoFile5, stockAlbum);

				stock.getAlbums().add(stockAlbum);
				List<User> users = new ArrayList<>();
				users.add(stock);

				save(users);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * 
		 * This is what happens if there is a file to read: Admin creates users so this
		 * will load the data for the admin scene
		 * 
		 * I think we need another try catch to create the stock file, but idk
		 */

		try {
			FileInputStream fileInputStream = new FileInputStream("data/data.dat");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			ArrayList<User> users = (ArrayList<User>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();

			User user = null;

			/*
			 * This sets the current user to we can find out which user info to grab
			 * 
			 **/
			for (User currentUser : users) {
				if (currentUser.getUsername().equals(username)) {
					user = currentUser;
					break;
				}
			}

			/**
			 * 
			 * If the username is the admin it will load the admin page
			 */
			if (username.equals(admin)) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
				Parent sceneManager = (Parent) loader.load();
				AdminController adminController = loader.getController();
				Scene adminScene = new Scene(sceneManager);
				Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				adminController.start(users);
				appStage.setScene(adminScene);
				appStage.show();

			}

			/**
			 * 
			 * if the user != null then it means that we have a user that exists It will go
			 * into the usercontroller and load the correct user (maybe you can think of a
			 * different way?)
			 */
			else if (user != null) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumDisplay.fxml"));
				Parent sceneManager = (Parent) loader.load();
				AlbumDisplayController userController = loader.getController();
				Scene userScene = new Scene(sceneManager);
				Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				userController.start(users, user);
				appStage.setScene(userScene);
				appStage.show();
			}

			/**
			 * Else throw the error and go back to the login page
			 * 
			 */
			else {
				Alert erroralert = new Alert(AlertType.ERROR);
				erroralert.setHeaderText("Error");
				erroralert.setContentText("Invalid username entered!! Please enter a valid username");
				erroralert.showAndWait();

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
				Parent sceneManager = (Parent) loader.load();
				Scene loginScene = new Scene(sceneManager);
				Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				appStage.setScene(loginScene);
				appStage.show();

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * method that saves the stock photo details so they do not disappear
	 * 
	 * @param photoFile  The photo itself
	 * @param stockAlbum the Album where you are saving the photo to
	 * 
	 */
	private static void photoSave(File photoFile, Album stockAlbum) {
		String photoName = photoFile.getName();
		Calendar photoDate = Calendar.getInstance();
		photoDate.setTimeInMillis(photoFile.lastModified());
		Photo newStockPhoto = new Photo(photoName, "", photoDate, photoFile.toURI().toString());
		stockAlbum.getPhotos().add(newStockPhoto);
	}

	/**
	 * method that saves the user details so they do not disappear
	 * 
	 * @param users the current list of users to save
	 * 
	 */

	private static void save(List<User> users) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(users);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
