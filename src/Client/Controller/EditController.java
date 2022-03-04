package Client.Controller;

import Client.Model.Main;
import Client.Model.PageLoader;
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

public class EditController {

    @FXML
    TextField pass;
    @FXML
    ImageView profile;
    @FXML
    Text text;
    @FXML
    public void initialize(){
        pass.setText(Main.user.password);
        if(Main.user.image!=null){
            profile.setImage(Main.user.image);
            text.setVisible(false);
        }
    }
    public void save(ActionEvent actionEvent) throws IOException {
        if(pass.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"All fields must be filled");
            alert.showAndWait();
            return;
        }
        Main.user.password=pass.getText();
        Main.user.image=profile.getImage();
        Main.out.writeUTF("edit " + Main.user.name+" "+pass.getText());
        new PageLoader().load("../View/Mainmenu.fxml");
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

    public void delete(ActionEvent actionEvent) throws IOException {
        Main.out.writeUTF("delete "+ Main.user.name);
        Main.user=null;
        new PageLoader().load("../View/LoginPage.fxml");
    }
}
