package org.example.java_final_project.Client.Controller.Bank;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.java_final_project.Client.Controller.Handle.ClientCore;
import org.example.java_final_project.Client.Controller.Interface.LoginCallBack;
import org.example.java_final_project.Client.Controller.Login_And_SignUp.User_loginController;
import org.example.java_final_project.Main;
import org.example.java_final_project.Model.DAO.UserDAO;
import org.example.java_final_project.Model.Request;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenController implements Initializable,LoginCallBack {

    @FXML private AnchorPane main_menu;
    @FXML private AnchorPane expandedView;
    @FXML private AnchorPane initialView;
    @FXML private AnchorPane upper_infor;
    @FXML private AnchorPane user_pane;
    @FXML private AnchorPane change_Pass_Pane;
    @FXML private AnchorPane check_pane;

    @FXML private Label user_SDT;
    @FXML private Label user_name;
    @FXML private Label Money1;
    @FXML private Label Name_user;
    @FXML private Label SDT1;
    @FXML private Label TrueOrNot;

    @FXML private Button user_Home;
    @FXML private Button dangXuat;
    @FXML private Button Search;
    @FXML private Button User_Infor;
    @FXML private Button notification;
    @FXML private Button toggleButton;
    @FXML private Button ChuyenTien_button;
    @FXML private Button toggleButtonExpanded;
    @FXML private Button Move_back;
    @FXML private Button thaydoimatkhau;
    @FXML private Button Change_pass_send;

    @FXML private ImageView close_eye_1;
    @FXML private ImageView close_eye_2;
    @FXML private ImageView close_eye_3;

    @FXML private ImageView open_eye_1;
    @FXML private ImageView open_eye_2;
    @FXML private ImageView open_eye_3;

    @FXML private ImageView not_1;
    @FXML private ImageView not_2;
    @FXML private ImageView not_3;

    @FXML private ImageView yes_1;
    @FXML private ImageView yes_2;
    @FXML private ImageView yes_3;

    @FXML private TextField pass_text_1;
    @FXML private TextField pass_text_2;
    @FXML private TextField pass_text_3;

    @FXML private PasswordField password_1;
    @FXML private PasswordField password_2;
    @FXML private PasswordField password_3;

    private boolean isExpanded = false;
    private boolean isChange2 = false ;
    private boolean isChange1 = false;
    private Stage prevStage;
    private String Password1 ;
    private String Password2 ;
    private String Password3 ;
    private LoginCallBack loginCallBack ;
    private String userID;
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
        SDT1.setText(userID);
        user_SDT.setText(userID);
        DisplayUserName();
    }
    public Stage getPrevStage() {
        return prevStage;
    }
    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        upper_infor.setVisible(true);
        toggleButton.setVisible(true);
        initialView.setVisible(true);
        expandedView.setVisible(true);
        toggleButton.setOnAction(e -> handleToggle());
        toggleButtonExpanded.setOnAction(e -> handleToggle());
        expandedView.setTranslateY(-358);
        toggleButtonExpanded.setVisible(false);
        user_pane.setTranslateY(-564); // Position it below the visible area
        user_pane.setVisible(true); // Initially hidden
        change_Pass_Pane.setVisible(true);
        change_Pass_Pane.setTranslateX(+340);
        check_pane.setVisible(false);
        CheckFields();

        User_Infor.setOnAction(e -> newPane());
        user_Home.setOnAction(e -> changeToMainScreen());
        dangXuat.setOnAction(e -> LogOut());
        thaydoimatkhau.setOnAction(e -> ManHinhDoi());
        Move_back.setOnAction(e -> ManHinhDoi());


        password_2.textProperty().addListener((observable, oldValue, newValue) -> {
            Check(newValue);
        });
        password_2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(200), password_3);
            TranslateTransition transition1 = new TranslateTransition(Duration.millis(200), close_eye_3);
            if(newValue){
                check_pane.setVisible(true);
                transition.setToY(transition.getByY() + 68);
                transition.play();
                transition1.setToY(transition1.getByY() + 68);
                transition1.play();
            }else{
                check_pane.setVisible(false);
                transition.setToY(transition.getByY());
                transition.play();
                transition1.setToY(transition1.getByY());
                transition1.play();
            }
        });
        password_3.textProperty().addListener((observable, oldValue, newValue) -> {
            String password = (password_2.isVisible()) ? password_2.getText() : pass_text_2.getText();
            if(!newValue.equals(password)){
                TrueOrNot.setText("Không khớp vui lòng thử lại");
            }else{
                TrueOrNot.setText("");
                CheckFields();
            }
        });
    }

    private void ManHinhDoi() {
        if(isChange2){
            slidePaneToX(change_Pass_Pane,0);
            slidePaneToX(user_pane,-340);
        }else{
            slidePaneToX(change_Pass_Pane,+340);
            slidePaneToX(user_pane,0);
        }
        isChange2 = !isChange2;
    }
    private void LogOut() {
        String userID = getUserID() ;
        String request = Request.SignOff;
        new ClientCore(userID, request,this);
    }
    private void changeToMainScreen() {
        Platform.runLater(() -> {
            Timer timer = new Timer() ;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    slidePane(user_pane,+564);
                }
            },120);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    upper_infor.setVisible(true);
                    toggleButton.setVisible(true);
                    initialView.setVisible(true);
                    main_menu.setVisible(true);
                    expandedView.setVisible(true);
                }
            },220);
        });
    }
    private void handleToggle() {
        Platform.runLater(() -> {
            if (isExpanded) {
                // Slide up to collapse
                slidePane(expandedView, -358);
                slidePane(initialView, 0);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButtonExpanded.setVisible(false);
                        toggleButton.setVisible(true);
                    }
                }, 100);
            } else {
                // Slide down to expand
                slidePane(expandedView, 0);
                slidePane(initialView, 300);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toggleButtonExpanded.setVisible(true);
                        toggleButton.setVisible(false);
                    }
                }, 100);
            }
            isExpanded = !isExpanded;
        });
    }
    private void slidePane(AnchorPane pane, double toY) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), pane);
        transition.setToY(toY);
        transition.play();
    }
    private void slidePaneToX(AnchorPane pane,double toX){
        TranslateTransition transition = new TranslateTransition(Duration.millis(200),pane);
        transition.setToX(toX);
        transition.play();
    }
    private void DisplayUserName() {
        String fullName = UserDAO.ChangeName(userID);
        Name_user.setText(fullName);
        BigDecimal sdt = UserDAO.UpdateBalance(userID);
        Money1.setText(String.valueOf(sdt));
        user_name.setText(fullName);
    }
    private void newPane() {
        Platform.runLater(() -> {
            Timer timer = new Timer() ;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    initialView.setVisible(false);
                    expandedView.setVisible(false);
                    upper_infor.setVisible(false);
                    main_menu.setVisible(false);
                    toggleButton.setVisible(false);
                    toggleButtonExpanded.setVisible(false);
                    user_pane.setVisible(true);
                    user_pane.setTranslateY(564);
                }
            },250);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    user_pane.setVisible(true);
                    user_pane.setTranslateY(564); // Reset the position below the visible area
                    slidePane(user_pane, 0); // Slide to the center
                }
            },100);
         });
    }

    @Override public void onLoginSuccess() {
    }
    @Override public void onLoginFailure(String message) {

    }
    @Override public void OnSignUpSuccess() {}
    @Override public void OnSignUpFailure(String message) {}
    @Override public void logOutSuccess() {
        backToSignIn();
    }

    /*Chức năng*/
    // Đăng xuất
    private void backToSignIn() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("User.fxml"));
                Parent root = fxmlLoader.load();

                User_loginController loginController = fxmlLoader.getController();
                loginController.setPrevStage(prevStage);

                Scene scene = new Scene(root);
                prevStage.setScene(scene);
                prevStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML void HidePasswordOnAction_1(KeyEvent event) {
        Password1 = password_1.getText() ;
        pass_text_1.setText(Password1);
    }
    @FXML void HidePasswordOnAction_2(KeyEvent event) {
        Password2 = password_2.getText() ;
        pass_text_2.setText(Password2);
    }
    @FXML void HidePasswordOnAction_3(KeyEvent event) {
        Password3 = password_3.getText() ;
        pass_text_3.setText(Password3);
    }

    @FXML void ShowPasswordOnAction_1(KeyEvent event) {
        Password1 = pass_text_1.getText() ;
        password_1.setText(Password1);
    }
    @FXML void ShowPasswordOnAction_2(KeyEvent event) {
        Password2 = pass_text_2.getText() ;
        password_2.setText(Password2);
    }
    @FXML void ShowPasswordOnAction_3(KeyEvent event) {
        Password3 = pass_text_3.getText() ;
        password_3.setText(Password3);
    }

    @FXML void Close_Eye_ClickOnAction_1(MouseEvent event) {
        open_eye_1.setVisible(true);
        pass_text_1.setVisible(true);
        password_1.setVisible(false);
        close_eye_1.setVisible(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_ClickOnAction_1(null));
            }
        }, 1000);
    }
    @FXML void Close_Eye_ClickOnAction_2(MouseEvent event) {
        open_eye_2.setVisible(true);
        pass_text_2.setVisible(true);
        password_2.setVisible(false);
        close_eye_2.setVisible(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_ClickOnAction_2(null));
            }
        }, 1000);
    }
    @FXML void Close_Eye_ClickOnAction_3(MouseEvent event) {
        open_eye_3.setVisible(true);
        pass_text_3.setVisible(true);
        password_3.setVisible(false);
        close_eye_3.setVisible(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> Open_Eye_ClickOnAction_3(null));
            }
        }, 1000);
    }

    @FXML void Open_Eye_ClickOnAction_1(MouseEvent event) {
        open_eye_1.setVisible(false);
        pass_text_1.setVisible(false);
        password_1.setVisible(true);
        close_eye_1.setVisible(true);
    }
    @FXML void Open_Eye_ClickOnAction_2(MouseEvent event) {
        open_eye_2.setVisible(false);
        pass_text_2.setVisible(false);
        password_2.setVisible(true);
        close_eye_2.setVisible(true);
    }
    @FXML void Open_Eye_ClickOnAction_3(MouseEvent event) {
        open_eye_3.setVisible(false);
        pass_text_3.setVisible(false);
        password_3.setVisible(true);
        close_eye_3.setVisible(true);
    }

    public boolean Check(String password2){
        boolean length = password2.length()<8 ;
        boolean number = containsNumber(password2);
        boolean special = SpecialCharacter(password2);
        boolean Upper_value = containsUpperCase(password2);
        if(length){
            not_3.setVisible(true);
            yes_3.setVisible(false);
        }else {
            yes_3.setVisible(true);
            not_3.setVisible(false);
        }
        if(number || special){
            yes_2.setVisible(true);
            not_2.setVisible(false);
        } else{
            yes_2.setVisible(false);
            not_2.setVisible(true);
        }
        if(Upper_value){
            yes_1.setVisible(true);
            not_1.setVisible(false);
        }else{
            yes_1.setVisible(false);
            not_1.setVisible(true);
        }
        return length && (number || special) && Upper_value;
    }
    private boolean containsNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    private boolean SpecialCharacter(String str){
        String regexPattern = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";
        Pattern pattern = Pattern.compile(regexPattern) ;
        Matcher matcher = pattern.matcher(str);

        return matcher.find();
    }
    private boolean containsUpperCase(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void CheckFields(){
        boolean pass1 = password_1.getText().isEmpty() ;
        boolean pass2 = password_2.getText().isEmpty() ;
        boolean pass3 = password_3.getText().isEmpty() ;
        if(pass1 || pass2 || pass3){
            Change_pass_send.setDisable(true);
        }else{
            Change_pass_send.setDisable(false);
        }
    }
}
