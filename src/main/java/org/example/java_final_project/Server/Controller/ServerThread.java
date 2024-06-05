package org.example.java_final_project.Server.Controller;


import org.example.java_final_project.Client.Controller.Handle.Client;
import org.example.java_final_project.Model.DAO.UserDAO;
import org.example.java_final_project.Model.Request;
import org.example.java_final_project.Model.User;

import java.io.*;
import java.net.Socket;


public class ServerThread{
    private ServerController controller;
    private Socket socket;

    public ServerThread(ServerController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
    }
    // Method
    public void DangNhap(){
            checkStatus();
            Runnable dangnhap = new Runnable() {
                @Override
                public void run() {
                    new Thread(() -> {
                            try {
                                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                                String SDT = fromClient.readLine();
                                String Password = fromClient.readLine();
                                System.out.println(SDT + "  " + Password);

                                // Simulate user verification
                                User user1 = UserDAO.verifyUser(new User(SDT, Password));
                                if (user1 == null) {
                                    toClient.write(Request.WrongUser + "\n");
                                } else if(!user1.isOnline()) {
                                    toClient.write(Request.LoginSuccess + "\n");
                                    UserDAO.updateToOnline(user1.getID());
                                    controller.updateMessage("[" +user1.getID()+"]"+user1.getTen() +" : đang online");
                                }else{
                                    UserDAO.updateToOffline(user1.getID());
                                    controller.updateMessage("[" +user1.getID()+"]"+user1.getTen() +" : đang offline");
                                }
                                toClient.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }).start();
                }
            };
            dangnhap.run();
    }
    public void DangKy(){
        checkStatus();
        Runnable dangky = new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String Ho = fromClient.readLine();
                    String Ten = fromClient.readLine();
                    String SDT = fromClient.readLine();
                    String email = fromClient.readLine();
                    String password = fromClient.readLine();
                    System.out.println(Ho + " " + Ten + " " + SDT + " " + email + " " + password);
                    boolean checkup = UserDAO.CheckDuplicated(new User(SDT));

                    System.out.println(checkup);

                    if (checkup) {
                        toClient.write(Request.ExistNumberPhone + "\n");
                        System.out.println(Request.ExistNumberPhone);
                    } else {
                        UserDAO.AddUser(new User(Ho, Ten, SDT, email, password));
                        toClient.write(Request.SignUpSuccess + "\n");
                        User user = UserDAO.verifyUser(new User(Ho,Ten,SDT,email,password)) ;

                        UserDAO.updateToOnline(user.getID());
                        controller.updateMessage("[" +user.getID()+"]"+user.getTen() +" : đang online");
                    }
                    toClient.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(dangky).start();
    }

    public void DangXuat() {
        checkStatus();
        Runnable DangXuat = new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String request = fromClient.readLine();
                    User user = UserDAO.verifyUserBy_SDT(new User(request));
                    if(user != null){
                        toClient.write(Request.SignOffSuccess+"\n");
                        toClient.flush();
                        UserDAO.updateToOffline(user.getID());
                        controller.updateMessage("[" +user.getID()+"]"+user.getTen() +" : đã offline");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(DangXuat).start();
    }

    public void checkStatus(){
        try{
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            toServer.write(Request.OKE+"\n");
            toServer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
