package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

/**
 * 
 * Search Page to search for a photo by tag
 * 
 * @author Suryateja Gandhesiri
 * @author Afaq Qamar
 * 
 * 
 */

public class SearchPageController {

	/**
	 * FXML button for creating the album
	 */
	@FXML
	private Button createAlbumButton;

	/**
	 * FXML ImageView for displaying the image
	 */
	@FXML
	private ImageView imageDisplay;

	/**
	 * FXML Button for back button
	 */
	@FXML
	private Button mBack;

	/**
	 * FXML Button for display button
	 */
	@FXML
	private Button mButtonDisplay;

	/**
	 * FXML Button for left button
	 */
	@FXML
	private Button mleftButton;

	/**
	 * FXML Button for right button
	 */
	@FXML
	private Button mrightButton;

	/**
	 * FXML ListView for photo list view
	 */
	@FXML
	private ListView<Photo> photolistview;

	/**
	 * FXML text for caption
	 */
	@FXML
	private Text tCaption;

	/**
	 * FXML text for date
	 */
	@FXML
	private Text tDate;

	/**
	 * FXML text for tags
	 */
	@FXML
	private Text tTags;

	/**
	 * FXML ListView for tags
	 */
	@FXML
	private ListView<String> tagListview;

	/**
	 * FXML textfield for caption
	 */
	@FXML
	private TextField tfCaption;

	/**
	 * FXML textfield for create album
	 */
	@FXML
	private TextField tfCreateAlbum;

	/**
	 * FXML textfield for date
	 */
	@FXML
	private TextField tfDate;

	/**
	 * FXML text for title page
	 */
	@FXML
	private Text titlePage;

	/**
	 * Arraylist for the list of photos
	 */
	private ArrayList<Photo> photoList = new ArrayList<>();

	/**
	 * Observable list for the list of photos
	 */
	public ObservableList<Photo> obsList;

	/**
	 * Observable list 2 for the list of photos
	 */
	public ObservableList<Album> obsList2;

	/**
	 * Listview for the Albums
	 */
	public ListView<Album> listview;

	/**
	 * date format for the displaying of date
	 */
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * current user
	 */
	User currentUser;

	/**
	 * list of current users
	 */
	List<User> users = new ArrayList<>();

	/**
	 * boolean to see if a selected photo is checked
	 */
	boolean checked;

	/**
	 * current album and Photo
	 */
	Album currentAlbum;

	/**
	 * local tagdisplay
	 */
	public static ArrayList<String> tagdisplay = new ArrayList<>();

	/**
	 * Start method for the search page controller
	 * 
	 * @param photoList The arraylist for photos
	 * @param current   The current user
	 * @param users     The current list of users
	 * @param listview  The listview of albums from previous Album scenes
	 */
	public void start(ArrayList<Photo> photoList, User current, List<User> users, ListView<Album> listview) {

		this.photoList = photoList;
		this.currentUser = current;
		this.users = users;
		this.listview = listview;
		this.currentAlbum = initiateCurrentAlbum();

		obsList = FXCollections.observableArrayList(this.photoList);
		this.photolistview.setItems(obsList);
		displayThumb();

		if (!photoList.isEmpty()) {

			photolistview.getSelectionModel().select(0);

		}

		displayPic2();

		this.photolistview.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> displayPic());

		this.photolistview.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> update());

		update();

		this.photolistview.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> displayPic());

		this.photolistview.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> update());



//		this.photolistview
//		.getSelectionModel()
//		.selectedIndexProperty()
//		.addListener(
//				(obs, oldVal, newVal) -> 
//				setStockPic());

	}

	/**
	 * This method sets the stock pics when the stock album is selected
	 * 
	 */
//	private void setStockPic() {
//		//Calendar date = Calendar.getInstance();
//		Photo photo = photolistview.getSelectionModel().getSelectedItem();
//		//String d = dateTimeformat.format(date.getTime());
//		
//		
//		
//		Calendar date = this.getPhotos().get(0).getPhotoDate();
//		String d = dateTimeformat.format(date.getTime());
//		tfDate.setText(d);
//
//	}

	/**
	 * This method displays the picture on the Imageview without selection
	 * 
	 */
	private void displayPic2() {
		if (!photolistview.getSelectionModel().isEmpty()) {
			imageDisplay.setVisible(true);
			Photo photo = photolistview.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.getFilePath());
			imageDisplay.setImage(p);

			// tagListView.setItems(FXCollections.observableArrayList(this.photolistview.getSelectionModel().getSelectedItem().getPhotoTags()));

		}
	}

	/**
	 * method to display the thumbnail upon selection of listview item
	 * 
	 */

	private void displayThumb() {
		photolistview.setCellFactory(listView -> new ListCell<Photo>() {
			private ImageView photoView = new ImageView();

			@Override
			public void updateItem(Photo photo, boolean empty) {
				super.updateItem(photo, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					Image image = new Image(photo.getFilePath());
					photoView.setFitHeight(50);
					photoView.setFitWidth(70);
					photoView.setImage(image);

					setText("Caption: " + photo.getPhotoCaption()); // + " " + "Tags:" + photo.printPhotoTags() + " , "

					setGraphic(photoView);
				}
			}
		});

	}

	/**
	 * Goes back to the previous scene
	 * 
	 * @param event Handles the event
	 * @throws IOException
	 */
	@FXML
	void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumDisplay.fxml"));
		Parent sceneManager = (Parent) loader.load();
		AlbumDisplayController userController = loader.getController();
		Scene userScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		userController.start(users, currentUser);
		appStage.setScene(userScene);
		appStage.show();
	}

	/**
	 * Creates the Album
	 * 
	 * @param event Handles the event
	 * @throws Exception
	 */
	@FXML
	void createAlbum(ActionEvent event) throws Exception {
		if (tfCreateAlbum.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error Adding Album");
			alert.setContentText("You must enter a Album Name!");
			alert.showAndWait();

			return;
		}

		else {
			String albumName = tfCreateAlbum.getText().trim();

			for (Album album : currentUser.getAlbums()) {
				if (album.getAlbumName().equals(albumName)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This Album Name already exists");
					alert.showAndWait();
					return;
				}
			}

			Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + albumName + " ?", ButtonType.YES, ButtonType.NO,
					ButtonType.CANCEL);
			alert.setTitle("Add new user");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				currentUser.getAlbums().add(new Album(albumName));
				listview.getItems().add(new Album(albumName));

				save(users);

				AlbumDisplayController.obsList = FXCollections.observableArrayList(currentUser.getAlbums());

			}

		}

		// ALBUM CREATED
		// COPY ALL PHOTOS

		String albumName = tfCreateAlbum.getText().trim();

		for (Album album : this.currentUser.getAlbums()) {
//			
			if (album.getAlbumName().equals(albumName)) {

				for (Photo photo : photoList) {

					if (album.getAlbumName().equals(albumName)) {
						album.getPhotos().add(photo);
						save(users);
					}
				}

			}
		}

		save(users);

		tfCreateAlbum.clear();
		tfCreateAlbum.setDisable(false);

		back(event);
	}

	/**
	 * This method starts the current album and gets the photos related to it
	 * 
	 * @return The initiated album
	 */
	public Album initiateCurrentAlbum() {

		Album tempCurrentAlb = new Album("tempCurrentAlb");

		for (Photo photo : photoList) {

			tempCurrentAlb.getPhotos().add(photo);
		}

		return tempCurrentAlb;

	}

	/**
	 * swithces scnene to singlephoto when button is clicked
	 * 
	 * @param event Allows event to be handled
	 * @throws IOException if display button doesnt work
	 */

	public void display(ActionEvent event) throws IOException {

		if (photoList.size() > 0) {
			for (int i = 0; i < photoList.size(); i++) {
				if (photolistview.getSelectionModel().isSelected(i)) {
					checked = true;
				}

				if (checked) {
					int photoIndex = photolistview.getSelectionModel().getSelectedIndex();
					Photo current = currentAlbum.getPhotos().get(photoIndex);

					SinglePhotoController.photo = photolistview.getSelectionModel().getSelectedItem();
					SinglePhotoController.users = users;
					SinglePhotoController.photoList = photoList;

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SinglePhoto.fxml"));

					Parent sceneManager = (Parent) fxmlLoader.load();

					SinglePhotoController singlePhotoController = fxmlLoader.getController();
					Scene adminScene = new Scene(sceneManager);

					Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

					if (appStage != null) {
						singlePhotoController.start(currentAlbum, currentUser);
						appStage.setScene(adminScene);
						appStage.show();
					}

				}

			}
		}
	}

	/**
	 * Goes to the next photo
	 * 
	 * @param event handles the event
	 */
	@FXML
	void nextPhoto(ActionEvent event) {
		if (!photolistview.getSelectionModel().isEmpty()) {
			photolistview.getSelectionModel().select(photolistview.getSelectionModel().getSelectedIndex() + 1);

		}
	}

	/**
	 * Goes to the prev photo
	 * 
	 * @param event handles the event
	 */
	@FXML
	void prevPhoto(ActionEvent event) {
		if (!photolistview.getSelectionModel().isEmpty() && photolistview.getSelectionModel().getSelectedIndex() != 0) {
			photolistview.getSelectionModel().select(photolistview.getSelectionModel().getSelectedIndex() - 1);
		}
	}

	/**
	 * displays the picture in the big screen
	 */

	private void displayPic() {
		if (!photolistview.getSelectionModel().isEmpty()) {
			imageDisplay.setVisible(true);
			Photo photo = photolistview.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.getFilePath());
			imageDisplay.setImage(p);

			// tagListView.setItems(FXCollections.observableArrayList(this.photolistview.getSelectionModel().getSelectedItem().getPhotoTags()));

		}
	}

	/**
	 * Saves the users and keeps track of data
	 * 
	 * @param users The current list of users
	 * @throws Exception if users wont save
	 */
	private static void save(List<User> users) throws Exception {

		FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);

		objectOutputStream.close();
		fileOutputStream.close();

	}

	/**
	 * Updates listview as tags are added
	 * 
	 */
	public void update() {

		Photo photo = photolistview.getSelectionModel().getSelectedItem();

		Calendar date = photo.getPhotoDate();
		String d = dateTimeformat.format(date.getTime());
		tfDate.setText(d);

		tfCaption.setText(photo.getPhotoCaption());

		tagdisplay.clear();

		if (photo != null) {

			ArrayList<Tag> tags = photo.getPhotoTags();

			for (Tag tag : tags) {
				tagdisplay.add("Name: " + tag.tagName + " | Value: " + tag.tagValue);
			}

			tagListview.setItems(FXCollections.observableArrayList(tagdisplay));

		}
	}
}
