package edu.foothill.model;

// import packages
import edu.foothill.model.Book;
import edu.foothill.model.Media;
import edu.foothill.model.MediaLibrary;
import edu.foothill.model.MediaLibraryWrapper;
import edu.foothill.model.Song;
import edu.foothill.model.Type;
import edu.foothill.model.Video;
import edu.foothill.model.VideoGame;

// import java.util
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.io.Serializable;



/**
 * This class holds the business logic and implements the Observer Observable
 * Pattern 
 * One object of MediaLibrary Class represents the library of
 * the various media.
 * one list of the Media Objects.
 * Author DG
 * Version 2: DS - removed references to Collection interface. Added the
 * following methods:addMedia, getLibraryByType, getLibraryByTitle, Mainly used
 * for business logic. 
 * Version 3: SS - added functionality to search & delete
 * multiple media with title matching search string
 */

public class MediaLibrary extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;

	private MediaLibraryWrapper mediaLibraryWrapper = new MediaLibraryWrapper();
	private List<Observer> observers = new ArrayList<Observer>();

	/**
	 * Default Constructor for List Class Author DG
	 */
	public MediaLibrary() {

	}

	public void setMediaLibraryWrapper(MediaLibraryWrapper mediaLibraryWrapper) {
		this.mediaLibraryWrapper = mediaLibraryWrapper;
	}

	public MediaLibraryWrapper getMediaLibraryWrapper() {
		return mediaLibraryWrapper;
	}

	/**
	 * Method adds media to the library
	 * Author: SS
	 */
	public void addMedia(Media media) {
		addToMediaWrapper(media);
		notifyObservers(mediaLibraryWrapper);
	}

	/**
	 * Method to add media and sort it and notifies the observer in the view
	 */

	public void addMediaWithSortByTitle(Media media) {
		addToMediaWrapper(media);
		sortByTitle(media.getType());
		notifyObservers(mediaLibraryWrapper);

	}

	/**
	 * Method to add the various media types to the mediaLibraryWrapper. 
	 * Author: SS
	 * V2: DG
	 */
	private void addToMediaWrapper(Media media) {
		switch (media.getType()) {
		case Book:
			mediaLibraryWrapper.getBooks().add((Book) media);
			break;
		case Song:
			mediaLibraryWrapper.getSongs().add((Song) media);
			break;
		case Video:
			mediaLibraryWrapper.getVideos().add((Video) media);
			break;
		case VideoGame:
			mediaLibraryWrapper.getVideogames().add((VideoGame) media);
			break;
		default:
			break;
		}
		System.out.println("Added " + media.getType() + " to library");
		System.out.println(mediaLibraryWrapper.toString());

	}

	/**
	 * method to remove media and notify the observers
	 * Author: DG          
	 */

	public void removeMedia(Media media) {
		removeFromMediaWrapper(media);

		notifyObservers(mediaLibraryWrapper);
	}

	/**
	 * method to determine which media type and which object to remove
	 * media
	 * Author: SS
	 * V2: DG
	 */
	private void removeFromMediaWrapper(Media media) {
		switch (media.getType()) {
		case Book:
			System.out.println(mediaLibraryWrapper.getBooks());
			mediaLibraryWrapper.getBooks().remove((Book) media);
			break;
		case Song:
			System.out.println(mediaLibraryWrapper.getSongs());
			mediaLibraryWrapper.getSongs().remove((Song) media);
			break;
		case Video:
			mediaLibraryWrapper.getVideos().remove((Video) media);
			break;
		case VideoGame:
			mediaLibraryWrapper.getVideogames().remove((VideoGame) media);
			break;
		default:
			break;
		}

		System.out.println("Removed " + media.getType() + " from library");
		System.out.println(mediaLibraryWrapper.toString());
	}

	/**
	 * method deletes media type with title matching the delete text If the
	 * allMediaFlag is set to true, the method performs the action for all media
	 * types           
	 * Author: SS
	 */
	private void removeFromMediaWrapper(Media media, String deleteText,
			boolean allMediaFlag) {
		if (!allMediaFlag) { // perform action only for specific media type
			switch (media.getType()) {
			case Book:
				deleteObjectWithMatchingTitle(
						(List) mediaLibraryWrapper.getBooks(), deleteText);
				break;
			case Song:
				deleteObjectWithMatchingTitle(
						(List) mediaLibraryWrapper.getSongs(), deleteText);
				break;
			case Video:
				deleteObjectWithMatchingTitle(
						(List) mediaLibraryWrapper.getVideos(), deleteText);
				break;
			case VideoGame:
				deleteObjectWithMatchingTitle(
						(List) mediaLibraryWrapper.getVideogames(), deleteText);
				break;
			default:
				break;
			}
		} else { // perform action for all media types

			deleteObjectWithMatchingTitle(
					(List) mediaLibraryWrapper.getBooks(), deleteText);
			deleteObjectWithMatchingTitle(
					(List) mediaLibraryWrapper.getSongs(), deleteText);
			deleteObjectWithMatchingTitle(
					(List) mediaLibraryWrapper.getVideos(), deleteText);
			deleteObjectWithMatchingTitle(
					(List) mediaLibraryWrapper.getVideogames(), deleteText);

			System.out.println("Removed from all media type from library");
			System.out.println(mediaLibraryWrapper.toString());
		}

	}

	/**
	 * Method scans through a List of media and deletes objects with titles
	 * matching the delete text.
	 * Author: SS
	 * - This is a list of media (Song, Book, Video, VideoGames)
	 *	deleteText
	 * - Any Media with title that matches this text is removed from
	 *   the list
	 */
	private void deleteObjectWithMatchingTitle(List<Media> list,
			String deleteText) {
		String compareTitleToText = deleteText.trim().toLowerCase();
		int compareTitleToTextLength = compareTitleToText.length();
		String lowCaseTitleSubString;
		String lowCaseTitleString;
		int titleLength;
		boolean perfectMatchFound = false;
		boolean matchFound = false;

		for (int i = list.size() - 1; i >= 0; i--) {
			lowCaseTitleString = list.get(i).getTitle().trim().toLowerCase();

			titleLength = lowCaseTitleString.length();
			if (titleLength < compareTitleToTextLength) {
				lowCaseTitleSubString = lowCaseTitleString; // No match and no
															// perfect match
			} else { // come here if title is longer or equal length to search
						// text.
				if (lowCaseTitleString.equals(compareTitleToText)) {
					perfectMatchFound = true;

					list.remove(i);
				}
				lowCaseTitleSubString = lowCaseTitleString.substring(0,
						compareTitleToTextLength);
				if (lowCaseTitleSubString.equals(compareTitleToText)) {
					matchFound = true;
				}
			}

		}
		
		if (matchFound && !perfectMatchFound) {
			for (int i = list.size() - 1; i >= 0; i--) {

				lowCaseTitleString = list.get(i).getTitle().trim()
						.toLowerCase();
				titleLength = lowCaseTitleString.length();
				if (titleLength > compareTitleToTextLength) {
					lowCaseTitleSubString = lowCaseTitleString.substring(0,
							compareTitleToTextLength);
					if (lowCaseTitleSubString.equals(compareTitleToText)) {

						list.remove(i);
					}
				}
			}
		}
	}

	/**
	 * Removes a media object and sorts the remaining list
	 * Author: SS
	 * V2: DG           
	 */
	public void removeMediaWithSortByTitle(Media media) {
		removeFromMediaWrapper(media);
		sortByTitle(media.getType());
		notifyObservers(mediaLibraryWrapper);

	}

	/**
	 * Removes a media objects that begin with the search String as part of a
	 * delete function. Remaining media is sorted:
	 * Author: SS
	 * V2: DG - Added searchString to the delete method
	 */
	public void removeMediaWithSortByTitle(Media media, String searchString) {

		System.out.println("media.getClass = \n" + media.getClass());
		removeFromMediaWrapper(media, searchString, false); // Shmuel added
															// search text
		// for delete function
		sortByTitle(media.getType());
		notifyObservers(mediaLibraryWrapper);

	}

	/**
	 * This method is called when the delete function has to take place for all
	 * four media types. Removes a media objects that begin with the search
	 * String as part of a delete function. Remaining media is sorted
	   Author: SS
	 * V2: DG - Added searchString to the delete method
	 */
	public void removeMediaWithSortByTitle(Media media, String searchString,
			boolean allMediaFlag) {
		if (allMediaFlag) {
			removeFromMediaWrapper(media, searchString, allMediaFlag);
			sortByTitle(media.getType());
			notifyObservers(mediaLibraryWrapper);
		}

	}

	/**
	 * Method returns a library object with all of the media elements of a
	 * certain type 
	 * Author: SS
	 */
	public MediaLibrary getLibraryByType(String mediaType) {
		MediaLibrary tempLibrary = new MediaLibrary();
		return tempLibrary;
	}

	/**
	 * Method returns library object with all of the media elements of a certain
	 * title. Author: SS
	 * 
	 */
	public MediaLibrary getLibraryByTitle(String mediaTitle) {
		MediaLibrary tempLibrary = new MediaLibrary();

		return tempLibrary;
	}

	/**
	 * Method returns a printable string containing all of the elements in the
	 * library 
	 * Author: SS
	 * 
	 */

	public String toString() {
		return mediaLibraryWrapper.toString();
	}

	public String toStringSongs() {
		return mediaLibraryWrapper.toStringSongs();
	}

	public String toStringBooks() {
		return mediaLibraryWrapper.toStringBooks();
	}

	public String toStringVideos() {
		return mediaLibraryWrapper.toStringVideos();
	}

	public String toStringVideoGames() {
		return mediaLibraryWrapper.toStringVideoGames();
	}

	/**
	 * Method deletes entries with the specified media type and media title from
	 * the media library 
	 * Author: DG
	 */

	public void deleteEntries(String mediaType, String mediaTitle) {

		System.out.println("Deleted specific entries from the media library");
	}

	/**
	 * The is a method of the class Observable that allows the model to be
	 * observed.
	 * Author: DG & DS
	 */
	public void addObserver(Observer observe) {
		observers.add(observe);
	}

	/**
	 * This method notifies the observer view object to call update and show the
	 * result in the output textField 
	 * Author: DG
	 */
	public void notifyObservers(Object mediaLibraryWrapper) {
		for (Observer observer : observers) {
			observer.update(this, this.mediaLibraryWrapper);
		}
		clearChanged();
	}

	/**
	 * non parameterized notifyObserver method
	 * Author: DG
	 */
	public void notifyObservers() {
		this.notifyObservers(mediaLibraryWrapper);
	}

	/**
	 * This is a sort method that sorts a mediaLibrary object by title and type
	 * Author: DS
	 * V2: DG 
	 */
	public void sortByTitle(Type type) {
		if (type.equals(Type.Book)) {
			Collections.sort(mediaLibraryWrapper.getBooks(),
					new Comparator<Book>() {
						@Override
						public int compare(Book book1, Book book2) {
							return book1.getTitle().toLowerCase()
									.compareTo(book2.getTitle().toLowerCase());
						}

					});
		} else if (type.equals(Type.Song)) {
			Collections.sort(mediaLibraryWrapper.getSongs(),
					new Comparator<Song>() {
						@Override
						public int compare(Song song1, Song song2) {
							return song1.getTitle().toLowerCase()
									.compareTo(song2.getTitle().toLowerCase());
						}

					});

		} else if (type.equals(Type.Video)) {
			Collections.sort(mediaLibraryWrapper.getVideos(),
					new Comparator<Video>() {
						@Override
						public int compare(Video video1, Video video2) {
							return video1.getTitle().toLowerCase()
									.compareTo(video2.getTitle().toLowerCase());
						}

					});

		} else if (type.equals(Type.VideoGame)) {
			Collections.sort(mediaLibraryWrapper.getVideogames(),
					new Comparator<VideoGame>() {
						@Override
						public int compare(VideoGame videoGame1,
								VideoGame videoGame2) {
							return videoGame1
									.getTitle()
									.toLowerCase()
									.compareTo(
											videoGame2.getTitle().toLowerCase());
						}

					});

		}

	}
	/**
	 * This method deletes the entire media library
	 * Author: DS
	 * V2: DG 
	 */
	public void deleteMediaLibrary() {
		mediaLibraryWrapper = new MediaLibraryWrapper();
		notifyObservers();
	}
}