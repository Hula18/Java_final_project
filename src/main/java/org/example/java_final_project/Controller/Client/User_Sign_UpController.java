package org.example.java_final_project.Controller.Client;

import javafx.application.Platform;
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
import org.example.java_final_project.Controller.Client.User_loginController;
import org.example.java_final_project.User;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class User_Sign_UpController implements Initializable {
    @FXML
    private ImageView Image_Password_closeEye;
    @FXML
    private ImageView Image_confirmPassword_closeEye;
    @FXML
    private ImageView Image_Password_openEye;
    @FXML
    private ImageView Image_confirmPassword_openEye;

    /*Password*/
    @FXML
    private PasswordField Pass_confirmPassword;
    @FXML
    private PasswordField Pass_password;

    /*TextField*/
    @FXML
    private TextField Pass_text_confirmPassword;
    @FXML
    private TextField Pass_text_password;
    @FXML
    private TextField Text_email;
    @FXML
    private TextField Text_user;

    /*Button*/
    @FXML
    private Button back_button;
    @FXML
    private Button login_button;
    @FXML
    private Button signUp_button;

    private Stage preSignInStage ;

    public void setPreSignInStage(Stage preSignInStage) {
        this.preSignInStage = preSignInStage;
    }

    String password,confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pass_text_confirmPassword.setVisible(false);
        Pass_text_password.setVisible(false);
        Image_confirmPassword_openEye.setVisible(false);
        Image_Password_openEye.setVisible(false);
        login_button.setOnAction(e->backToSignIn());
    }

    private void backToSignIn() {
        try{
            Runnable openSignIn = new Runnable() {
                @Override
                public void run() {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("User.fxml"));
                        Parent root = fxmlLoader.load();

                        User_loginController loginController = fxmlLoader.getController() ;
                        loginController.setPrevStage(preSignInStage); // Thay đổi scene của stage chính khi cần

                        preSignInStage.getScene().setRoot(root); // Thay đổi root luôn có nghĩa là như một anchar pane mà nó visiable = false
                        System.out.println("Chuyen ve login ");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            openSignIn.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void ShowPasswordOnAction(KeyEvent event) {
        /* Cái textfield nhận dữ liệu từ passwordField */
        password = Pass_text_password.getText() ;
        Pass_password.setText(password);
    }
    @FXML
    void HidePasswordOnAction(KeyEvent event) {
        /* Cái passwordfield nhận dữ liệu từ cái textfield*/
        password = Pass_password.getText();
        Pass_text_password.setText(password);
    }
    @FXML
    void Open_Eye_OnAction(MouseEvent event) {
        /*Lúc mắt đóng*/ /*When eye close*/
        Pass_password.setVisible(true);
        Image_Password_closeEye.setVisible(true);
        Pass_text_password.setVisible(false);
        Image_Password_openEye.setVisible(false);
    }
    @FXML
    void close_eye_OnAction(MouseEvent event) {
        /*Lúc mắt mở*/ /*When eye open*/
        Pass_password.setVisible(false);
        Image_Password_closeEye.setVisible(false);
        Pass_text_password.setVisible(true);
        Image_Password_openEye.setVisible(true);

        Timer timer = new Timer() ;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_OnAction(null));
            }
        },1000);
    }


    @FXML
    void HideConfirmPasswordOnAction(KeyEvent event) {
         confirmPassword = Pass_confirmPassword.getText();
         Pass_text_confirmPassword.setText(confirmPassword);
    }
    @FXML
    void ShowConfirmPasswordOnAction(KeyEvent event) {
        confirmPassword = Pass_text_confirmPassword.getText();
        Pass_confirmPassword.setText(confirmPassword);
    }
    @FXML
    void Open_confirm_eye_OnAction(MouseEvent event) {
        /*Lúc mắt đóng*/ /*When eye close*/
        Pass_confirmPassword.setVisible(true);
        Image_confirmPassword_closeEye.setVisible(true);
        Pass_text_confirmPassword.setVisible(false);
        Image_confirmPassword_openEye.setVisible(false);
    }
    @FXML
    void close_confirm_eye_OnAction(MouseEvent event) {
        /*Lúc mắt mở*/ /*When eye open*/
        Pass_confirmPassword.setVisible(false);
        Image_confirmPassword_closeEye.setVisible(false);
        Pass_text_confirmPassword.setVisible(true);
        Image_confirmPassword_openEye.setVisible(true);

        Timer timmer = new Timer();
        timmer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_confirm_eye_OnAction(null));
            }
        },1000);
    }


}
