package model;

import java.io.Serializable;


/**
 * This is the model class for the Tag
 * @author Suryateja Gandhesiri (sg1571)
 * @author Afaq Qamar (aq101)
 * 
 * */
public class Tag implements Serializable{

	/**
	 * serial id for this class
	 */
	private static final long serialVersionUID = 3430529772963736249L;
	
	/**
	 * name of tag
	 */
	
	public String tagName;
	
	/**
	 * value  of the tag
	 */
	
	public String tagValue;
	
	/**
	 * basic constructor for the tag
	 * @param tagName the tag name
	 * @param tagValue the tag value
	 */
	
	public Tag(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	
	/**
	 * tags of the photo
	 * @return the tag name
	 */
	
	public String getTagName() {
		return this.tagName;
	}
	/**
	 * gets the value of the Tag
	 * @return the String value of the Tag
	 */
	public String getTagValue() {
		return this.tagValue;
	}
	
	/**
	 * returns the string name and value of the tag 
	 * @return formatted tag name and value
	 * */
	@Override
	public String toString() {
		return this.tagName + ":" + this.tagValue;
	}
	
	

}
