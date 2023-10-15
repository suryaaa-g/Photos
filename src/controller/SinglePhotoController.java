package controller;

import java.io.File;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

/**
 * This class displays the photo in a single screen and gives more in depth
 * information
 * 
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 */

public class SinglePhotoController implements LogOutController {

	/**
	 * The current user
	 */
	private User current;

	/**
	 * The current album
	 */
	private Album currentAlbum;

	/**
	 * FXML button to log off
	 */
	@FXML
	Button mLogOff;

	/**
	 * FXML button to set caption
	 */
	@FXML
	Button mCaption;

	/**
	 * FXML button to remove tag
	 */
	@FXML
	Button mRemoveTag;

	/**
	 * FXML button to add tag
	 */
	@FXML
	Button mAddTag;

	/**
	 * FXML ImageView to display image
	 */
	@FXML
	ImageView displayArea;

	/**
	 * FXML TextField to see caption
	 */
	@FXML
	TextField tfCaption;
	
	/**
	 * FXML Text to see date
	 */
    @FXML
    private Text dateText;

	/**
	 * FXML TextField to type tag name
	 */
	@FXML
	TextField tfTagName;

	/**
	 * FXML TextField to type tag value
	 */
	@FXML
	TextField tfTagValue;

	/**
	 * FXML ListView to display tags
	 */
	@FXML
	ListView<String> listview;

	/**
	 * Local variable to get users
	 */
	public static List<User> users = new ArrayList<>();

	/**
	 * Local variable to get photoList
	 */
	public static ArrayList<Photo> photoList = new ArrayList<>();

	/**
	 * Local variable to get photo
	 */
	public static Photo photo;

	/**
	 * ArrayList to keep track of tags
	 */
	public static ArrayList<Tag> taglist = new ArrayList<>();

	/**
	 * ArrayList to keep track of tags to display
	 */
	public static ArrayList<String> tagdisplay = new ArrayList<>();

	/**
	 * ObservableList to keep track of tag changes
	 */
	public ObservableList<String> obstag;
	
	/**
	 * datetimeformat for displaying date
	 */
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");


	/************************************************************************/

	/**
	 * Start method for the SinglePhotoView takes in current album and the user and
	 * displays the image
	 * 
	 * @param currentAlbum the current album
	 * @param currentUser  the current User
	 * 
	 */
	public void start(Album currentAlbum, User currentUser) {
		// TODO Auto-generated method stub
		this.currentAlbum = currentAlbum;
		this.current = currentUser;

		Image p = new Image(photo.getFilePath());
		displayArea.setImage(p);

		

		if (!taglist.isEmpty()) {
			listview.getSelectionModel().select(0);
			
		}
		
//		this.listview.getSelectionModel().selectedIndexProperty()
//		.addListener((obs, oldVal, newVal) -> update());
		
		update();

	}


	/**
	 * Removes the tag from the listview and the photo
	 * 
	 * @param event Handles event
	 * @throws Exception if tag does not remove
	 */
	public void removeTag(ActionEvent event) throws Exception {
		int index = listview.getSelectionModel().getSelectedIndex();

		ArrayList<Tag> tagList = photo.getPhotoTags();

		photo.removeTag(tagList.get(index).tagName, tagList.get(index).tagValue);

		update();

		save(users);
	}

	/**
	 * Adds the tag to the listview and the photo
	 * 
	 * @param event handles event
	 * @throws Exception if tag does not add
	 */

	public void addTag(ActionEvent event) throws Exception {
		String tagName = tfTagName.getText().trim();
		String tagValue = tfTagValue.getText().trim();

		Tag newTag = new Tag(tagName, tagValue);
		

		if (tagName.isEmpty() || tagValue.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tag Add Error");
			alert.setContentText("Tag Information Incomplete.");
			alert.showAndWait();
			return;
		}
		
		if(photo.tagChecker(newTag)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tag Add Error");
			alert.setContentText("This tag name and value already exists!!");
			alert.showAndWait();
			return;
		}

		else {
			
			Tag tag = new Tag(tagName, tagValue);
			photo.addTag(tag.tagName, tag.tagValue);
			
			update();
			save(users);
		}

	}

	/**
	 * Takes you back to previous scene
	 * 
	 * @param e handles event
	 * @throws IOException if scene does not go back
	 */
	public void back(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		Parent sceneManager = (Parent) loader.load();
		ViewPhotosController photoController = loader.getController();
		Scene userScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		photoController.start(currentAlbum, current, users, photoList);
		appStage.setScene(userScene);
		appStage.show();

	}

	/**
	 * 
	 * Save Method for this controller
	 * 
	 * @param users The current list of users
	 * @throws Exception if users dont save
	 */

	private static void save(List<User> users) throws Exception {

		FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);

		objectOutputStream.close();
		fileOutputStream.close();
	}

	/**
	 * Updates the Listview to display the tags
	 * 
	 */
	public void update() {
		Calendar date = photo.getPhotoDate();
		String d = dateTimeformat.format(date.getTime());
		
		dateText.setText(d);
		tfCaption.setText(photo.getPhotoCaption());

		tagdisplay.clear();
		ArrayList<Tag> tags = photo.getPhotoTags();

		for (Tag tag : tags) {
			tagdisplay.add("Name: " + tag.tagName + " | Value: " + tag.tagValue);
		}
		obstag = FXCollections.observableArrayList(tagdisplay);
		listview.setItems(obstag);
		// System.out.println(taglist.toString());
		tfTagName.clear();
		tfTagValue.clear();
	}

	/**
	 * 
	 * Log out user
	 * 
	 * @param e handles event
	 * @throws Exception if log off does not work
	 */

	public void logOff(ActionEvent e) throws Exception {

		logUserOut(e);
	}

}
