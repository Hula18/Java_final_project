module org.example.java_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.java_final_project to javafx.fxml;
    exports org.example.java_final_project;
    exports org.example.java_final_project.Client.Controller;
    opens org.example.java_final_project.Client.Controller to javafx.fxml;
    exports org.example.java_final_project.Server.Controller;
    opens org.example.java_final_project.Server.Controller to javafx.fxml;
}