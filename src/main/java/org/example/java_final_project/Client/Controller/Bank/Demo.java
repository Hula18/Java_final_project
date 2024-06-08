package org.example.java_final_project.Client.Controller.Bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java_final_project.Client.Controller.Login_And_SignUp.User_loginController;
import org.example.java_final_project.Main;

import java.io.IOException;

public class Demo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(loader.load(),338, 564) ;
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
