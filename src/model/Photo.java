package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.*;

/**
 * This is the model class for the Photo
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 * */
public class Photo implements Serializable{

	/**
	 * Version ID for this class
	 */
	private static final long serialVersionUID = 6955723612371190680L;
	
	

	/**
	 * caption for the photo
	 */
	public String photoCaption;
	
	/**
	 * name of the photo
	 */
	
	public String photoName;
	
	/**
	 * date of the photo
	 */
	
	public Calendar photoDate;
	
	/**
	 * tags of the photo
	 */
	public ArrayList<Tag> photoTags;
	
	/**
	 * file source/path of the photo
	 */
	public String filePath;
	

	
	/**
	 * Constructor for the photo 
	 * @param photoName the name of the photo
	 * @param photoCaption the caption of the photo
	 * @param photoDate the date of the photo
	 * @param filePath the location where the photo is located
	 */
	
	public Photo(String photoName, String photoCaption, Calendar photoDate, String filePath) {
		this.photoName = photoName;
		this.photoCaption = photoCaption;
		this.photoDate = photoDate;
		this.photoTags = new ArrayList<Tag>();
		this.filePath = filePath;
		photoDate = new GregorianCalendar();
		photoDate.set(Calendar.MILLISECOND, 0);
		//this.date = photoDate.getTime();
		
	}
	/**
	 * Method to add a tag to the photo 
	 * @param name the String value for the name of tag 
	 * @param value the String value for the value of the tag
	 * */
	public void addTag(String name, String value) {
		photoTags.add(new Tag(name,value));
	}
	
	/**
	 * Method to remove a tag to the photo 
	 * @param name the String value for the name of tag 
	 * @param value the String value for the value of the tag
	 * */
	public void removeTag(String name, String value) {
		for(int i = 0; i < photoTags.size(); i++) {
			Tag cur = photoTags.get(i);
			if(cur.tagName.toLowerCase().equals(name.toLowerCase()) && cur.tagValue.toLowerCase().equals(value.toLowerCase())) {
				photoTags.remove(i);
			}
		}
	}
	
	
	/**
	 * gets photo name
	 * @return String photo name
	 */
	
	public String getPhotoName() {
		return this.photoName;
		
	}
	
	/**
	 * gets the photo Date
	 * @return the photo date
	 * */
	public Calendar getPhotoDate() {
		return this.photoDate;
	}
	
	
//	public Date getDate() {
//		return date;
//	}
	
	/**
	 * gets the tags of the photo 
	 * @return the photo tags
	 * */
	public ArrayList<Tag> getPhotoTags(){
		return this.photoTags;
	}
	
	
	/**
	 * gets the caption of the photo  
	 * @return the photo caption
	 * */
	public String getPhotoCaption() {
		return this.photoCaption;
	}
	
	
	/**
	 * sets the caption of the photo  
	 * @param photoCaption The caption of the photo
	 * */
	public void setPhotoCaption(String photoCaption) {
		this.photoCaption = photoCaption;
	}
	
	
	
	/**
	 * gets the filepath of the photo  
	 * @return the filepath
	 * */
	public String getFilePath() {
		return this.filePath;
	}
	
	/**
	 * sets the filepath of the photo  
	 * @param fp The String filepath
	 * */
	public void setFilePath(String fp) {
		 this.filePath = fp;
	}
	
	
	/**
	 * Turns the name of the photo to a string form 
	 * @return photo name
	 * */
	public String toString() {
		return this.photoName;
	}
	
	
	/**
	 * Prints the Photo tags 
	 * @return formatted tag name and tag value
	 * */
	public ArrayList<String> printPhotoTags() {
		ArrayList<Tag> tags = this.getPhotoTags();
		ArrayList<String> formatted = new ArrayList<String>();
		for(Tag s : tags) {
			
			formatted.add(s.tagName + "=" + s.tagValue);
		}
		return formatted;
	}
	
	/**
	 * This method checks for duplicate tags
	 * @param newTag The new tag you are going to be adding 
	 * @return boolean true or false to check if there is a same tag
	 * */
	public boolean tagChecker(Tag newTag) {
		for(Tag tag : this.getPhotoTags()) {
			if(tag.tagName.equals(newTag.tagName) && (tag.tagValue.equals(newTag.tagValue))) {
				return true;
			}
			
		}
		return false;
	}
	
	
}
