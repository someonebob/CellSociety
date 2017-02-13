package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Handles informing user of exceptions thrown to the GUI from the backend.
 * @author Nathaniel Brooke
 * @version 02-12-2017
 */
public class ExceptionHandler {

	/**
	 * Displays an alert with the message from the given Exception.
	 * @param e the Exception
	 */
	public ExceptionHandler(Exception e) {
		showAlert(e.getMessage());
	}
	
	/**
	 * Displays an alert with the specified message.
	 * @param message the error message
	 */
	public ExceptionHandler(String message) {
		showAlert(message);
	}
	
	/**
	 * Stops the program, and re-opens the starting menu.
	 */
	public void startOver(Stage window) {
		window.setScene(new Menu(window).initialize());
	}
	
	/**
	 * Exits the program, stops Java.
	 * Used only if error is completely fatal.
	 */
	public void exit() {
		System.exit(0);
	}
	
	private void showAlert(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(error);
		alert.showAndWait();
	}

}
