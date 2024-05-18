package org.example.java_final_project.Controller.Server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.java_final_project.Controller.Server.Server;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class serverController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Start_server_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (port_server.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hệ thống");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn chưa nhập PORT\nVui lòng nhập đầy đủ!");
                    alert.showAndWait();
                } else {
                    try {
                        port = port_server.getText() ;
                        if(isNumber(port)) {
                            if(Integer.parseInt(port) <= 65535){
                                status_Running.setVisible(true);
                                status_OFF.setVisible(false);
                                Start_server_button.setDisable(true);
                                End_server_button.setDisable(false);
                                serverCore = new Server(Integer.parseInt(port));
                                updateMessage("START SERVER ON PORT :"+port);
                            }else{
                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Lỗi");
                                alert.setHeaderText(null);
                                alert.setContentText("Port không được quá 65.535!\nVui lòng thử lại");
                                alert.showAndWait();
                            }
                        }else{
                            alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Thông báo");
                            alert.setHeaderText(null);
                            alert.setContentText("Port không đúng vui lòng nhập lại!");
                            alert.showAndWait() ;
                        }
                    } catch (Exception e) {
                        updateMessage("START ERROR");
                        e.printStackTrace();
                    }
                }
            }
        });
        End_server_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Thread close_server = new Thread(() -> {
                    try {
                        serverCore.stopServer();
                        updateMessage("STOP SERVER");
                        status_Running.setVisible(false);
                        status_OFF.setVisible(true);
                        Start_server_button.setDisable(false);
                        End_server_button.setDisable(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                close_server.start();
            }
        });
    }















































































    @FXML
    private Button End_server_button;

    @FXML
    private Button InforButton;

    @FXML
    private TextArea Message;

    @FXML
    private Label Number_Of_User;

    @FXML
    private Button Start_server_button;

    @FXML
    private TextField port_server;

    @FXML
    private Label status_OFF;

    @FXML
    private Label status_Running;

    @FXML
    private Button userButton;
    private Alert alert ;
    private static Server serverCore ;
    private String port ;


    public void updateMessage(String msg) {
        Message.appendText(msg+"\n");
    }
    public boolean isNumber(String n) {
        String regex = "^\\d{3,5}$"; /* một chuỗi có phải là số nguyên dương
                                     có từ 4 đến 8 chữ số hay không trong Java */

        // Tạo đối tượng Pattern
        Pattern pattern = Pattern.compile(regex) ;

        // Tạo đối tượng Mathcher
        Matcher matcher  = pattern.matcher(n) ;

        // Kiểm tra chuỗi có khớp với biểu thức chính quy
        return matcher.matches() ;
    }
}
