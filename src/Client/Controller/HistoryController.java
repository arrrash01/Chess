package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.Game.GameRecord;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HistoryController {
    @FXML
    ListView<GameRecord> list;
    @FXML
    TextArea text;
    public void initialize(){
        for (int i = 0; i < Main.user.gameRecordList.size(); i++) {
            list.getItems().add(Main.user.gameRecordList.get(i));
        }
    }

    public void show(MouseEvent mouseEvent) {
        GameRecord gamerecord=list.getSelectionModel().getSelectedItem();
        text.setText("winner: "+gamerecord.winner.name+"\n"+"time: "+gamerecord.date.toString());

    }

    public void menu(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Mainmenu.fxml");
    }
}
