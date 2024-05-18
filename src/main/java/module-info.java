module org.example.java_final_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.java_final_project to javafx.fxml;
    exports org.example.java_final_project;
    exports org.example.java_final_project.Controller.controller;
    opens  org.example.java_final_project.Controller.controller to javafx.fxml;
}