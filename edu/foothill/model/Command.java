package edu.foothill.model;

/**
 * enumerator class contains the different command types and
 * creates a String for each enum
 * Author: DG
 */

public enum Command {

	ADD ("Add"), DELETE("delete"), PRINT("print"), SAVE("save"), ADD_WITH_SORT("addWithSort"), 
		DELETE_WITH_SORT("deleteWithSort"), PRINT_SONGS("printSongs"), PRINT_BOOKS("printBooks"),
				PRINT_VIDEOS("printVideos"), PRINT_VIDEO_GAMES("printVideoGames"), DELETE_LIBRARY("deleteLibrary");
	
  private final String type;
	
	private Command(final String type){
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
