package org.example.java_final_project.Server.DAO;

import org.example.java_final_project.Server.Model.User;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
        public static User verifyUser(User nguoidung){
            try{
                Connection conn = ConnectDB.OpenConnection() ;
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT *\n"
                        +"FROM user \n"
                        +"WHERE `username` = ?\n"
                        +"AND `pass`= ?"
                );
                preparedStatement.setString(1,nguoidung.getUsername());
                preparedStatement.setString(2,nguoidung.getPassword());
                ResultSet rs = preparedStatement.executeQuery() ;
                while(rs.next()){
                    return new User(rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    (rs.getInt(5) != 0),
                                    (rs.getInt(6) != 0)
                                    );
                }
                ConnectDB.CloseConnection(conn);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null ;
        }

        public void UpdateToOnline(int id){
            try{
                Connection conn = ConnectDB.OpenConnection() ;
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user\n"
                        + "SET IsOnline = 1\n"
                        + "WHERE ID = ?");
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate() ;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    public void UpdateToOffline(int id){
        try{
            Connection conn = ConnectDB.OpenConnection() ;
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE user\n"
                    + "SET IsOnline = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
