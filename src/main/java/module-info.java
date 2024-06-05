module org.example.java_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.java_final_project to javafx.fxml;
    exports org.example.java_final_project;
    exports org.example.java_final_project.Server.Controller;
    opens org.example.java_final_project.Server.Controller to javafx.fxml;
    exports org.example.java_final_project.Client.Controller.Login_And_SignUp;
    opens org.example.java_final_project.Client.Controller.Login_And_SignUp to javafx.fxml;
    exports org.example.java_final_project.Client.Controller.Bank;
    opens org.example.java_final_project.Client.Controller.Bank to javafx.fxml;
    exports org.example.java_final_project.Model.DAO;
    opens org.example.java_final_project.Model.DAO to javafx.fxml;
    exports org.example.java_final_project.Client.Controller.Handle;
    opens org.example.java_final_project.Client.Controller.Handle to javafx.fxml;
}