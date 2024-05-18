package org.example.java_final_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java_final_project.Controller.Client.User_loginController;

import java.io.IOException;

public class User extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(User.class.getResource("User.fxml"));
        Parent root = loader.load() ;

        User_loginController controller = loader.getController() ;
        controller.setPrevStage(stage); // Cung cấp stage chính cho controller

        Scene scene = new Scene(root,338, 487) ;
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

