package edu.foothill.view.gui;

// import packages
import edu.foothill.controller.gui.ViewEvent;
import edu.foothill.controller.gui.ViewListener;
import edu.foothill.model.Command;
import edu.foothill.model.Media;
import edu.foothill.model.MediaLibrary;
import edu.foothill.model.MediaLibraryWrapper;

// import java.awt
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

// import javax
import javax.swing.*;


/**
 * This class creates a JFrame to function as the Welcome GUI from which GUI's
 * specific for each each media type are launched. When a button is activated
 * it navigates to a JFrame that is specific to a media type and carries the
 * text in the search bar forward to the next GUI. Version 1 Gudeman
 * Version 2: Scottolini
 */
public class MediaView extends JFrame implements Observer {

	
	// set constants for the frame
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;
	private static final long serialVersionUID = 1L;

	// initialize the elements in the frame

	private JPanel panel;

	private JLabel prompt;

	private JTextField mainSearch;

	private JButton mainAllButton;
	private JButton mainSongButton;
	private JButton mainBookButton;
	private JButton mainVideoButton;
	private JButton mainVideoGameButton;
	private JButton exitButton;

	private AllMediaView allMediaView;
	private SongView songView;
	private BookView bookView;
	private VideoView videoView;
	private VideoGameView videoGameView;

	private MediaLibraryWrapper mediaLibraryWrapper;

	/**
	 * Non parameterized constructor for this class, creates a JFrame and places
	 * buttons in the frame Gudeman
	 */
	public MediaView() {

		// removes the buttons from the standard upper left area to force the
		// user to use the exit button to close, ensuring saving of the data DG
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		// specifics for the JFrame of this class DG
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);

		// creates panel DG
		panel = new JPanel(new GridBagLayout());
		Color myNewMuaveHeavy = new Color(220, 255, 255, 220);
		panel.setBackground(myNewMuaveHeavy);

		// declares the elements in the frame DG
		mainSearch = new JTextField(
				"Search for a title and/or choose a media type", 25);
		prompt = new JLabel("My Media Library");
		prompt.setFont(new Font("Geneva", Font.ROMAN_BASELINE, 30));
		mainAllButton = new JButton("ALL MEDIA");
		mainSongButton = new JButton("SONG");
		mainVideoButton = new JButton("VIDEO");
		mainVideoGameButton = new JButton("VIDEOGAME");
		mainBookButton = new JButton("BOOK");
		exitButton = new JButton("EXIT");

		/**
		 * Uses Layout tool to position the elements in the panel Gudeman
		 */
		// creates an object to hold the gridBaglayout constraints DG
		GridBagConstraints c = new GridBagConstraints();

		// sets the distance between elements DG
		c.insets = new Insets(10, 10, 10, 10);

		// use GridBagLayout specifications to position the components DG
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;

		panel.add(prompt, c);

		c.gridx = 0;
		c.gridy = 1;
		panel.add(mainSearch, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.ipady = 20;
		panel.add(mainAllButton, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		panel.add(mainSongButton, c);

		c.gridx = 1;
		c.gridy = 3;
		panel.add(mainBookButton, c);

		c.gridx = 0;
		c.gridy = 4;
		panel.add(mainVideoButton, c);

		c.gridx = 1;
		c.gridy = 4;
		panel.add(mainVideoGameButton, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		panel.add(exitButton, c);

		// add layout to the panel
		this.add(panel);

		// completes the JFrame specifications
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method uses anonymous classes to Add ActionListeners to all the
	 * buttons. A media view SELF variable is utilized to identify the mediaView
	 * within the classes Gudeman
	 */
	public void addController(ViewListener controller) {
		// this declaration allows the view to identify itself when
		// calling making Action Listeners DG
		final MediaView SELF = this;

		// need to instantiate the next tier of views so the
		// controllers can be added
		songView = new SongView(SELF, controller);
		songView.setVisible(false);
		bookView = new BookView(SELF, controller);
		bookView.setVisible(false);
		videoView = new VideoView(SELF, controller);
		videoView.setVisible(false);
		videoGameView = new VideoGameView(SELF, controller);
		videoGameView.setVisible(false);
		allMediaView = new AllMediaView(SELF, controller);
		allMediaView.setVisible(false);
		

		/**
		 * Add action listeners. Utilizes anonymous classes to create new
		 * ActionListeners for each element. Uses Runnable interface within an
		 * anonymous class when needed to process through the GUIs Gudeman
		 */

		// instantiates the AllView and makes mediaView invisible
		// utilizes an anonymous class to add the action listener
		// and a Runnable object is needed to make a thread
		mainAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// use the visibility property to adjust the
				// procession of JFrame views
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						allMediaView.setVisible(true);
					}
				});
			}
		});
		// instantiates the SongView and makes mediaView invisible
		// utilizes an anonymous class to add the action listener
		// and a Runnable object is needed to make a thread DG
		mainSongButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						songView.setVisible(true);
						
						// populates the search bar in songView appropriately
						// if mediaView search bar is empty
						if (!mainSearch.getText().isEmpty()) {
							songView.setSearchText(mainSearch.getText());
						}
					}
				});
			}
		});
		/**
		 * instantiates the SongView and makes mediaView invisible utilizes an
		 * anonymous class to add the action listener and a Runnable object is
		 * needed to make a thread DG Shaffer and Gudeman
		 */
		mainBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						bookView.setVisible(true);

						// populates the search bar in songView appropriately
						// if mediaView search bar is empty
						if (!mainSearch.getText().isEmpty()) {
							bookView.setSearchText(mainSearch.getText());
						}
					}
				});
			}
		});
		/**
		 * instantiates the AllMediaView and makes mediaView invisible utilizes an
		 * anonymous class to add the action listener and a Runnable object is
		 * needed to make a thread DG Shaffer and Gudeman
		 */
		mainAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						allMediaView.setVisible(true);

						// populates the search bar in songView appropriately
						// if mediaView search bar is empty
						if (!mainSearch.getText().isEmpty()) {
							// allMediaView.setSearchText(mainSearch.getText());
						}
					}
				});
			}
		});
		/**
		 * instantiates the VideoView and makes mediaView invisible utilizes an
		 * anonymous class to add the action listener and a Runnable object is
		 * needed to make a thread DG Shaffer and Gudeman
		 */
		mainVideoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						videoView.setVisible(true);

						// populates the search bar in songView appropriately
						// if mediaView search bar is empty
						if (!mainSearch.getText().isEmpty()) {
							 videoView.setSearchText(mainSearch.getText());
						}
					}
				});
			}
		});

		/**
		 * instantiates the VideoGameView and makes mediaView invisible utilizes an
		 * anonymous class to add the action listener and a Runnable object is
		 * needed to make a thread DG Shaffer and Gudeman
		 */
		mainVideoGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SELF.setVisible(false);
						videoGameView.setVisible(true);

						// populates the search bar in songView appropriately
						// if mediaView search bar is empty
						if (!mainSearch.getText().isEmpty()) {
							videoGameView.setSearchText(mainSearch.getText());
						}
					}
				});
			}
		});
		// the exit button
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				controller.viewEventOccured(new ViewEvent(MediaView.class,
						(Media)null, Command.SAVE));
				SELF.dispatchEvent(new WindowEvent(SELF,
						WindowEvent.WINDOW_CLOSING));
			}
		});
		// empties the search Bar when mouse cursor enters it, so new text may
		// be typed DG
		mainSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				mainSearch.setText("");
			}
		});
	}

	/**
	 * This is a method necessary to implement the Observer interface. It takes
	 * two parameters o is the observable object and arg an argument passed to
	 * the notifyObservers method. This allows the mediaView to update the other
	 * views. Gudeman & Shaffer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MediaLibrary) {
			this.mediaLibraryWrapper = (MediaLibraryWrapper) arg;
			songView.setMediaLibraryWrapper(mediaLibraryWrapper);
			bookView.setMediaLibraryWrapper(mediaLibraryWrapper);
			videoView.setMediaLibraryWrapper(mediaLibraryWrapper);
			videoGameView.setMediaLibraryWrapper(mediaLibraryWrapper);
			allMediaView.setMediaLibraryWrapper(mediaLibraryWrapper);
		}
	}
}
