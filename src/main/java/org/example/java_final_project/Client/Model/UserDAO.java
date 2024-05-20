package org.example.java_final_project.Client.Model;

import org.example.java_final_project.Client.Controller.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// Class này dùng để gửi đi dữ liệu tới server
public class UserDAO {
    public UserDAO(String username, String password, String request){
        Socket socket = Client.getConnect() ;
        System.out.println(username+":"+password);
        try{
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            toServer.write(request+"\n");
            toServer.flush();
                String access = fromServer.readLine();
                if(access.equals("Oke")){
                    toServer.write(username+"\n");
                    toServer.write(password+"\n");
                    toServer.flush();
                }
            Client.getClose(socket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public UserDAO(String username , String gmail , String password , String request) {
        Socket socket = Client.getConnect() ;
        System.out.println(username +":"+gmail +":"+password);
        try{
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            toServer.write(request+"\n");
            toServer.flush();
                String access = fromServer.readLine(); ;
                if(access.equals("Oke")) {
                    toServer.write(username+"\n");
                    toServer.write(gmail+"\n");
                    toServer.write(password+"\n");
                    toServer.flush();
                }
                Client.getClose(socket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
