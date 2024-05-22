package org.example.java_final_project.Server.Controller;


import org.example.java_final_project.Server.DAO.UserDAO;
import org.example.java_final_project.Server.Model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread {

    public static void DangNhap(Socket socket){
        try{
            checkStatus(socket);
            Runnable dangnhap = new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        String Username = fromClient.readLine();
                        String Password = fromClient.readLine();
                        User user1 = UserDAO.verifyUser(new User(Username, Password));
                        if (user1 == null) {
                            toClient.write("<Wrong-user>\n");
                            toClient.flush();
                        } else {
                            toClient.write("<Login-success>\n");
                            toClient.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            dangnhap.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void DangKy(Socket socket){
        try{
            checkStatus(socket);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            String username = fromServer.readLine() ;
            String gmail = fromServer.readLine();
            String password = fromServer.readLine() ;
            System.out.println(username + " "+gmail+" "+password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void checkStatus(Socket socket){
        try{
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            toServer.write("<Oke>\n");
            toServer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
