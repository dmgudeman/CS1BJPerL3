package edu.foothill.model;

// import java.io
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This Class provides interface to the hard disk. The
 * class has methods for saving and retrieving the  whole media library to/from a disk. 
 * Author: SS
 */


public class Persistence {

	private String mediaLibraryFileName;
	private File mediaLibraryFile;
    private MediaLibraryWrapper mediaLibraryWrapperFromDisk;
	/**
	 * Non-parameterized constructor for the Disk Class. Note that if you do not
	 * specify file name it defaults to Temp.ser
	 * Author: SS
	 */
	Persistence() {
		this("Temp.ser");
	}

	/**
	 * parameterized constructor for the Persistence Class.
	 * Author: SS
	 */

	public Persistence(String diskFileName) {
		mediaLibraryFileName = diskFileName;
		mediaLibraryFile = new File(mediaLibraryFileName);

	}

	/**
	 * The writeToDisk method gets the MediaLibrary Object as an input parameter
	 * and overrides the existing (if any) file with a new library file. The
	 * method returns true if successful, and false if an error occurs.
	 * 
	 * - mediaLibraryObject: media library object to be written to disk
	 * - Returns true if write operation was successful and false if it
	 *         failed.
	 * Author: SS
	 */
	public boolean writeToDisk(MediaLibraryWrapper mediaLibraryObject) { // changed S
		FileOutputStream fileOutput = null;
		ObjectOutputStream objectOutput = null;
		boolean successfulWriteToDisk=true;
		try {
			fileOutput = new FileOutputStream(mediaLibraryFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			successfulWriteToDisk = false;
		}
		try {
			objectOutput = new ObjectOutputStream(fileOutput);
		} catch (IOException e) {
			e.printStackTrace();
			successfulWriteToDisk = false;
		}
		try {
			objectOutput.writeObject(mediaLibraryObject);

		} catch (IOException e) {
			e.printStackTrace();
			successfulWriteToDisk = false;

		}
		return successfulWriteToDisk;
	}

	/**
	 * The readFromDisk method attempts to read an object from the disk. If the
	 * read operation fails, the method returns false. If the read operation
	 * succeeds the method returns true and populates the MediaLibrary object
	 * with the MediaLibrary object from the disk file.
	 * 
	 * Returns true if read is successful and false if operation failed.
	 * Author: SS 
	 * V2: DG changed to send data to the Wrapper class where arraylists are
	 *         
	 */
	public boolean readFromDisk() {
		ObjectInputStream objectInputStream = null;
		FileInputStream fileInput = null;
		boolean successfulReadFromDisk;
		if (mediaLibraryFile.exists()) {
			successfulReadFromDisk = true;
			try {
				fileInput = new FileInputStream(mediaLibraryFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				successfulReadFromDisk = false;
			}
			try {
				objectInputStream = new ObjectInputStream(fileInput);
			} catch (IOException e) {

				e.printStackTrace();
				successfulReadFromDisk = false;

			}
			try {
				
				// put data into the mediaWrapper class
				mediaLibraryWrapperFromDisk = (MediaLibraryWrapper) objectInputStream
						.readObject();
			} catch (IOException e) {

				e.printStackTrace();
				successfulReadFromDisk = false;

			} catch (ClassNotFoundException e) {

				e.printStackTrace();
				successfulReadFromDisk = false;

			}
		} else{
			successfulReadFromDisk = false;
		}
		return successfulReadFromDisk;
	}

	/**
	 * The method getObjectFromDiskFile returns the object which was read from
	 * the disk by the readFromDisk method. If the readFromDisk method was not
	 * successful, the method returns null .
	 * 
	 *  - Returns ContMediaLibrary object containing the media library object
	 *         which was read from the disk.
	 * Author: SS
	 */
	
	// getter for the file Author: SS
	public MediaLibraryWrapper getDiskFileObject() {
		if (!mediaLibraryFile.exists()) {
			mediaLibraryWrapperFromDisk = null;
		}
		return mediaLibraryWrapperFromDisk;
	}
	/**
	 * deletes the library from the disk
	 * Author: SS
	 * V2: DS
	 */
	 public void deleteLibrary() {
		boolean didDelete = mediaLibraryFile.delete();
	    System.out.println(didDelete);
		 
	 }
}
