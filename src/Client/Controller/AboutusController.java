package Client.Controller;

import Client.Model.PageLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AboutusController {
    public void menu(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Mainmenu.fxml");
    }
}
