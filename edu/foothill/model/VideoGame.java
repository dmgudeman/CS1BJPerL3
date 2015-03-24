package edu.foothill.model;

// import java.io
import java.io.Serializable;

/**
 * One object of VidoeGame Type class and holds information about one Media object. 
 * This subclass extends Media Class. 
 * Adds parameters VideoGame type to the Media class information.
 * Author DG  
 */
public class VideoGame extends Media implements Serializable{
	private static final long serialVersionUID = 1L;


	
	/**
	 * Constructor utilizing the Media Superclass constructor with parameters: title
	 * location, format and notes.  It adds parameters VideoGame type.
	 * Author DG
	 */
	public VideoGame(String title, String location, String format, String notes) {
		super(title, location, format, notes);
	}

	/**
	 * Default constructor
	 * Author: SS
	 */
	public VideoGame() {
		
	}

	// getter for media type author: DG
	public Type getType() {
		return Type.VideoGame;
	}

	
	/**
	* Returns a String containing all the data stored in this object.
	* Author: DG
    */

    public String toString()
    {
        String result = super.toString() + "\n";
        return result;
    }	
    
}

