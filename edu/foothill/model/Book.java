package edu.foothill.model;

// import java.io
import java.io.Serializable;

/**
 * One object of Book type class and holds information about one Media object. 
 * This subclass extends Media Class. 
 * Adds parameters author and Book type to the Media class information
 * Author DG  
 */
public class Book extends Media implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String author;
	
	
	/**
	 * Constructor utilizing the Media Superclass constructor with parameters: title
	 * location, format and notes.  It adds parameters author and Book type.
	 * Author DG
	 */
	public Book(String title, String location, String format, String notes, String author) {
		super(title, location, format,  notes);
		this.author = author;
	}
	
	/**
	 * Default Constructor
	 * Author: DG
	 */
    public Book()
    {
    	
    }
	
    // Author DG
	public String getAuthor() {
		return author;
	}
	// Author DG
	public void setAuthor(String author) {
		this.author = author;
	}
	// Author DG
	public Type getType() {
		return Type.Book;
	}

	
	/**
	* Returns a String containing all the data stored in this object. DG
    */
  
    public String toString()
    {
        String result = super.toString() +"Author: " + this.getAuthor() +  "\n";
        return result;
    }	
}
