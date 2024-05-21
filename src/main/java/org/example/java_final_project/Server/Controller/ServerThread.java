package org.example.java_final_project.Server.Controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread {
    public static void Login(Socket socket){
        try{
            checkStatus(socket);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String Username = fromServer.readLine();
            String Password = fromServer.readLine();
            System.out.println(Username + " " + Password);
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
