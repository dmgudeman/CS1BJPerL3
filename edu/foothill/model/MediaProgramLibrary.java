package edu.foothill.model;

// import package
import edu.foothill.controller.gui.Controller;
import edu.foothill.view.gui.MediaView;

/**
 * Object of this class runs the Media Library 
 * Author: SS
 * V2: DG
 * 
 */
public class MediaProgramLibrary {

	
	public static void main(String[] args) {
		MediaView view = new MediaView();
		MediaLibrary model = new MediaLibrary();

		// adds the view to be an observer
		model.addObserver(view);

		Controller controller = new Controller(model, view, "mediaLibrary.ser");
		
		// registers the controller to be a listener of the button activator on
		// the view.
		view.addController(controller);
		
		controller.loadFile();

	}

}