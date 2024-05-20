package org.example.java_final_project.Client.Controller;

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
import org.example.java_final_project.Client.Model.UserDAO;
import org.example.java_final_project.Main;
import org.example.java_final_project.Server.Controller.ServerThread;

import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class User_loginController implements Initializable {

    /*Text Filed*/
    @FXML
    private TextField User_text;
    @FXML
    private TextField pass;

    /*ImageView*/
    @FXML
    private ImageView close_eye;
    @FXML
    private ImageView open_eye;

    /*Button*/
    @FXML
    private Button login_button;
    @FXML
    private Button signUpButton;

    /*Other*/
    @FXML
    private PasswordField pass_hide;
    private Stage prevStage ;
    private String password;
    private UserDAO userDAO ;

    public void setPrevStage(Stage stage) { // Lấy dữ liệu từ stage cũ để chuyển stage mới
        this.prevStage = stage;
    }

    public void ShowPasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
        password = pass.getText() ;
        pass_hide.setText(password);
    }
    public void HidePasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
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
    private void changeScene() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("User-create-account.fxml"));

            Parent root = fxmlLoader.load();

            User_Sign_UpController signUpController = fxmlLoader.getController() ;
            signUpController.setPreSignInStage(prevStage);

            prevStage.getScene().setRoot(root);

            System.out.println("Dieu huong toi dang ki ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pass.setVisible(false);
        open_eye.setVisible(false);
        User_text.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());
        //Thêm sự kiện cho User_text
        //Observable -> đối tượng theo dõi  oldValue -> giá trị cũ  newValue -> giá trị mới
        // Hàm được gọi CheckFields() sẽ hoạt động khi User_text có dữ liệu mới
        pass.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());
        pass_hide.textProperty().addListener((observable, oldValue, newValue) -> CheckFields());
        //Dữ liệu sẽ
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
            };
        });
        login_button.setOnAction(e -> sendData());
    }

    private void CheckFields(){
        boolean isUsernameEmpty = User_text.getText().isEmpty() ;
        boolean isPasswordEmpty = pass.isVisible() ? pass.getText().isEmpty() : pass_hide.getText().isEmpty() ;
        login_button.setDisable(isPasswordEmpty||isUsernameEmpty);
        if(!isPasswordEmpty && !isUsernameEmpty){
            login_button.setDisable(false);
        }
    }
    private void sendData() {
        String username = User_text.getText().trim() ;
        String password = pass.getText().trim() ;
        //Xóa đi khoảng trắng nếu người dùng khi dùng ngôn ngữ ko phải là tiếng anh
        String request = "<Login>";
        new UserDAO(username,password,request) ;
    }


}