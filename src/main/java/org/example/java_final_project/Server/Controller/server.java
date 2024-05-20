package org.example.java_final_project.Server.Controller;

import org.example.java_final_project.Server.Model.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private ServerSocket serverSocket ;
    public boolean isStop = false;
    public server(int port)  {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server open port: "+port);
            isStop = false;
            (new WaitForConnect()).start();
        }catch (Exception e){
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
    public static void Read_Request_Of_Client(Socket socket) {
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readLine = bufferedReader.readLine();
            switch (readLine){
                case Request.LOGIN -> {
                    System.out.println("Đăng nhập");
                    ServerThread.Login(socket);
                }
                case Request.SIGNUP -> {
                    System.out.println("Đăng ký");
                    ServerThread.DangKy(socket);
                    /*Chưa hoàn thành*/
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
