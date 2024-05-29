package org.example.java_final_project.Model.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDB {
    public static Connection OpenConnection() {
        Connection connection = null ;

        final String DATABASE_NAME = "jdbc:mysql://localhost:3306/cuoiki";
        final String USER = "root" ;
        final String password = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver") ;
            connection = DriverManager.getConnection(DATABASE_NAME,USER,password) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection ;
    }
    public static void CloseConnection(Connection conn) {
            if(conn != null){
                try{
                   if(!conn.isClosed()) {
                       conn.close();
                   }
                }catch (Exception e){
                        e.printStackTrace();
                }
            }
    }
}
