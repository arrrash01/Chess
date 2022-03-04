package Client.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageLoader {
    public static Stage stage;

    public static void initStage(Stage primaryStage){
        stage=primaryStage;
        stage.setTitle("Chesster");
        stage.setHeight(600);
        stage.setWidth(1000);
    }
    public void load(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }
}
