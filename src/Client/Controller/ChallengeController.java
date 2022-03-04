package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ChallengeController {
    ArrayList<User> online=new ArrayList<>();
    @FXML
    TextField searchbar;
    @FXML
    ListView<User> list;
    public void initialize() throws IOException, ClassNotFoundException {
        Main.out.writeUTF("online");
        Object onlin=null;
        while(!(onlin instanceof ArrayList)){
            onlin=Main.in.readObject();
        }
        online=(ArrayList<User>) onlin;
        for (int i = 0; i <online.size() ; i++) {
            if(!(online.get(i).name.equals(Main.user.name)) && !(Main.user.blocked.contains(online.get(i))))
                list.getItems().add(online.get(i));
        }
    }
    public void challenge(MouseEvent mouseEvent) throws IOException {
        User selected=list.getSelectionModel().getSelectedItem();
        new PageLoader().load("../challengeAndBlock.fxml");
        Main.selectedUser=selected;

    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Mainmenu.fxml");
    }

    public void search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if(!(searchbar.getText().isEmpty())) {
            list.getItems().removeAll();
            for (int i = 0; i < online.size(); i++) {
                if (online.get(i).name.startsWith(searchbar.getText()) && !(online.get(i).name.equals(Main.user.name)))
                    list.getItems().add(online.get(i));
            }
        }
        else{
            initialize();
        }
    }
}
