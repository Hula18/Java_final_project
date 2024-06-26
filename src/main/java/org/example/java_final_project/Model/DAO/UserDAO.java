package org.example.java_final_project.Model.DAO;

import org.example.java_final_project.MaHoa.RSA_Private;
import org.example.java_final_project.Model.DAO.ConnectDB;
import org.example.java_final_project.Model.Private_User;
import org.example.java_final_project.Model.User;

import java.math.BigDecimal;
import java.sql.*;

public class UserDAO {
    private static Connection conn = ConnectDB.OpenConnection();

    public static User verifyUser(User user) {
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ? AND `Password` = ?");
            preparedStatement.setString(1, user.getSDT());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("Id"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("SDT"),
                        rs.getString("Gmail"),
                        rs.getString("Password"),
                        rs.getInt("IsOnline") != 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User verifyUserBy_SDT(User user) {
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `bank` WHERE `SDT` = ?");
            preparedStatement.setString(1, user.getSDT());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("Id"),
                        rs.getString("Ho"),
                        rs.getString("Ten"),
                        rs.getString("SDT"),
                        rs.getString("Gmail"),
                        rs.getString("Password"),
                        rs.getInt("IsOnline") != 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Private_User verifyUserBy_SDT_For_user(Private_User user) {
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `user` WHERE `SDT` = ?");
            preparedStatement.setString(1, user.getSdt());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return new Private_User(rs.getString("SDT"),
                        rs.getBigDecimal("Balance"),
                        rs.getString("Pin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void UpdateMaPin(Private_User user) {
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE `user` SET `Pin` = ? WHERE `SDT` = ?");
            preparedStatement.setString(1, user.getPin());
            preparedStatement.setString(2, user.getSdt());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AddUser(User user) {
        String bankQuery = "INSERT INTO `bank` (`Ho`, `Ten`, `SDT`, `Gmail`, `Password`)"
                + "VALUES (?,?,?,?,?);";
        String userQuery = "INSERT INTO `user` (`SDT`, `Pin`) VALUES (?,?)";
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(bankQuery);
            preparedStatement.setString(1, user.getHo());
            preparedStatement.setString(2, user.getTen());
            preparedStatement.setString(3, user.getSDT());
            preparedStatement.setString(4, user.getGmail());
            preparedStatement.setString(5, user.getPassword());

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                PreparedStatement insertInformation = conn.prepareStatement(userQuery);
                insertInformation.setString(1, user.getSDT());
                insertInformation.setString(2, "0");
                insertInformation.executeUpdate();
            } else {
                System.out.println("Failed to add user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean CheckDuplicated(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM `bank` WHERE `SDT` = ?");
            preparedStatement.setString(1, user.getSDT());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateToOnline(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `bank` SET `IsOnline` = 1 WHERE `ID` = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateToOffline(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `bank` SET `IsOnline` = 0 WHERE `ID` = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ChangeName(String SDT) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT `Ho`, `Ten` FROM `bank` WHERE `SDT` = ?");
            preparedStatement.setString(1, SDT);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("Ho") + " " + rs.getString("Ten");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown User";
    }

    public static BigDecimal UpdateBalance(String SDT) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT `Balance` FROM `user` WHERE `SDT` = ?");
            preparedStatement.setString(1, SDT);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("Balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updatePassword(String sdt, String newPassword) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `bank` SET `Password` = ? WHERE `SDT` = ?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, sdt);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean CheckMaPIN(String sdt, String PIN) {
        try {
            conn = ConnectDB.OpenConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT `Pin` FROM `user` WHERE `SDT` = ?");
            preparedStatement.setString(1, sdt);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
               String pin = rs.getString("Pin") ;
               return pin.equals(PIN) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean ChuyenTien(String currentDate ,String currentTime, String sendSDT , String receiveSDT , BigDecimal amount,String loinhan){
        try{
            conn = ConnectDB.OpenConnection();
            conn.setAutoCommit(false); // Tắt chế độ tự động xác nhận

            // Trừ tiền từ tài khoản gửi
            PreparedStatement psUpdateSender = conn.prepareStatement("UPDATE `user` SET `Balance` = `Balance` - ? WHERE `SDT` = ?");
            psUpdateSender.setBigDecimal(1, amount);
            psUpdateSender.setString(2, sendSDT);
            int n = psUpdateSender.executeUpdate();

            // Cộng tiền vào tài khoản nhận
            PreparedStatement psUpdateReceiver = conn.prepareStatement("UPDATE `user` SET `Balance` = `Balance` + ? WHERE `SDT` = ?");
            psUpdateReceiver.setBigDecimal(1, amount);
            psUpdateReceiver.setString(2, receiveSDT);
            int m = psUpdateReceiver.executeUpdate();
            if(n == 1 && m == 1 ){
                String giveSQL = "INSERT INTO transactions (date, time, from_SDT, to_SDT, description, amount, is_incoming) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String getSQL = "INSERT INTO transactions (date,time, from_SDT, to_SDT, description, amount, is_incoming) VALUES (?,?,?, ?, ?, ?, ?)" ;

                PreparedStatement sender = conn.prepareStatement(giveSQL) ;
                sender.setString(1, currentDate);
                sender.setString(2, currentTime);
                sender.setString(3,sendSDT);
                sender.setString(4,receiveSDT);
                sender.setString(5,loinhan);
                sender.setBigDecimal(6,amount);
                sender.setBoolean(7,false);
                sender.executeUpdate() ;

                PreparedStatement receiver = conn.prepareStatement(getSQL) ;
                receiver.setString(1,currentDate);
                receiver.setString(2, currentTime);
                receiver.setString(3,sendSDT);
                receiver.setString(4,receiveSDT);
                receiver.setString(5,loinhan);
                receiver.setBigDecimal(6,amount);
                receiver.setBoolean(7,true);
                receiver.executeUpdate();

                conn.commit(); // Xác nhận giao dịch nếu không có lỗi
                return true;
            }else{
                conn.rollback(); // Rollback transaction if update fails
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false ;
    }

    public boolean checkIsBanned(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM banned_user WHERE `SDT_User` = ?");
            preparedStatement.setString(1, user.getSDT());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
