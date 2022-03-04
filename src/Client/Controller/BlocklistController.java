package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class BlocklistController {
    @FXML
    ListView<User> list;
    public void initialize(){
        for (int i = 0; i < Main.user.blocked.size(); i++) {
            list.getItems().add(Main.user.blocked.get(i));
        }
    }
    public void unblock(MouseEvent mouseEvent) throws IOException {
        Main.user.blocked.remove(list.getSelectionModel().getSelectedItem());
        Main.out.writeUTF("unblock "+Main.user.name+ " "+ list.getSelectionModel().getSelectedItem().name);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Mainmenu.fxml");
    }
}
