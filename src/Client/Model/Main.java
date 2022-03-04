package Client.Model;
import Server.User.User;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class Main extends Application {
    public static User selectedUser;
    public Socket socket;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
    public static User user;
    public Main() throws IOException {
    }

    @Override
    public void init(){
        try {
            socket = new Socket("localhost", 8888);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("connected to server");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try{
                            String challenge=in.readUTF();
                            if(challenge.startsWith("challenge")){
                                String[] parse=challenge.split(" ");
                                Alert alert=new Alert(Alert.AlertType.INFORMATION,"new match from "+parse[1]);
                                alert.showAndWait();
                                new PageLoader().load("../View/Game.fxml");
                            }

                        }catch (Exception e){}
                    }
                }
            }).start();

        }catch (Exception e){
            System.out.println("Connection failed");
        }
    }
    @Override
    public void stop(){
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        new PageLoader().load("../View/LoginPage.fxml");
    }
    public static void main(String[] args) {
        launch(args);

    }

}
