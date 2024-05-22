package org.example.java_final_project.Server.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    protected Statement statement ;
    public static Connection OpenConnection() {
        Connection connection = null ;

        final String DATABASE_NAME = "jdbc:mysql://localhost:3306/dangnhap";
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
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
