package edu.foothill.model;

// import java.io
import java.io.Serializable;

// import java.util
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Primary class to hold the data for the MediaLibrary. Wrapper class for data
 * is needed because in observer/Observable pattern, you cannot pass a list
 * (only an Object) so this class creates a wrapper to wrap lists in an object.
 * This creates ArrayLists for each of the four media types. 4 lists allows more
 * efficient searching of the various media groups. Faciliates populating the
 * search bar in the various views.
 * Author: DG
 */
public class MediaLibraryWrapper extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;

	// creates four array lists based on media type. makes manipulation
	// in the media types as a group more efficient for sorting and presenting
	// and populating the textAreas in the the various 4 media type GUIs. 

	private List<Song> songs = new ArrayList<Song>();
	private List<Book> books = new ArrayList<Book>();
	private List<Video> videos = new ArrayList<Video>();
	private List<VideoGame> videogames = new ArrayList<VideoGame>();

	// getter for the various Lists Author: DG

	public List<Song> getSongs() {
		return songs;
	}

	public List<Book> getBooks() {
		return books;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public List<VideoGame> getVideogames() {
		return videogames;
	}

	// toString function for entire library Author: DG
	public String toString() {
		String libraryString = "";
		libraryString += toStringBooks() + "\n" + toStringSongs() + "\n"
				+ toStringVideos() + "\n" + toStringVideoGames() + "\n";
		return libraryString;
	}

	/**
	 * A getter for a list of all Media Author: DG
	 */
	public List<Media> getMedia() {
		List<Media> allMedia = new ArrayList<Media>();

		for (Song song : songs) {
			allMedia.add(song);
		}
		for (Book book : books) {
			allMedia.add(book);
		}
		for (Video video : videos) {
			allMedia.add(video);
		}
		for (VideoGame videoGame : videogames) {
			allMedia.add(videoGame);
		}
		return allMedia;

	}

	// toString for Songs Author: DG
	public String toStringSongs() {
		String libraryString = "";
		if (songs.isEmpty()) {
			libraryString = "There are no songs";
		} else {
			libraryString = "Your Song library contains:" + "\n";

			for (int i = 0; i < songs.size(); i++) {
				libraryString += songs.get(i) + "\n";
			}
		}
		return (libraryString);
	}

	// toString for Books Author: DG
	public String toStringBooks() {
		String libraryString = "";
		if (books.isEmpty()) {
			libraryString = "There are no books";
		} else {
			libraryString = "Your Book library contains:" + "\n";

			for (int i = 0; i < books.size(); i++) {
				libraryString += books.get(i) + "\n";
			}
		}
		return (libraryString);
	}

	// toString for Videos Author: DG
	public String toStringVideos() {
		String libraryString = "";
		if (videos.isEmpty()) {
			libraryString = "There are no videos";
		} else {
			libraryString = "Your Video library contains:" + "\n";

			for (int i = 0; i < videos.size(); i++) {
				libraryString += videos.get(i) + "\n";
			}
		}
		return (libraryString);
	}

	// toString for VideoGames Author: DG
	public String toStringVideoGames() {
		String libraryString = "";
		if (videogames.isEmpty()) {
			libraryString = "There are no videogames";
		} else {
			libraryString = "Your VideoGame library contains:" + "\n";

			for (int i = 0; i < videogames.size(); i++) {
				libraryString += videogames.get(i) + "\n";
			}
		}
		return (libraryString);
	}
}
