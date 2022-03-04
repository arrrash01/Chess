package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LeaderboardController {
    public void replace(User user1,User user2){
        User temp=new User(user1.name);
        temp.password=user1.password;
        temp.wins=user1.wins;
        temp.losses=user1.losses;
        temp.image=new Image(user1.image.getUrl());
        temp.gameRecordList=user1.gameRecordList;
        user1=user2;
        user2=temp;
    }
    @FXML
    ListView<String> list;
    public void initialize() throws IOException, ClassNotFoundException {
        Main.out.writeUTF("leaderboard");
        Object all=null;
        while(! (all instanceof List)){
            all=Main.in.readObject();
        }
        List<User> array=(CopyOnWriteArrayList<User>) all;
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j <array.size() ; j++) {
                if(array.get(i).wins>array.get(j).wins){
                    replace(array.get(i),array.get(j));
                }
                if(array.get(i).wins==array.get(j).wins && array.get(i).losses<array.get(j).losses)
                    replace(array.get(i),array.get(j));
            }
        }
        for (int i = 0; i < array.size(); i++) {
            list.getItems().add(array.get(i).name+", wins: "+array.get(i).wins+", losses: "+array.get(i).losses);
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("../View/Mainmenu.fxml");
    }
}
