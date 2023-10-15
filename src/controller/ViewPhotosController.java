package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

/**
 * This class displays all the photos in the album and has a slideshow
 *        
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101) 
 * 
 * 
 * 
 */

public class ViewPhotosController {

	/**
	 * FXML ListView for list of photos
	 */
	@FXML
	ListView<Photo> photolistview;

	/**
	 * FXML ListView for list of tags
	 */
	@FXML
	ListView<String> tagListview;

	/**
	 * FXML ImageView for display photo
	 */
	@FXML
	ImageView imageDisplay;

	/**
	 * FXML Button for add photo
	 */
	@FXML
	Button mAddPhoto;

	/**
	 * FXML Button for delete photo
	 */
	@FXML
	Button mDeletePhoto;

	/**
	 * FXML Button to go back
	 */
	@FXML
	Button mBack;

	/**
	 * FXML Button to add tag
	 */
	@FXML
	Button mAddTag;

	/**
	 * FXML Button to delete tag
	 */
	@FXML
	Button mdeleteTag;

	/**
	 * FXML Text to set title
	 */
	@FXML
	Text TitlePage;

	/**
	 * FXML Button to search
	 */
	@FXML
	Button mSearchByButton;

	/**
	 * FXML Button to copy
	 */
	@FXML
	Button mcopyTo;

	/**
	 * FXML Button to move
	 */
	@FXML
	Button mmoveTo;

	/**
	 * FXML Button to move right
	 */
	@FXML
	Button mrightButton;

	/**
	 * FXML Button to move left
	 */
	@FXML
	Button mleftButton;

	/**
	 * FXML Button to edit caption
	 */
	@FXML
	Button mEditCaption;

	/**
	 * FXML TextField for copy/move location
	 */
	@FXML
	TextField tfcopyMove;

	/**
	 * FXML TextField for caption
	 */
	@FXML
	TextField tfCaption;

	/**
	 * FXML TextField for date
	 */
	@FXML
	TextField tfDate;

	/**
	 * FXML TextField for tagName
	 */
	@FXML
	TextField tfTagName;

	/**
	 * FXML TextField for tagValue
	 */
	@FXML
	TextField tfTagValue;

	/***************************/
	/**
	 * Displays single photo viewer
	 */
	@FXML
	Button mButtonDisplay;

	/**
	 * boolean to see if a selected photo is checked
	 */
	boolean checked;
	/***************************/

	/**
	 * ArrayList to store the instance of photos
	 * 
	 */
	public ArrayList<Photo> photoList = new ArrayList<>();

	/**
	 * Observable List to store the instance of photos
	 * 
	 */
	public ObservableList<Photo> obsList;

	/**
	 * datetimeformat for displaying date
	 */
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Stores current user and list of users
	 * 
	 */
	User currentUser;

	/**
	 * local list of users
	 */
	List<User> users = new ArrayList<>();

	/**
	 * current album and Photo
	 */
	Album currentAlbum;

	/**
	 * current photo
	 */
	Photo currentPhoto;

	/**
	 * previous caption
	 */
	String lastCaption = "";

	/**
	 * local taglist
	 */
	public static ArrayList<Tag> taglist = new ArrayList<>();

	/**
	 * local tagdisplay
	 */
	public static ArrayList<String> tagdisplay = new ArrayList<>();

	/**
	 * local obslist
	 */
	public ObservableList<String> obstag;

	/**
	 * This method starts the scene to display all the photos
	 * 
	 * @param selectAlbum the current album passed in
	 * @param current     the current user passed in
	 * @param users       the current list of users
	 * @param photoList   the current albums list of photos
	 * 
	 */

	public void start(Album selectAlbum, User current, List<User> users, ArrayList<Photo> photoList) {
		this.photoList = photoList;
		obsList = FXCollections.observableArrayList(this.photoList);
		this.photolistview.setItems(obsList);

		displayThumb();

		this.currentAlbum = selectAlbum;
		this.currentUser = current;
		this.users = users;

		if (!photoList.isEmpty()) {

			photolistview.getSelectionModel().select(0);
			update();
		}

		displayPic2();

//	if(!photolistview.getSelectionModel().isEmpty()) {
//		this.tagListView.setItems(FXCollections.observableArrayList(this.photolistview.getSelectionModel().getSelectedItem().getPhotoTags()));
//
//	}

//	  obstag = FXCollections.observableArrayList(photolistview.getSelectionModel().getSelectedItem().getPhotoTags());
		// tagListView.setItems(FXCollections.observableArrayList(tagList));

		// tagListView.getSelectionModel().getSelectedIndex().getTagValue();

		/**
		 * idk what this does found it online but it works! this is to display the photo
		 * when selected (thumbnail)
		 * 
		 */

		this.photolistview.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> displayPic());

		this.photolistview.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> update());

	

		this.photolistview.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> setStockPic());
		
		this.photolistview.getSelectionModel().selectedIndexProperty()
		.addListener((obs, oldVal, newVal) -> update());

	}

	/**
	 * This method sets the stock pics when the stock album is selected
	 * 
	 */

	private void setStockPic() {
		// Calendar date = Calendar.getInstance();
		Photo photo = photolistview.getSelectionModel().getSelectedItem();
		// String d = dateTimeformat.format(date.getTime());

		Calendar date = currentAlbum.getPhotos().get(0).getPhotoDate();
		String d = dateTimeformat.format(date.getTime());
		tfDate.setText(d);

	}

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

//	            if(!photoList.isEmpty()) {
//	            	Calendar date=  photolistview.getSelectionModel().getSelectedItem().photoDate;
//	            	String d = dateTimeformat.format(date.getTime());
//	        		//photolistview.getSelectionModel().select(0);
//	        		tfCaption.setText(photolistview.getSelectionModel().getSelectedItem().photoCaption);
//	        		tfDate.setText(d);
//	        	}

					// System.out.println();
					//
					setGraphic(photoView);
				}
			}
		});

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
	 * moves photo to the right (slideshow)
	 * 
	 * @param e handles event
	 */

	public void nextPhoto(ActionEvent e) {
		if (!photolistview.getSelectionModel().isEmpty()) {
			photolistview.getSelectionModel().select(photolistview.getSelectionModel().getSelectedIndex() + 1);

		}
	}

	/**
	 * moves photo to the left (slideshow)
	 * 
	 * @param e handles event
	 */
	public void prevPhoto(ActionEvent e) {
		if (!photolistview.getSelectionModel().isEmpty() && photolistview.getSelectionModel().getSelectedIndex() != 0) {
			photolistview.getSelectionModel().select(photolistview.getSelectionModel().getSelectedIndex() - 1);

		}
	}

//
//public void setStockDate() {
//	if(currentAlbum.albumName.equals("stockAlbum")) {
//		
//	}
//}

	/**
	 * adds photo to the listview and album
	 * 
	 * @param e handles event
	 * @throws Exception if add photo does not works
	 */
	public void addPhoto(ActionEvent e) throws Exception {
		FileChooser filechooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
				"*.jpeg", "*.gif");
		filechooser.getExtensionFilters().add(extFilterJPG);
		File imgfile = filechooser.showOpenDialog(null);

		if (imgfile == null) {
			return;
		}

		else {

			Image image = new Image(imgfile.toURI().toString());
			String name = imgfile.getName();

			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(imgfile.lastModified());

			Photo addedPhoto = new Photo(name, "", date, imgfile.toURI().toString());

			for (Photo curr : currentAlbum.getPhotos()) {
				if ((curr.getPhotoName().equals(addedPhoto.getPhotoName()))
						&& (curr.getFilePath().equals(addedPhoto.getFilePath()))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error in Adding New Photo");

					alert.setContentText("This photo already exists in the album");

					alert.showAndWait();
					return;

				}
			}

			String d = dateTimeformat.format(date.getTime());
//    		//photolistview.getSelectionModel().select(0);
//    		tfCaption.setText(photolistview.getSelectionModel().getSelectedItem().photoCaption);
			tfDate.setText(d);

			this.photolistview.getItems().add(addedPhoto);

			photolistview.refresh();
			//System.out.println(imgfile.toURI().toString());

			currentAlbum.getPhotos().add(addedPhoto);
			save(users);
		}
	}

	/**
	 * Deletes the photo from the listview
	 * 
	 * @param e handles event
	 * @throws Exception if delete photo doesnt work
	 */

	public void deletePhoto(ActionEvent e) throws Exception {

		if (photolistview.getSelectionModel().getSelectedItem() != null) {
			Photo removedPhoto = photolistview.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Delete " + photolistview.getSelectionModel().getSelectedItem().getPhotoName() + " ?",
					ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {

				currentAlbum.getPhotos().remove(removedPhoto);
				this.photolistview.getItems().remove(removedPhoto);
				displayPic();

				// tagListView.setItems(null);
				
				save(users);

			} else {
				Alert alert2 = new Alert(AlertType.ERROR);
				alert2.setHeaderText("No selected Photo to delete!");
				alert2.showAndWait();
			}
			

		}

	}

	/**
	 * 
	 * Back button to go back to previous screen
	 * 
	 * @param e handles event
	 * @throws IOException if prev scene errors out
	 */

	public void back(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumDisplay.fxml"));
		Parent sceneManager = (Parent) loader.load();
		AlbumDisplayController userController = loader.getController();
		Scene userScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		userController.start(users, currentUser);
		appStage.setScene(userScene);
		appStage.show();

	}

	/**
	 * copies the photo to another album
	 * 
	 * @throws Exception if photo does not copy
	 */

	public void copyTo() throws Exception {
		Photo photoToCopy = photolistview.getSelectionModel().getSelectedItem();

		String albumName = tfcopyMove.getText().trim();

		for (Album album : this.currentUser.getAlbums()) {
//			
			if (album.getAlbumName().equals(albumName)) {
				Album albumToCopy = album;
				for (Photo photo : album.getPhotos()) {
					if (photo.getPhotoName().equals(photoToCopy.getPhotoName())
							&& photo.getFilePath().equals(photoToCopy.getFilePath())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Photo is already present in that Album");
						alert.showAndWait();
						return;
					}

				}
			}

//			if(!album.getAlbumName().equals(albumName)) {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setHeaderText("Photo is already present in the Album!!");
//				alert.showAndWait();
//				return;
//			}

			if (album.getAlbumName().equals(albumName)) {
				album.getPhotos().add(photoToCopy);
				save(users);
			}

			save(users);

			tfcopyMove.clear();

		}

	}

	/**
	 * moves the photo to another album
	 * 
	 * @throws Exception if photo does not move
	 */

	public void moveTo() throws Exception {
		// photo to be moved
		Photo photoToMove = photolistview.getSelectionModel().getSelectedItem();

		// name of album to move to
		String albumName = tfcopyMove.getText().trim();

		// System.out.println(albumName);

		for (Album album : this.currentUser.getAlbums()) {

			// needs to be deleted

			// System.out.println(album.getAlbumName());

			if (album.getAlbumName().equals(albumName)) {

				for (Photo photo : album.getPhotos()) {
					if (photo.getPhotoName().equals(photoToMove.getPhotoName())
							&& photo.getFilePath().equals(photoToMove.getFilePath())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Photo is already present");
						alert.showAndWait();
						return;
					}
				}

				album.getPhotos().add(photoToMove);
				save(users);

				this.currentAlbum.getPhotos().remove(photoToMove);

				photolistview.getItems().remove(photoToMove);

				tfcopyMove.clear();

				save(users);
				return;
			}
		}

		Alert alert2 = new Alert(AlertType.ERROR);
		alert2.setHeaderText("This album doesn't exist");
		alert2.showAndWait();
		tfcopyMove.clear();

	}

	/**
	 * 
	 * Saves User info
	 * 
	 * @param users the Current list of users to save
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
	 * Edits the caption
	 * 
	 * @param e handles event
	 * @throws Exception if edit caption fails
	 */

	public void editCaption(ActionEvent e) throws Exception {

		tfCaption.setEditable(true);
		String caption = tfCaption.getText();

		Photo photo = photolistview.getSelectionModel().getSelectedItem();

		photo.setPhotoCaption(caption);
		tfCaption.setText(caption);

		

		update();

		save(users);
	}

	/**
	 * BELOW METHODS I IMPLEMENTED IN SINGLEPHOTOVIEW CONTROLLER
	 * 
	 * I KEPT THEM IN CASE YOU WANTED TO TRY TO DO IT IN THE SAME SCENE (IF NOT YOU
	 * CAN DELETE THE METHODS AS WELL AS THE BUTTONS FOR THEM)
	 * 
	 * @throws Exception
	 * 
	 * 
	 * 
	 **/

	/**
	 * deletes tag from listview
	 * 
	 * @throws Exception if tag fails to delete
	 */
	public void deleteTag() throws Exception {
		int index = photolistview.getSelectionModel().getSelectedIndex();

		Photo photo = photolistview.getSelectionModel().getSelectedItem();

		ArrayList<Tag> tagList = photo.getPhotoTags();

		photo.removeTag(tagList.get(index).tagName, tagList.get(index).tagValue);

		update();

		save(users);
	}

	/**
	 * Updates listview as tags are added
	 * 
	 */
	public void update() {

		Photo photo = photolistview.getSelectionModel().getSelectedItem();
		photolistview.refresh();
		
		
			
		
		tfCaption.setText(photo.getPhotoCaption());
		
		Calendar date = photo.getPhotoDate();
		String d = dateTimeformat.format(date.getTime());
		tfDate.setText(d);
		

		tagdisplay.clear();

		if (photo != null) {

			ArrayList<Tag> tags = photo.getPhotoTags();

			for (Tag tag : tags) {
				tagdisplay.add("Name: " + tag.tagName + " | Value: " + tag.tagValue);
			}

			tagListview.setItems(FXCollections.observableArrayList(tagdisplay));

			tfTagName.clear();
			tfTagValue.clear();

			tfCaption.setText(photo.getPhotoCaption());
			
		}
		
	}

	/**
	 * Adds a tag
	 * 
	 * @param e handles event
	 * @throws Exception if add tag fails
	 */
	public void addTag(ActionEvent e) throws Exception {

		String tagName = tfTagName.getText().trim();
		String tagValue = tfTagValue.getText().trim();
		Tag newTag = new Tag(tagName, tagValue);
		Photo photo1 =  photolistview.getSelectionModel().getSelectedItem();

		if (tagName.isEmpty() || tagValue.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tag Add Error");
			alert.setContentText("Tag Information Incomplete.");
			alert.showAndWait();
			return;
		}
		
		if(photo1.tagChecker(newTag)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tag Add Error");
			alert.setContentText("This tag name and value already exists!!");
			alert.showAndWait();
			return;
		}

		else {

			Photo photo = photolistview.getSelectionModel().getSelectedItem();
			Tag tag = new Tag(tagName, tagValue);
			photo.addTag(tag.tagName, tag.tagValue);

			update();

			save(users);
		}

	}

}
