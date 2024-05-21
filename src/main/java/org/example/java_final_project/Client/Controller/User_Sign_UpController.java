package org.example.java_final_project.Client.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.java_final_project.Main;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_Sign_UpController implements Initializable {
    @FXML
    private ImageView Image_Password_closeEye;
    @FXML
    private ImageView Image_confirmPassword_closeEye;
    @FXML
    private ImageView Image_Password_openEye;
    @FXML
    private ImageView Image_confirmPassword_openEye;
    @FXML
    private ImageView checkIcon;
    @FXML
    private ImageView notEqual;

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

    /*Label*/
    @FXML
    private Label emailCheck;
    @FXML
    private Label equalCheck;

    private String password,confirmPassword;
    private Stage preSignInStage ;
    public void setPreSignInStage(Stage preSignInStage) {
        this.preSignInStage = preSignInStage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pass_text_confirmPassword.setVisible(false);
        Pass_text_password.setVisible(false);
        Image_confirmPassword_openEye.setVisible(false);
        Image_Password_openEye.setVisible(false);

        Text_user.textProperty().addListener(((observableValue, oldValue,newValue) ->CheckFieldS()));
        Text_email.textProperty().addListener(((observableValue, oldValue,newValue) ->{
            CheckFieldS(); //Kiểm tra có empty hay ko
            if(!CheckEmail(newValue)){
                emailCheck.setVisible(true);
                emailCheck.setText("Bạn cần nhập một email hợp lệ");
                checkIcon.setVisible(true);
            }else {
                checkIcon.setVisible(false);
                emailCheck.setVisible(false);
            }
        }));
        Pass_password.textProperty().addListener(((observableValue, oldValue,newValue) ->{
            CheckFieldS(); // Kiểm tra có empty hay ko
        }));
        Pass_confirmPassword.textProperty().addListener(((observableValue, oldValue,newValue) -> {
            CheckFieldS() ; // Kiểm tra có empty hay ko
            CheckPasswordFieldS();
        }));
        Pass_text_password.textProperty().addListener(((observableValue, oldValue,newValue) -> {
            CheckFieldS() ; // Kiểm tra có empty hay ko
        }));
        Pass_text_confirmPassword.textProperty().addListener(((observableValue, oldValue,newValue) ->{
            CheckFieldS() ; // Kiểm tra có empty hay ko
            CheckPasswordFieldS();
        }));

        login_button.setOnAction(e->backToSignIn());
        signUp_button.setOnAction(e->CreateAccount());
    }
/*Password*/
    @FXML
    void ShowPasswordOnAction(KeyEvent event) {
        CheckFieldS();
        /* Cái textfield nhận dữ liệu từ passwordField */
        password = Pass_text_password.getText() ;
        Pass_password.setText(password);
    }
    @FXML
    void HidePasswordOnAction(KeyEvent event) {
        CheckFieldS();
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

/*Confirm password*/
    @FXML
    void HideConfirmPasswordOnAction(KeyEvent event) {
        CheckFieldS();
         confirmPassword = Pass_confirmPassword.getText();
         Pass_text_confirmPassword.setText(confirmPassword);
    }
    @FXML
    void ShowConfirmPasswordOnAction(KeyEvent event) {
        CheckFieldS();
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

    private void CheckFieldS() {
        boolean isUsernameEmpty = Text_user.getText().isEmpty() ;
        boolean isGmailEmpty = Text_email.getText().isEmpty() ;
        boolean isPasswordEmpty = Pass_text_password.isVisible() ? Pass_text_password.getText().isEmpty() : Pass_password.getText().isEmpty()  ;
        boolean isConfirmPasswordEmpty = Pass_text_confirmPassword.isVisible()? Pass_text_confirmPassword.getText().isEmpty() : Pass_confirmPassword.getText().isEmpty() ;
        signUp_button.setDisable(isUsernameEmpty||isGmailEmpty||isPasswordEmpty||isConfirmPasswordEmpty);
        if(!isUsernameEmpty && !isGmailEmpty && !isConfirmPasswordEmpty && !isPasswordEmpty) {
            login_button.setDisable(false);
        }
    }
    private void CheckPasswordFieldS(){
       String Password = Pass_password.isVisible() ? Pass_password.getText() : Pass_text_password.getText() ;
       String confirmPassword = Pass_confirmPassword.isVisible() ? Pass_confirmPassword.getText() : Pass_text_password.getText();
       boolean checkEqual = CheckEqual(Password,confirmPassword) ;
       if(!checkEqual){
           notEqual.setVisible(true);
           equalCheck.setText("Mật khẩu không trùng khớp");
           equalCheck.setVisible(true);
       }else {
           notEqual.setVisible(false);
           equalCheck.setVisible(false);
       }
    }
    private boolean CheckEmail(String email){
        String regexPattern ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        /*
        * 1. Cho phép các ký tự số từ 0 đến 9
          2. Cho phép cả chữ hoa và chữ thường từ a đến z
          3. Cho phép các ký tự đặc biệt gạch dưới “_”, gạch nối “-” và dấu chấm “.”
          4. Dấu chấm không được phép ở đầu và cuối phần local part
          5. Các dấu chấm liền nhau sẽ vi phạm
          6. Đối với phần local part, số lượng ký tự đối ta là 64
        * VD:
        username@domain.com
        user.name@domain.com
        user-name@domain.com
        username@domain.co.in
        user_name@domain.com
        * */

        //Tạo đối tượng Pattern
        Pattern pattern  = Pattern.compile(regexPattern);

        //Tạo đối tượng Matcher
        Matcher matcher = pattern.matcher(email) ;

        return matcher.matches();
    }
    public boolean CheckEqual(String password , String confirmPassword){
        return confirmPassword.equals(password);
    }
    private void CreateAccount() {
        String username = Text_user.getText().trim() ;
        String email  = Text_email.getText();
        String password = Pass_password.isVisible() ? Pass_password.getText():Pass_text_password.getText();
        String confirmPassword = Pass_confirmPassword.isVisible() ? Pass_confirmPassword.getText() : Pass_text_password.getText() ;
        String request = "<Sign_Up>" ;
        /*Other*/
        Alert alert;
        if(!CheckEmail(email)) {
            alert = new Alert(Alert.AlertType.CONFIRMATION) ;
            alert.setContentText("Thông tin giá trị lỗi");
            alert.setHeaderText(null);
            alert.showAndWait() ;
        } else if (!CheckEqual(password,confirmPassword)) {
            alert = new Alert(Alert.AlertType.CONFIRMATION) ;
            alert.setContentText("Mật khẩu không trùng khớp");
            alert.setHeaderText(null);
            alert.showAndWait() ;
        }
        else {
            new ClientCore(username,email,password,request) ;
        }
    }
    private void backToSignIn() {
        try{
            Runnable openSignIn = new Runnable() {
                @Override
                public void run() {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("User.fxml"));
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
}
