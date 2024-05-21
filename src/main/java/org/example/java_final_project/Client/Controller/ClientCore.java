package org.example.java_final_project.Client.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// Class này dùng để gửi đi dữ liệu tới server
public class ClientCore {
    public ClientCore(String username, String password, String request){
        Socket socket = Client.getConnect() ;
        System.out.println(username+":"+password);
        try {
            Thread sendRequest = new Thread(() ->SendRequest(request,socket));
            sendRequest.start();
            sendRequest.join();
            Thread sendValue = new Thread(() -> Send_Login_Value(username,password,socket));
            sendValue.start();
            sendValue.join();

        }catch (Exception e){
            e.printStackTrace();
        }
        Client.getClose(socket);
    }
    public ClientCore(String username , String email , String password , String request) {
        Socket socket = Client.getConnect() ;
        System.out.println(username +" : "+email +" : "+password);
        try{
            Thread sendRequest = new Thread(() -> SendRequest(request,socket));
            sendRequest.start();sendRequest.join();
            Thread SendValue = new Thread(() -> Send_SignUp_Value(username,password,email,socket));
            SendValue.start();SendValue.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        Client.getClose(socket);
    }

    // Method chung
    public void SendRequest(String request,Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            toServer.write(request+"\n");
            toServer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }  /*Gửi yêu cầu tới server*/


    // Method riêng
    public void Send_Login_Value(String username , String password ,Socket socket){
        try{
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            String access = fromServer.readLine() ;
            if(access.equals("<Oke>")) {
                toServer.write(username+"\n");
                toServer.write(password+"\n");
                toServer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Send_SignUp_Value(String username , String password , String email , Socket socket){
        try{
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            String access = fromServer.readLine() ;
            if(access.equals("<Oke>")){
                toServer.write(username+"\n");
                toServer.write(password+"\n");
                toServer.write(email+"\n");
                toServer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
