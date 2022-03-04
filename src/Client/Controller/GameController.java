package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.Game.Game;
import Server.Game.GameRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class GameController {
    Game current=new Game();
    GameRecord gameRecord;

    @FXML
    TextField text;
    @FXML
    ListView<String> chat;
    @FXML
    GridPane board;
    @FXML
    ImageView background;
    private ImageView load(String url){
        ImageView imageView=new ImageView();
        Image image=new Image(url);
        imageView.setImage(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return  imageView;
    }
    public void add(ImageView imageView,int row,int column){
        board.add(imageView,column,row);
    }
    public void initialize(){
        current.black=Main.selectedUser;
        current.white=Main.user;
        current.startgmae();

        background.setImage(new Image("src/assets/chessboard.jpg"));

        ImageView blackrook=load("src/assets/blackrook.png");
        add(blackrook,0,0);
        ImageView blackrook2=load("src/assets/blackrook.png");
        add(blackrook2,0,7);

        ImageView blackhorse=load("src/assets/blackhorse.png");
        add(blackhorse,0,1);
        ImageView blackhorse2=load("src/assets/blackhorse.png");
        add(blackhorse2,0,6);

        ImageView blackbishop=load("src/assets/blackbishop.png");
        add(blackbishop,0,2);
        ImageView blackbishop2=load("src/assets/blackbishop.png");
        add(blackbishop2,0,5);

        ImageView blackqueen=load("src/assets/blackqueen.png");
        add(blackqueen,0,3);

        ImageView blackking=load("src/assets/blackking.png");
        add(blackking,0,4);

        ImageView whiterook=load("src/assets/whiterook.png");
        add(whiterook,7,0);
        ImageView whiterook2=load("src/assets/whiterook.png");
        add(whiterook2,7,7);

        ImageView whitehorse=load("src/assets/whitehorse.png");
        add(whitehorse,7,1);
        ImageView whitehorse2=load("src/assets/whitehorse.png");
        add(whitehorse2,7,6);

        ImageView whitebishop=load("src/assets/whitebishop.png");
        add(whitebishop,7,2);
        ImageView whitebishop2=load("src/assets/whitebishop.png");
        add(whitebishop2,7,5);

        ImageView whitequeen=load("src/assets/whitequeen.png");
        add(whitequeen,7,4);

        ImageView whiteking=load("src/assets/whiteking.png");
        add(whiteking,7,3);

        ImageView[] blackpawn=new ImageView[8];
        ImageView[] whitepawn=new ImageView[8];

        for (int i = 0; i < 7; i++) {
            blackpawn[i]=load("src/assets/blackpawn.png");
            add(blackpawn[i],1,i);
            whitepawn[i]=load("src/assets/whitepawn.png");
            add(whitepawn[i],6,i);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String resign = Main.in.readUTF();
                        if(resign.equals("resign")){
                            Main.user.wins++;
                            new PageLoader().load("../Mainmenu.fxml");
                        }
                    }
                }catch (Exception e){}
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        String recieve=Main.in.readUTF();
                        if(recieve!=null) {
                            String[] parsed = recieve.split(" ");
                            if(parsed[0].equals("chat")){
                                String full=parsed[2];
                                for (int i = 3; i <parsed.length ; i++) {
                                    full=full+" "+parsed[i];
                                }
                                chat.getItems().add(parsed[1]+": "+ full);
                            }
                        }

                    } catch (IOException e) {

                    }
                }
            }
        }).start();

    }
    public void send(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("chat "+ Main.user.name+" "+ Main.selectedUser.name + " "+text.getText());
    }

    public void resign(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("resign "+Main.user.name+" " +Main.selectedUser.name);
        Main.user.losses++;
        gameRecord=new GameRecord(Main.selectedUser,current);
        Main.out.writeObject(gameRecord);
        new PageLoader().load("../View/Mainmenu.fxml");
    }
}
