package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainmenuController {
    public void history(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/history.fxml");
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/edit.fxml");
    }

    public void challenge(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/challenge.fxml");
    }

    public void out(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("out "+ Main.user.name);
        Main.user=null;
        new PageLoader().load("../View/LoginPage.fxml");
    }

    public void about(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/aboutus.fxml");
    }

    public void leader(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Leaderboard.fxml");
    }

    public void blocked(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/blocklist.fxml");
    }
}
