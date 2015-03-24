package edu.foothill.view.gui;

// import packages
import edu.foothill.controller.gui.ViewEvent;
import edu.foothill.controller.gui.ViewListener;
import edu.foothill.model.Video;
import edu.foothill.model.Command;

// import java.awt
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//import javax
import javax.swing.*;


/**
 * This class creates a GUI JFrame to be able to add a video object. It has a
 * button to navigate back to the videoView and a button to print the list.
 * Author: DS
 * V2: DG
 */

public class AddSubViewVideo extends JFrame {
	
	

	// include the view needed to process through the UI tree
	private final VideoView videoView;

	// constants for the frame
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 600;
	private static final long serialVersionUID = 1L;

	// create a panel
	JPanel panel = new JPanel();

	// declare variables needed
	private JButton addButton;
	private JButton backButton;
	private JButton printButton;

	private JTextField title;
	private JTextField star;
	private JTextField format;
	private JTextField location;
	private JTextArea notes;

	private JLabel videoPrompt;
	private JLabel titlePrompt;
	private JLabel starPrompt;
	private JLabel formatPrompt;
	private JLabel locationPrompt;
	private JLabel notesPrompt;

	/**
	 * Constructor for this class that takes a mediaView and a videoView as
	 * parameters. The mediaView is necessary so that visibility of the GUIs can
	 * be manipulated. 
	 * Author: DS
	 */
	public AddSubViewVideo(final VideoView videoView) {
		// the next two lines remove the buttons from the standard upper left
		// area to force the user to use the exit button to close, ensuring saving of
		// the data DS & DG
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		this.videoView = videoView;

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel(new GridBagLayout());
		Color myLimeMedium = new Color(120, 196, 80, 196);
		panel.setBackground(myLimeMedium);

		// declares the elements in the frame DS
		videoPrompt = new JLabel("Add a Video");
		videoPrompt.setFont(new Font("Geneva", Font.ROMAN_BASELINE, 30));
		titlePrompt = new JLabel("*Enter title:");
		titlePrompt.setFont(new Font("Arial", Font.BOLD, 14));
		titlePrompt.setForeground(Color.red);
		starPrompt = new JLabel("Enter star:");
		formatPrompt = new JLabel("Enter format:");
		locationPrompt = new JLabel("Enter location: ");
		notesPrompt = new JLabel("Enter notes:");

		title = new JTextField();
		star = new JTextField();
		format = new JTextField();
		location = new JTextField();
		notes = new JTextArea(5, 20);

		addButton = new JButton("Add");
		addButton.setEnabled(false);
		backButton = new JButton("Back");
		printButton = new JButton("Print");

		// makes frame visible and exits on close DS
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		// declare and instantiate an object to hold the GridBagConstraints DS
		GridBagConstraints c = new GridBagConstraints();

		// sets the distance between elements DS
		c.insets = new Insets(10, 10, 0, 0);

		// formats the elements on the gridBagLayout and adds the initialized
		// elements DS
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(videoPrompt, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(titlePrompt, c);

		c.gridx = 1;
		c.gridy = 1;
		panel.add(title, c);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(starPrompt, c);

		c.gridx = 1;
		c.gridy = 2;
		panel.add(star, c);

		c.gridx = 0;
		c.gridy = 4;
		panel.add(formatPrompt, c);

		c.gridx = 1;
		c.gridy = 4;
		panel.add(format, c);

		c.gridx = 0;
		c.gridy = 5;
		panel.add(locationPrompt, c);

		c.gridx = 1;
		c.gridy = 5;
		panel.add(location, c);

		c.gridx = 0;
		c.gridy = 6;
		panel.add(notesPrompt, c);

		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(notes, c);

		c.gridx = 0;
		c.gridy = 8;
		panel.add(addButton, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		panel.add(backButton, c);

		c.gridx = 1;
		c.gridy = 9;
		panel.add(printButton, c);

		this.add(panel);
	}

	/**
	 * Add Action Listeners to Buttons, uses anonymous classes to add each
	 * Listener. Action performed methods are within each class. Action
	 * performed methods perform functions of printing etc. and also adjust the
	 * visibility of the procession of GUIs when navigating between GUIs.
	 * Author: DG & DS
	 */
	public void addController(ViewListener controller) {

		// variable to be able to specify the addSubViewVideo within the
		// anonymous classes DS & DG
		final AddSubViewVideo self = this;

		// ActionListener added to ADD button DS & DG 
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Add Video Button has been clicked");

				Video video = new Video(title.getText(), location.getText(),
					format.getText(), notes.getText(), star.getText());
				controller.viewEventOccured(new ViewEvent(AddSubViewVideo.class,
						video, Command.ADD_WITH_SORT));
				clearTextFields();
				addButton.setEnabled(false);
			}
		});
		// ActionListener added to PRINT button DS & DG
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Print Button has been clicked");

				controller.viewEventOccured(new ViewEvent(AddSubViewVideo.class,
						(Video)null, Command.PRINT_VIDEOS));

			}
		});
		// ActionListener added to BACK button DS & DG
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Back Button has been clicked");
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						self.setVisible(false);
						videoView.setVisible(true);
					}
				});
			}
		});
		// implements keylisteners to the title text Field to active delete button
		// upon a title match in the search bar. DS & DG
		title.addKeyListener(new KeyListener() {

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
				if (!title.getText().trim().isEmpty()) {
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}
			}

		});

	}
	/**
	 * Helper method to clear text fields
	 * Author: DS & DG
	 */

	private void clearTextFields() {
		title.setText("");
		star.setText("");
		location.setText("");
		format.setText("");
		notes.setText("");
	}

}
