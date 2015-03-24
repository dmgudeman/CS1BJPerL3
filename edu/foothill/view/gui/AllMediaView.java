package edu.foothill.view.gui;

// import packages
import edu.foothill.view.gui.SongView;

import edu.foothill.controller.gui.ViewEvent;
import edu.foothill.controller.gui.ViewListener;
import edu.foothill.model.Command;
import edu.foothill.model.Media;
import edu.foothill.model.MediaLibraryWrapper;

//import java.awt
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

// import javax.swing
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * This class creates a JFrame to takes search Text and search the VideoGame
 * Class. When a button is activates it navigates to a JFrame that is specific
 * to a media type or a JFrame that addresses the entire list. 
 * Author: DG
 * Version 2: SS - added the search and Delete functions for multiple media objects & types
 */
public class AllMediaView extends JFrame {
	private static final long serialVersionUID = 1L;

	private MediaLibraryWrapper mediaLibraryWrapper;
	private MediaView mediaView;
	// set constants for the frame
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 700;

	// initialize the elements in the panel
	private JPanel panel;
	private JTextField search;
	private JButton homeButton;
	private JButton deleteButton;
	private JLabel prompt;
	private JButton printButton;
	private JButton exitButton;
	private JButton deleteEntireLibraryButton;

	private JTextArea textArea;

	private Media matchedMedia;
	private String searchString;

	/**
	 * Non parameterized constructor for this class, creates a JFrame and places
	 * buttons in the frame
	 * Author: DG
	 * V2: SS          
	 */
	public AllMediaView(MediaView mediaView, ViewListener controller) {
		// removes the buttons from the standard upper left area to force the
		// user to use the exit button to close, ensuring saving of the data
		// Author: DS & DG
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		// mediaView object needed to be able to set invisible
		this.mediaView = mediaView;
		// creates frame
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);

		// create a panel
		panel = new JPanel(new GridBagLayout());
		Color myYellowMedium = new Color(220, 255, 255, 220);
		panel.setBackground(myYellowMedium);

		// declares the elements in the panel
		prompt = new JLabel("My Library");
		prompt.setFont(new Font("Geneva", Font.ROMAN_BASELINE, 30));
		search = new JTextField("Search for a media here", 20);

		deleteButton = new JButton("DELETE THE SONG IN SEARCH BAR");
		deleteButton.setEnabled(false);

		textArea = new JTextArea("After a search your songs will\nappear here",
				15, 30);
		textArea.setEditable(false);

		homeButton = new JButton("HOME");
		printButton = new JButton("PRINT");
		exitButton = new JButton("EXIT");
		deleteEntireLibraryButton = new JButton(
				"Delete entire Library (CAREFUL)");

		// makes an object of constraints to allow the grid layout DG
		GridBagConstraints c = new GridBagConstraints();

		// sets the distance between elements DG
		c.insets = new Insets(10, 10, 10, 10);

		// adds the initialized elements to the frame DG
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(prompt, c);

		c.gridx = 0;
		c.gridy = 1;
		panel.add(search, c);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(textArea, c);

		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 20;
		panel.add(printButton, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		panel.add(deleteButton, c);

		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 0.5;
		c.gridwidth = 1;
		panel.add(homeButton, c);

		c.gridx = 1;
		c.gridy = 5;
		panel.add(exitButton, c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		panel.add(deleteEntireLibraryButton, c);

		this.add(panel);

		// makes frame visible and exits on close DG
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		/**
		 * Add Action Listeners to Buttons, uses anonymous classes to add each
		 * Listener. Action performed methods are within each class. Action
		 * performed methods perform functions of printing etc., and also adjust
		 * the visibility of the procession of GUIs when navigating between
		 * GUIs. Gudeman
		 */

		// variable to be able to specify the songView within the anonymous
		// classes DG
		final AllMediaView SELF = this;

		// add ActionListeners to the various buttons

		// ActionListener added to HOME button DG
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						mediaView.setVisible(true);
						SELF.setVisible(false);
					}
				});
			}
		});
		// ActionListener added to PRINT button DG
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// this utilizes the mechanism of the AddSubViewSong without
				// duplicating code
				controller.viewEventOccured(new ViewEvent(AllMediaView.class,
						null, Command.PRINT));

			}
		});
		// ActionListener added to EXIT button DG
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				controller.viewEventOccured(new ViewEvent(SongView.class, null,
						Command.SAVE));
				SELF.dispatchEvent(new WindowEvent(SELF,
						WindowEvent.WINDOW_CLOSING));
			}
		});
		deleteEntireLibraryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				controller.viewEventOccured(new ViewEvent(SongView.class, null,
						Command.DELETE_LIBRARY));
				// textArea.setText("");
				search.setText("Your Library is Empty");

			}
		});

		/**
		 * implements keylisteners to the search bar to active delete button
		 * upon a title match in the search bar. Gudeman and Scottilini
		 */
		search.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// to complete interface
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// to complete interface

			}

			// enables the delete button when there
			// is a match in the search bar to the
			// mediaWrapper arrayList
			@Override
			public void keyReleased(KeyEvent e) {
				if (searchHelper(search.getText())) {
					deleteButton.setEnabled(true);
				} else {
					deleteButton.setEnabled(false);
				}
			}

		});

		/**
		 * Add mouse Listener to the search bar and the delete button Gudeman
		 */
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				search.setText("");
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				// sends info to the controller of what to delete
				controller.viewEventOccured(new ViewEvent(AllMediaView.class,
						matchedMedia, Command.DELETE_WITH_SORT, searchString,
						true)); // SS Added searchString and newAllMediaFlag
								// parameter
				deleteButton.setEnabled(false);
				search.setText("");
			}
		});

	}

	/**
	 * A method to get the Data to repopulate the textArea box. Takes the
	 * parameter mediaLibraryWrapper. Gudeman
	 */
	public void setMediaLibraryWrapper(MediaLibraryWrapper mediaLibraryWrapper) {
		this.mediaLibraryWrapper = mediaLibraryWrapper;
		repopulateTextArea();

	}

	/**
	 * This method iterates through the song class and appends the title to a
	 * viewable output to display in the textArea box Gudeman
	 */
	private void repopulateTextArea() {
		textArea.setText("");

		for (Media media : mediaLibraryWrapper.getMedia()) {
			textArea.append(media.getTitle() + "\n");
		}
	}

	/**
	 * Method to manage the text in the search bar. text as a parameter examined
	 * to determine a match Gudeman
	 */
	
	/**
	 * This method iterates through the book class and appends all the titles
	 * that begin with the input string to the viewable output to display in the
	 * textArea box Shmuel
	 */
	private boolean repopulateMatchedTextArea(String textToMatch) {
		String text = textToMatch.trim();
		int textLength = text.length();
		int titleLength;
		String subTitle;
		String MatchingBooksString = "";
		String perfectMatchString = "";
		String allTitles = "";
		boolean perfectMatchFound = false;
		boolean matchFound = false;
		boolean returnMatch = false;

		String title;
		searchString = text.trim().toLowerCase();
		for (Media media : mediaLibraryWrapper.getMedia()) {
			this.matchedMedia = media; // **** IF SET TO null / book WE GET A
										// CRASH ***
			title = media.getTitle().trim();
			titleLength = title.length();
			allTitles += title + "\n";
			if (titleLength < textLength) {
				subTitle = title;
			} else { // come here if title is longer or equal length to search
						// text
				if (title.toLowerCase().equals(text.toLowerCase())) {
					perfectMatchFound = true;
					perfectMatchString += title + "\n";
					this.matchedMedia = media;
				}
				subTitle = title.substring(0, textLength);
				if (subTitle.toLowerCase().equals(text.toLowerCase())) {
					matchFound = true;
					MatchingBooksString += (media.getTitle() + "\n");
					this.matchedMedia = media;
					deleteButton.setEnabled(true);

				}
			}

		} // End of looping through all books in library

		if (perfectMatchFound) {
			textArea.setText(perfectMatchString);
			
			deleteButton.setEnabled(true);
			returnMatch = true;

		} else if (!perfectMatchFound && matchFound) {

			textArea.setText(MatchingBooksString);
			deleteButton.setEnabled(true);
			returnMatch = true;

		} else {
			textArea.setText(allTitles);
			this.matchedMedia = null;
			deleteButton.setEnabled(false);
			returnMatch = false;
		}
		textArea.repaint();

		return returnMatch;
	}

	/**
	 * Method to manage the text in the search bar. text as a parameter examined
	 * to determine a match 
	 * Author: DG & DS
	 *  Modified by SS for the new
	 * search functionality
	 */
	private boolean searchHelper(String text) {

		boolean matchFound = false;

		if (text.isEmpty()) {
			repopulateTextArea();
			matchFound = false;
		} else {
			matchFound = repopulateMatchedTextArea(text);

		}

		return matchFound;
	}

	/**
	 * Sets the text in the search bar and then calls the searchHelper method to
	 * determine how to proceed 
	 * Author: DG
	 * 
	 */
	public void setSearchText(String text) {
		this.search.setText(text);
		searchHelper(text);
	}

}
