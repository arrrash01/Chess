package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
import Server.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class LoginPageController {

    @FXML
    TextField loguser;
    @FXML
    TextField logpass;
    @FXML
    TextField signuser;
    @FXML
    TextField signpass;
    @FXML
    ImageView profile;

    @FXML
    Text text;


    public void login(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Main.out.writeUTF("log "+loguser.getText()+" "+logpass.getText());
        Object reult=null;
        while(!(reult instanceof User)){
            reult=(Main.in.readObject());
        }
        User result=(User)reult;
        if(!result.name.equals(null)){
            Main.user=result;
            new PageLoader().load("../View/Mainmenu.fxml");
            return;
        }
        Alert alert=new Alert(Alert.AlertType.INFORMATION,"username and password do not match!");
        alert.showAndWait();
    }

    public void signup(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        if(signuser.getText().isEmpty() || signpass.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"All fields must be filled");
            alert.showAndWait();
            return;
        }
        Main.out.writeUTF("sign "+signuser.getText()+" "+signpass.getText());
        Object reult=null;
        while(true){
            reult=Main.in.readObject();
            if(reult!=null)
                break;
        }
        User result=(User) reult;
        if(!result.name.equals(null)){
            Main.user=result;
            if(profile.getImage()!=null){
                result.image=profile.getImage();
            }
            new PageLoader().load("../View/Mainmenu.fxml");
            return;
        }
        Alert alert=new Alert(Alert.AlertType.INFORMATION,"User already exists with this username pls change your username!");
        alert.showAndWait();
    }

    public void chooseprofile(MouseEvent mouseEvent) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(null);
        if(file!=null) {
            Image image = new Image(file.toURI().toString());
            profile.setImage(image);
            text.setVisible(false);
        }
    }
}
