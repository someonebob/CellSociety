import gui.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of CellSociety, starts the window in which everything runs.
 * @author Jesse Yue
 * @version 1-30-2017
 */
public class Window extends Application {
	private Stage window;
	private Menu menu;

	/**
	 * Runs the entire program.
	 * @param args command line arguments, unused.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		window = arg0;
		
		menu = new Menu(window);
		
		window.setScene(menu.initialize());
		window.setTitle("Cell Civilization");
		window.show();
	}

}
