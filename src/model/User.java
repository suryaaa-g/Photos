package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * This is the model class for the User
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 * */
public class User implements Serializable {
	
	
	
	
	/*
	 * Serial ID for this class 
	 * */
	
	
	private static final long serialVersionUID = -3458166756928608586L;
	
	
	/**
	 * the username 
	 * */
	String username;
	
	/**
	 * lit of user albums
	 * */
	ArrayList<Album> userAlbums;
	
	/**
	 * Constructor that takes in User as a param
	 * @param username the username for the User
	 * */
	
	public User(String username) {
		this.username = username;
		this.userAlbums = new ArrayList<Album>();
		
	}
	
	
	/**
	 * gets the username of the user
	 * @return the username of the user
	 * */
	
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * turns the username to a string format 
	 * @return username in string form
	 * */
	public String toString() {
		return this.username;
	}
	
	/**
	 * gets the albums for the user
	 * @return the albums for the current user
	 * 
	 * */
	
	public ArrayList<Album> getAlbums(){
		
		return this.userAlbums;
	}
	
	/**
	 * This method checks if there is an existing album after renaming
	 * @param albumName The new album name you are trying to rename to
	 * @return boolean True if there is another album with same name false otherwise
	 * */
	public boolean albumRenameChecker(Album albumName) {
		for(Album album : userAlbums) {
			if (album.albumName.trim().equals(albumName.albumName.trim())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
}
