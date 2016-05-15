package piskvorky;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class.
 * @author Ivan Kratochvíl
 */
public class Piskvorky extends Application {

    /**
     * Version of this program.
     */
    private final static String VERSION = "v1.0";
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Piškvorky");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/gfx/icon.png")));
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return version of this program
     */
    public static String getVersion() {
        return VERSION;
    }
    
    
    
}
