package org.example.java_final_project.Server.Controller;

import org.example.java_final_project.Model.DAO.ConnectDB;
import org.example.java_final_project.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private static Connection conn = ConnectDB.OpenConnection() ;
    public static User verifyUser(User user){
        try{
            conn = ConnectDB.OpenConnection() ;
             PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ? AND `Password` = ? ");
            preparedStatement.setString(1,user.getSDT());
            preparedStatement.setString(2,user.getPassword());
            ResultSet rs = preparedStatement.executeQuery() ;
            while(rs.next()) {
                return new User(rs.getInt("Id"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("SDT"),
                        rs.getString("Gmail"),
                        rs.getString("Password"),
                        rs.getInt("IsOnline") != 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
    public static void AddUser(User user){
        try{
            conn = ConnectDB.OpenConnection() ;
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `bank` (`Ho`, `Ten`, `SDT`, `Gmail`, `Password`)"
                                    + "VALUES (?,?,?,?,?);");
            preparedStatement.setString(1,user.getHo());
            preparedStatement.setString(2,user.getTen());
            preparedStatement.setString(3,user.getSDT());
            preparedStatement.setString(4,user.getGmail());
            preparedStatement.setString(5,user.getPassword());

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean CheckDuplicated(User user){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ? ");
            preparedStatement.setString(1,user.getSDT());

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return true ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false ;
    }

    public static void updateToOnline(int id) {
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `bank`" +
                    "SET `IsOnline` = 1 Where `ID` = ? ");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void updateToOffline(int id) {
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `bank` SET `IsOnline` = 0 Where `ID` = ? ");
            preparedStatement.setInt(1,id); ;
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate() ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
