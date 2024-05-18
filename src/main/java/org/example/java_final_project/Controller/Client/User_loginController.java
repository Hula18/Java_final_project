package org.example.java_final_project.Controller.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.java_final_project.User;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class User_loginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pass.setVisible(false);
        open_eye.setVisible(false);
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Runnable openSignUp = new Runnable() {
                    @Override
                    public void run() {
                        changeScene();
                    }
                };
                openSignUp.run();
            }

        });
    }
    // Xem mật khẩu
    public void ShowPasswordOnAction(KeyEvent keyEvent) {
        password = pass.getText() ;
        pass_hide.setText(password);
    }
    public void HidePasswordOnAction(KeyEvent keyEvent) {
        password = pass_hide.getText();
        pass.setText(password);
    }
    public void open_eye_OnAction(MouseEvent mouseEvent) { // Đóng mắt
        pass_hide.setVisible(true);
        close_eye.setVisible(true);
        pass.setVisible(false);
        open_eye.setVisible(false);
    }
    public void close_eye_OnAction(MouseEvent mouseEvent) { // mở mắt
        pass_hide.setVisible(false);
        close_eye.setVisible(false);
        pass.setVisible(true);
        open_eye.setVisible(true);

        // Start a timer to hide the password after 3 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> open_eye_OnAction(null));
            }
        }, 1000);
    }





    @FXML
    private TextField User_text;

    @FXML
    private ImageView close_eye;

    @FXML
    private Button login_button;

    @FXML
    private ImageView open_eye;

    @FXML
    private TextField pass;

    @FXML
    private PasswordField pass_hide;

    @FXML
    private Button signUpButton;


    private Stage prevStage ;
    private String password;

    public void setPrevStage(Stage stage) { // Lấy dữ liệu từ stage cũ để chuyển stage mới
        this.prevStage = stage;
    }
    private void changeScene() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("User-create-account.fxml"));

            Parent root = fxmlLoader.load();

            User_Sign_UpController signUpController = fxmlLoader.getController() ;
            signUpController.setPreSignInStage(prevStage);

            prevStage.getScene().setRoot(root);

            System.out.println("Dieu huong toi dang ki ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}