package org.example.java_final_project.Server.Controller;

import javafx.stage.Stage;
import org.example.java_final_project.Server.Controller.ServerController;
import org.example.java_final_project.Model.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private ServerSocket serverSocket;
    private ServerController serverController;
    public boolean isStop = false;

    public server(int port, ServerController serverController) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.serverController = serverController;
            System.out.println("Server open port: " + port);
            isStop = false;
            (new WaitForConnect()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class WaitForConnect extends Thread {
        @Override
        public void run() {
            try{
                while(!isStop) {
                    Socket connection = serverSocket.accept() ;
                    Runnable DocDuLieu = new Runnable() {
                        @Override
                        public void run() {
                            Read_Request_Of_Client(connection);
                        }
                    };
                    DocDuLieu.run();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopServer() throws IOException {
        isStop = true ;
        serverSocket.close();
    }
    public void Read_Request_Of_Client(Socket socket) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readLine = bufferedReader.readLine();
            ServerThread serverThread = new ServerThread(serverController, socket);
            switch (readLine){
                case Request.LOGIN -> {
                    System.out.println("người dùng đăng nhập");
                    serverThread.DangNhap();
                }
                case Request.Lay_Du_Lieu ->{
                    System.out.println("Người dùng lấy dữ liệu");
                    serverThread.LayDuLieu();
                }
                case Request.SIGNUP -> {
                    System.out.println("người dùng đăng ký");
                    serverThread.DangKy();
                }
                case Request.SignOff -> {
                    System.out.println("Nggười dùng đăng xuất");
                    serverThread.DangXuat();
                }
                case Request.GetAccountName -> {
                    System.out.println("Người dùng tìm người");
                    serverThread.GetUser() ;
                }
                case Request.ChangePassword -> {
                    System.out.println("Người dùng đổi mật khẩu");
                    serverThread.ThayDoiMatKhau();
                }
                case Request.ChangeMaPin -> {
                    System.out.println("Người dùng đổi mã PIN");
                    serverThread.DoiMaPIN();
                }
                case Request.Check_Ma_PIN -> {
                    System.out.println("Người dùng kiểm tra mã PIN");
                    serverThread.XacThuc();
                }
                case Request.CTien -> {
                    System.out.println("Người dùng chuyển tiền");
                    serverThread.ChuyenTien();
                }
                case Request.Auto_Update_Balance -> {
                    System.out.println("Kiểm tra thay đổi");
                    serverThread.DoiBalance();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
