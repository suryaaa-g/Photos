package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


/**
 * This is the model class for the Album
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 * */
public class Album implements Serializable {

	/**
	 * Version ID for this class
	 */
	private static final long serialVersionUID = -4143935150417416554L;
	
	/**
	 * Name of the album
	 */
	public String albumName;
	
	/**
	 * list of the photos in the album
	 */
	public ArrayList<Photo>photos;
	
	/**
	 * Constructor for the Album
	 * @param albumName String for the name of the album
	 * 
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		this.photos = new ArrayList<Photo>();
	}
	
	/**
	 * gets the album name
	 * @return Album name
	 */
	
	public String getAlbumName() {
		return this.albumName;
	}
	/**
	 * gets the list of photos
	 * @return list of photos
	 */
	
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	
	/**
	 * adds a photo to the album
	 * @param photo The photo you add
	 */
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * gets the number of photos in an album
	 * @return the number of photos in the album
	 */
	public int getPhotoCount() {
		return this.photos.size();
	}
	
	/**
	 * turns the album name to string format
	 * @return the album name
	 */
	public String toString() {
		return this.albumName;

	}
	
	
	
	
	/**********I thought we needed these for albums (Sort by A-Z and sort for Z-A) ****************/
	
//	public static Comparator<Album> sortByAZ = new Comparator<Album>() {
//		public int compare(Album album1, Album album2) {
//			return album1.albumName.compareTo(album2.albumName);
//		}
//	};
//	
//	public static Comparator<Album> sortByZA = new Comparator<Album>() {
//		public int compare(Album album1, Album album2) {
//			return album1.albumName.compareTo(album2.albumName)*-1;
//		}
//	};
	
	
	
	/***********CAN DELETE ABOVE METHODS IF THEY CAUSE ERRORS***************/
	
	
	
	
	
	
	
	
	
	
	
	
	
}
