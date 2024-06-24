package org.example.java_final_project.Client.Controller.Login_And_SignUp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.java_final_project.Client.Controller.Bank.ScreenController;
import org.example.java_final_project.Client.Controller.Handle.ClientCore;
import org.example.java_final_project.Client.Controller.Interface.LoginCallBack;
import org.example.java_final_project.Client.Controller.Interface.Screen_Interface;
import org.example.java_final_project.Main;
import org.example.java_final_project.Model.Request;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class User_loginController implements Initializable,LoginCallBack, Screen_Interface {

    /*Text Filed*/
    @FXML private TextField SDT_text;
    @FXML private TextField pass;

    /*ImageView*/
    @FXML private ImageView close_eye;
    @FXML private ImageView open_eye;

    /*Button*/
    @FXML private Button login_button;
    @FXML private Button signUpButton;

    /*Other*/
    @FXML private PasswordField pass_hide;
    @FXML private Label information;
    private Stage prevStage ;
    private String password;
    private String User_ID ;

    public void setPrevStage(Stage stage) { // Lấy dữ liệu từ stage cũ để chuyển stage mới
        this.prevStage = stage;
    }
    public String getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
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
    public void open_eye_OnAction(MouseEvent mouseEvent) { // khi vào vái mở mắt -> đóng mắt
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        information.setVisible(false);
        pass.setVisible(false);
        open_eye.setVisible(false);
        SDT_text.textProperty().addListener((observable, oldValue, newValue) -> {CheckFields();information.setVisible(false);});
        pass.textProperty().addListener((observable, oldValue, newValue) -> {CheckFields();information.setVisible(false);});
        pass_hide.textProperty().addListener((observable, oldValue, newValue) -> {CheckFields();information.setVisible(false);});
        signUpButton.setOnAction(e -> changeScene());
        login_button.setOnAction(e -> sendData());
    }



    private void CheckFields(){
        boolean isUsernameEmpty = SDT_text.getText().isEmpty() ;
        boolean isPasswordEmpty = pass.isVisible() ? pass.getText().isEmpty() : pass_hide.getText().isEmpty() ;
        if(!isPasswordEmpty && !isUsernameEmpty){
            login_button.setDisable(false);
        }else{
            login_button.setDisable(true);
        }
    }
    private void sendData() {
        String SDT = SDT_text.getText();
        String password = pass.getText();
        String request = Request.LOGIN;

        new ClientCore(SDT, password, request, (LoginCallBack) this);
    }
    private void changeScene() {
       Runnable scene = new Runnable() {
           @Override
           public void run() {
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
       };
       new Thread(scene).start();
    }
    private void changeToMainScene(String fullName , BigDecimal balance) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
                Parent root = fxmlLoader.load();
                ScreenController screenController = fxmlLoader.getController();
                screenController.setPrevStage(prevStage);
                screenController.setUserID(SDT_text.getText());
                screenController.setUserName(fullName);
                screenController.setBalance(balance); ;


                Scene scene2 = new Scene(root, 340, 564);
                prevStage.setScene(scene2);
                prevStage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("");
            }
        });
    }
// --------------------Interface ------------------------
    @Override
    public void onLoginSuccess() {
        information.setText("");
        setUser_ID(SDT_text.getText());
        String request = Request.Lay_Du_Lieu;
        new ClientCore(SDT_text.getText(),request ,(Screen_Interface) this) ;
    }
    @Override
    public void onLoginFailure(String message) {
        information.setVisible(true);
        information.setText(message);
    }

    @Override
    public void OnSignUpSuccess(String fullName , String SDT) {

    }

    @Override
    public void OnSignUpFailure(String message) {

    }

    @Override
    public void logOutSuccess() {

    }

    @Override
    public void GetUserNameSuccess(String accountName) {

    }

    @Override
    public void GetUseNameFail() {

    }

    @Override
    public void Change_Pin_Success() {

    }

    @Override
    public void Chang_Pin_failed() {

    }

    @Override
    public void UserIsAlreadyUsing() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setHeaderText(null);
        alert.setContentText("Tài khoản đang có người sử dụng vui lòng kiểm tra lại");
        alert.showAndWait();
    }

    @Override
    public void Check_Last_Password(String message) {

    }

    @Override
    public void Change_Password_Success() {

    }

    @Override
    public void Xac_Thuc_True() {

    }

    @Override
    public void Xac_Thuc_False() {

    }

    @Override
    public void Chuyen_Tien_Thanh_Cong() {

    }

    @Override
    public void Chuyen_Tien_Khong_Thanh_Cong() {

    }

    @Override
    public void Lay_Du_Lieu_Thanh_Cong(String fullName, BigDecimal balance) {
        changeToMainScene(fullName,balance);
    }

    @Override
    public void UpdateBalance(BigDecimal balance) {

    }
}