import gui.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application {
	private Stage window;
	private Menu menu;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		window = arg0;
		
		menu = new Menu(window);
		
		window.setScene(menu.getMenu());
		window.setTitle("Cell Civilization");
		window.show();
	}

}
