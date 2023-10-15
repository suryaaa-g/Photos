package controller;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

/**
 * This is the main class where we execute the functionality of the Album
 * Display Scene, it implements the LogOut interface to enable the log out
 * button
 * 
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 */

public class AlbumDisplayController implements LogOutController {

	/**
	 * Displays Albums
	 */
	@FXML
	public ListView<Album> listview;

	/**
	 * Button to add album
	 */
	@FXML
	public Button mAddAlbum;

	/**
	 * Button to delete album
	 */
	@FXML
	public Button mDeleteAlbum;

	/**
	 * Button to open album
	 */
	@FXML
	public Button mOpenAlbum;

	/**
	 * Button to rename album
	 */
	@FXML
	public Button mRenameAlbum;

	/**
	 * Text to set the number of albums
	 */
	@FXML
	Text tNumber;

	/**
	 * Text to set the date of albums
	 */
	@FXML
	Text tDate;

	/**
	 * Button to search
	 */
	@FXML
	private Button searchButton;

	/**
	 * Button to search by date
	 */
	@FXML
	private RadioButton searchDate;

	/**
	 * Button to toggle search tags
	 */
	@FXML
	private RadioButton searchTags;

	/**
	 * Toggle Button to search
	 */
	@FXML
	private ToggleGroup searchToggle;

	/**
	 * Button to search conjective
	 */
	@FXML
	private RadioButton searchConjective;

	/**
	 * Button to search Disjunctive
	 */
	@FXML
	private RadioButton searchDisjunctive;

	/**
	 * Button to search single
	 */
	@FXML
	private RadioButton searchSingle;

	/**
	 * Button to search tag options
	 */
	@FXML
	private ToggleGroup tagsOptions;

	/**
	 * textfield to get search field 1
	 */
	@FXML
	private TextField searchf1;

	/**
	 * textfield to get search field 2
	 */
	@FXML
	private TextField searchf2;

	/**
	 * text to get search 1
	 */
	@FXML
	private Text searcht1;

	/**
	 * text to get search 2
	 */
	@FXML
	private Text searcht2;

	/**
	 * 
	 * TextField be used to display the album name
	 */

	@FXML
	public TextField albumName;

	/**
	 * 
	 * TextField be used to display the welcome name
	 */
	@FXML
	public TextField welcome;

	/**
	 * This is for adding the album name (rename or adding)
	 */
	@FXML
	public TextField addingAlbumName;

	/**
	 * AlbumList to display the arraylist of albums
	 */
	public ArrayList<Album> albumList = new ArrayList<>();

	/**
	 * the current User
	 */
	User current;

	/**
	 * the current list of users
	 */
	List<User> users;
	/**
	 * Date time format for the date
	 */
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * the observable list for the albums
	 */
	public static ObservableList<Album> obsList;

	/**
	 * Start method for the scene, will take in the current users as a param and the
	 * current user Helps keep track of the scenes per user
	 * 
	 * @param list Keeps track of the previous scenes users
	 * @param user the current user of the application
	 * @throws IOException if start does not work
	 */

	public void start(List<User> list, User user) throws IOException {

		welcome.setText("Welcome " + user.getUsername());
		welcome.setDisable(true);

		searchf2.setPromptText("mm/dd/yyyy");
		searchf1.setPromptText("mm/dd/yyyy");

		this.users = list;
		this.current = user;

		obsList = FXCollections.observableArrayList(current.getAlbums());
		listview.setItems(obsList);
		// lv2.setItems(obsList);

//		if (albumList.size() > 0) {
//			listview.getSelectionModel().select(0);
//			albumName.setText(albumList.get(0).getAlbumName());
//			tNumber.setText("Number of Photos: " + albumList.get(0).getPhotoCount());
//			updatePhotoDetails();
//			// tDate.setText("Date Span (First, Last): \n\t" +
//			// albumList.get(0).getFirstDate() + "\n\t" + albumList.get(0).getLastDate());
//		}

		// this is used to display the current date and # of photos in the album
		listview.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> updatePhotoDetails());

//		listview.getSelectionModel().selectedItemProperty()
//		.addListener((v, oldValue, newValue) -> updateStockPhotoDetails());

	}

//	private void updateStockPhotoDetails() {
//		Album selectedAlbum = listview.getSelectionModel().getSelectedItem();
//		String earliestDate = "";
//		String latestDate = "";
//		
//		if(selectedAlbum.albumName.equals("stockAlbum")) {
//			
//		
//		
//		if (selectedAlbum.getPhotos().size() != 0) {
//
//			Calendar date = selectedAlbum.getPhotos().get(0).getPhotoDate();
//			earliestDate = dateTimeformat.format(date.getTime());
//			latestDate = dateTimeformat.format(date.getTime());
//		}
//
//		for (Photo photo : selectedAlbum.getPhotos()) {
//			Calendar date = photo.getPhotoDate();
//
//			if (dateTimeformat.format(date.getTime()).compareTo(earliestDate) < 0) {
//				earliestDate = dateTimeformat.format(date.getTime());
//			}
//			if (dateTimeformat.format(date.getTime()).compareTo(latestDate) > 0) {
//				latestDate = dateTimeformat.format(date.getTime());
//			}
//
//			if (selectedAlbum != null) {
//				albumName.setText(selectedAlbum.getAlbumName());
//				tNumber.setText("Number of Photos: " + selectedAlbum.getPhotoCount());
//				tDate.setText("Date Span: \t" + earliestDate + " - " + latestDate);
//			}
//		}
//		}
//	}

	/**
	 * Updates the photo details such as the number of photos as well as the latest
	 * and earliest date of photos
	 * 
	 */
	private void updatePhotoDetails() {
		Album selectedAlbum = listview.getSelectionModel().getSelectedItem();
		String earliestDate = "";
		String latestDate = "";

		if (listview.getSelectionModel().getSelectedItem() != null) {
			if (selectedAlbum.getPhotos().size() != 0) {

				Calendar date = selectedAlbum.getPhotos().get(0).getPhotoDate();
				earliestDate = dateTimeformat.format(date.getTime());
				latestDate = dateTimeformat.format(date.getTime());

			}
			for (Photo photo : selectedAlbum.getPhotos()) {
				Calendar date = photo.getPhotoDate();

				if (dateTimeformat.format(date.getTime()).compareTo(earliestDate) < 0) {
					earliestDate = dateTimeformat.format(date.getTime());
				}
				if (dateTimeformat.format(date.getTime()).compareTo(latestDate) > 0) {
					latestDate = dateTimeformat.format(date.getTime());
				}

				if (selectedAlbum != null) {
					albumName.setText(selectedAlbum.getAlbumName());
					tNumber.setText("Number of Photos: " + selectedAlbum.getPhotoCount());
					tDate.setText("Date Span: \t" + earliestDate + " - " + latestDate);
				}

			}
			if (selectedAlbum.getPhotoCount() < 1) {
				tNumber.setText("Number of Photos: 0");
				tDate.setText("Date Span: \t" + earliestDate + " - " + latestDate);
				albumName.setText(listview.getSelectionModel().getSelectedItem().getAlbumName());
			}
		}

	}

	/**
	 * Renames The album
	 * 
	 * @param e enables the action to take place so we can rename the album
	 * @throws Exception if rename album fails
	 */
	public void RenameAlbum(ActionEvent e) throws Exception {

		if (listview.getSelectionModel().getSelectedItem() != null) {
			Album selectedAlbum = listview.getSelectionModel().getSelectedItem();
			String newName = albumName.getText().trim();
			Optional<ButtonType> result = null;
			
			
			
			Album checkAlbum = new Album(newName);
			
//			if(newName.length() == 0) {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setHeaderText("Enter valid album name!!!");
//				alert.showAndWait();
//				return;
//			}
//			else if(newName.equals(selectedAlbum.albumName)) {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setHeaderText("You did not change the album name!!");
//				alert.showAndWait();
//			}
//			else if(current.getAlbums())
			if(newName.length() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Enter valid album name!!!");
				alert.showAndWait();
				return;
			}
			
			if(newName.equals(selectedAlbum.albumName)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("You did not change the album name!!");
				alert.showAndWait();
				return;
			}
			
//			for(Album a : current.getAlbums() ) {
//				//System.out.println(a.albumName);
//				if(albumName.getText().trim().equals( a.albumName.trim()) ) {
//					Alert alert = new Alert(AlertType.ERROR);
//					alert.setHeaderText("Album with that name already exists!!");
//					alert.showAndWait();
//					//System.out.println("hey2");
//					
//				}
//				
//			}
			
			if(current.albumRenameChecker(checkAlbum)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Album with that name already exists!!");
				alert.showAndWait();
				albumName.setText(selectedAlbum.albumName);
			}
			
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm Rename");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to rename this album?");
				result = alert.showAndWait();
				
				
			}
			
			
			if (result.get() == ButtonType.OK) {
				selectedAlbum.albumName = albumName.getText();

				save(users);
			}
			else {
				albumName.setText(selectedAlbum.albumName);
				return;
			}
			

			obsList = FXCollections.observableArrayList(current.getAlbums());
			listview.setItems(obsList);
		}



	}

	/**
	 * 
	 * adds album to listview and the album list
	 * 
	 * @param e ActionEvent that allows the action to take place
	 * @throws Exception if album does not add
	 */
	public void addAlbum(ActionEvent e) throws Exception {

		if (addingAlbumName.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error Adding Album");
			alert.setContentText("You must enter a Album Name!");
			alert.showAndWait();

			return;
		}

		else {
			String albumName = addingAlbumName.getText().trim();

			for (Album album : current.getAlbums()) {
				if (album.getAlbumName().equals(albumName)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This Album Name already exists");
					alert.showAndWait();
					return;
				}
			}

//		current.getAlbums().add(new Album(albumName));
//			listview.getItems().add(new Album(albumName));
//			

			Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + albumName + " ?", ButtonType.YES, ButtonType.NO,
					ButtonType.CANCEL);
			alert.setTitle("Add new user");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				current.getAlbums().add(new Album(albumName));
				listview.getItems().add(new Album(albumName));

				save(users);

				obsList = FXCollections.observableArrayList(current.getAlbums());
				listview.setItems(obsList);

				addingAlbumName.clear();
				addingAlbumName.setDisable(false);
			}

		}
	}

	/**
	 * 
	 * Deletes Album from listview
	 * 
	 * @param e allows action to take place
	 * @throws Exception if album does not delete
	 */
	public void deleteAlbum(ActionEvent e) throws Exception {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			String removedAlbum = listview.getSelectionModel().getSelectedItem().getAlbumName();

			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + removedAlbum + " ?", ButtonType.YES,
					ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int removedAlbumIndex = listview.getSelectionModel().getSelectedIndex();

			if (alert.getResult() == ButtonType.YES) {
				current.getAlbums().remove(removedAlbumIndex);

				save(users);

				obsList = FXCollections.observableArrayList(current.getAlbums());
				listview.setItems(obsList);

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("You did not select an Album to Delete");
			alert.showAndWait();
		}

	}

	/**
	 * 
	 * Opens the Album to display a new scene with all the photos
	 * 
	 * @param e allows the action to take place
	 * @throws Exception if album does not open
	 */
	public void openAlbum(ActionEvent e) throws Exception {
		if (listview.getSelectionModel().getSelectedItem() != null) {
			Album selectAlbum = listview.getSelectionModel().getSelectedItem();
			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));

			Parent sceneManager = (Parent) loader.load();
			ViewPhotosController photoViewController = loader.getController();
			Scene userScene = new Scene(sceneManager);
			Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			photoViewController.start(selectAlbum, current, users, selectAlbum.getPhotos());
			appStage.setScene(userScene);
			appStage.show();

		}
	}

	/***
	 * Below methods are extra ( i thought we needed them)
	 * 
	 */

//	/**
//	 * 
//	 * Sorts albums A - Z
//	 * 
//	 */
//
//	public void sortByAZ() throws Exception {
//		albumList = current.getAlbums();
//		Collections.sort(albumList, Album.sortByAZ);
//
//		obsList = FXCollections.observableArrayList(albumList);
//		listview.setItems(obsList);
//
//		save(users);
//
//	}
//
//	/**
//	 * 
//	 * Sorts albums Z - A
//	 * 
//	 */
//
//	public void sortByZA() throws Exception {
//		albumList = current.getAlbums();
//		Collections.sort(albumList, Album.sortByZA);
//		obsList = FXCollections.observableArrayList(albumList);
//		listview.setItems(obsList);
//
//		save(users);
//
//	}

	/**
	 * This method allows the user to search for photos by Date or Tag
	 * 
	 * @param event Allows the event to take place
	 * @throws IOException    if search button does not search
	 * @throws ParseException if parsing does not work
	 * 
	 */
	@FXML
	void SearchPhotos(ActionEvent event) throws IOException, ParseException {

		ArrayList<Photo> searchResults = new ArrayList<Photo>();

		searchResults.clear();

		ArrayList<Album> albumList = current.getAlbums();
		// Search by date range
		if (searchDate.isSelected()) {

			String startDate = searchf1.getText();

			String endDate = searchf2.getText();

			for (Album album : albumList) {
				ArrayList<Photo> photoList = album.getPhotos();

				for (Photo photo : photoList) {
					Calendar photoDate = photo.getPhotoDate();

					if ((dateTimeformat.parse(dateTimeformat.format(photoDate.getTime()))
							.before(dateTimeformat.parse(endDate))
							&& dateTimeformat.parse(dateTimeformat.format(photoDate.getTime()))
									.after(dateTimeformat.parse(startDate)))
							|| (dateTimeformat.parse(dateTimeformat.format(photoDate.getTime()))
									.equals(dateTimeformat.parse(endDate)))
							|| (dateTimeformat.parse(dateTimeformat.format(photoDate.getTime()))
									.equals(dateTimeformat.parse(startDate)))) {
						if (!isDuplicate(photo, searchResults))
							searchResults.add(photo);
					}

				}
			}
		}

		// Search by tags
		else if (searchTags.isSelected()) {

			String searchField1 = searchf1.getText();
			String searchField2 = searchf2.getText();

			for (Album album : albumList) {

				ArrayList<Photo> photoList = album.getPhotos();

				for (Photo photo : photoList) {

					ArrayList<String> photoTags = photo.printPhotoTags();

					if (searchSingle.isSelected()) {

						if (photoTags.contains(searchField1) && !isDuplicate(photo, searchResults)) {
							searchResults.add(photo);
						}
					}

					if (searchConjective.isSelected()) {

						if (photoTags.contains(searchField1) && photoTags.contains(searchField2)
								&& !isDuplicate(photo, searchResults)) {
							searchResults.add(photo);
						}
					}

					if (searchDisjunctive.isSelected()) {

						if (photoTags.contains(searchField1)
								|| photoTags.contains(searchField2) && !isDuplicate(photo, searchResults)) {
							searchResults.add(photo);
						}
					}

				}
			}

		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchPage.fxml"));

		Parent sceneManager = (Parent) loader.load();

		SearchPageController searchPageController = loader.getController();
		Scene userScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		searchPageController.start(searchResults, current, users, listview);
		appStage.setScene(userScene);
		appStage.show();
	}

	/**
	 * This method checks the textfield for the date of search
	 * 
	 * @param event allows you to create an event
	 */
	@FXML
	void searchDateSelected(ActionEvent event) {

		searchSingle.setVisible(false);
		searchConjective.setVisible(false);
		searchDisjunctive.setVisible(false);
		searcht1.setText("Start");
		searcht2.setText("End");
		searcht2.setVisible(true);
		searchf2.setVisible(true);
		searchf2.setPromptText("mm/dd/yyyy");
		searchf1.setPromptText("mm/dd/yyyy");

	}

	/**
	 * This method checks the textfield for the Tags you want to search for
	 * 
	 * @param event allows you to create an event
	 */
	@FXML
	void searchTagsSelected(ActionEvent event) {

		searchSingle.setVisible(true);
		searchConjective.setVisible(true);
		searchDisjunctive.setVisible(true);
		searcht1.setText("Tag-pair");
		searcht2.setText("Tag-pair");
		searcht2.setVisible(false);
		searchf2.setVisible(false);
		searchf2.setPromptText("location=rutgers");
		searchf1.setPromptText("person=sesh");
	}

	/**
	 * This method checks the textfield for the Tag you want to search for
	 * 
	 * @param event allows you to create an event
	 */
	@FXML
	void singleTagSearch(ActionEvent event) {
		searcht2.setVisible(false);
		searchf2.setVisible(false);
	}

	/**
	 * This method checks the textfield for the Tags you want to search for
	 * 
	 * @param event allows you to create an event
	 */
	@FXML
	void multiTagSearch(ActionEvent event) {
		searcht2.setVisible(true);
		searchf2.setVisible(true);
	}

	/**
	 * This method checks photo if it is a duplicate when searching for it
	 * 
	 * @param photo         the photo you are checking duplicates for
	 * @param searchResults the arraylist of search results you are searching
	 *                      through
	 * @return true if it is a duplicate, false otherwise
	 */
	private boolean isDuplicate(Photo photo, ArrayList<Photo> searchResults) {

		for (Photo lPhoto : searchResults) {

			if (lPhoto.getFilePath().equals(photo.getFilePath())) {

				return true;

			}
		}
		return false;

	}

	/**
	 * Logs out of the program
	 * 
	 * @param e logs out of the program
	 * @throws Exception if log out doesnt work
	 */
	public void logOff(ActionEvent e) throws Exception {

		logUserOut(e);
	}

	/**
	 * Saves the user data in the data file
	 * 
	 * @param users The list of users in the scene
	 * @throws Exception if users wont save
	 */
	public static void save(List<User> users) throws Exception {

		FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);

		objectOutputStream.close();
		fileOutputStream.close();

	}

}
