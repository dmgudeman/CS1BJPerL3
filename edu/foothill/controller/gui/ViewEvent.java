package edu.foothill.controller.gui;

// import packages
import edu.foothill.model.Command;
import edu.foothill.model.Media;

// import java.util
import java.util.EventObject;

/**
 * This class represents one Event, it includes one object of the Media class as well attributes
 * the source of the event (The object on which the Event initially occurred) and the command 
 * that was performed on it (ADD, DELETE, PRINT, EXIT).
 * Author: DG
 *
 */
public class ViewEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private Command command;
	private Media media;
	private String searchString;
	private boolean allMediaFlag;
	
	/**
	 * Parameterized constructor taking three parameters:
	 * -source (The object on which the Event initially occurred)
	 * -media (An Object of the Media class)
	 * -command (ADD or DELETE or PRINT or EXIT)
	 * and returns the command and a media object
	 * Author: DG
	 */
	public ViewEvent(Object source, Media media, Command command) {
		super(source);
		this.command = command;
		this.media = media;	
	}
	
	/**
	 * Parameterized constructor taking four parameters:
	 * -source (The object on which the Event initially occurred)
	 * -text (A string to be searched on)
	 * -command (ADD or DELETE or PRINT or EXIT)
	 * and returns the command and a media object
	 * Author: SS
	 */
	public ViewEvent(Object source, Media media, Command command, String text) {
		super(source);
		this.command = command;
		this.media = media;	
		this.searchString = text;	
	}
	/**
	 * Parameterized constructor taking five parameters:
	 * -source (The object on which the Event initially occurred)
	 * -text (A string to be searched on)
	 * -command (ADD or DELETE or PRINT or EXIT)
	 * -newAllMediaFlag indicates that all of the media need to be acted on.
	 * and returns the command and a media object
	 * Author: SS
	 */
	public ViewEvent(Object source, Media media, Command command, String text, boolean newAllMediaFlag) {
		super(source);
		this.command = command;
		this.media = media;	
		this.searchString = text;
		this.allMediaFlag = newAllMediaFlag;
	}
	/**
	 * A getter for the allMediaFlag
	 * Author: SS
	 */
	public boolean getAllMediaFlag() {
		return allMediaFlag;
	}
	/**
	 * A getter for the searchString
	 * Author: SS
	 */
	public String getSearchString() {
		return searchString;
	}
	/**
	 * A getter for the command
	 * Author: DG
	 */
	public Command getCommand() {
		return command;
	}
	/**
	 * A getter for the media object, used by the controller to collect
	 * the media
	 * Author: DG
	 * 
	 */
	public Media getMedia() {
		return media;
	}

}
