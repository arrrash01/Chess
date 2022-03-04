package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChallengeAndBlockController {
    @FXML
    Text text;
    public void initialize(){
        text.setText(Main.selectedUser.name+" :");
    }
    public void challenge(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("challenge "+ Main.user.name+" "+ Main.selectedUser.name);
        new PageLoader().load("Game.fxml");
    }

    public void block(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("block "+ Main.user.name +" "+ Main.selectedUser);
        Main.user.blocked.add(Main.selectedUser);
        new PageLoader().load("../View/challenge.fxml");
    }
}
