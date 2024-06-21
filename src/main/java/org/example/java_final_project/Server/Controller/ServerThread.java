package org.example.java_final_project.Server.Controller;


import org.example.java_final_project.Client.Controller.Handle.Client;
import org.example.java_final_project.Model.DAO.UserDAO;
import org.example.java_final_project.Model.Private_User;
import org.example.java_final_project.Model.Request;
import org.example.java_final_project.Model.User;

import java.io.*;
import java.math.BigDecimal;
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
                                if(user1 != null){
                                    if(!user1.isOnline()){
                                        toClient.write(Request.LoginSuccess + "\n");
                                        UserDAO.updateToOnline(user1.getID());
                                        controller.updateMessage("[" +user1.getID()+"]"+user1.getTen() +" : đang online");
                                    }else{
                                        toClient.write(Request.UserAlreadyLoggedIn + "\n");
                                    }
                                }else{
                                    toClient.write(Request.WrongUser + "\n");
                                }
                                toClient.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }).start();
                }
            };
            new Thread(dangnhap).start();
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

    public void GetUser(){
        try{
            checkStatus();
            Runnable layDauLieu = new Runnable() {
                @Override
                public void run() {
                    try{
                        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                        BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
                        String data = fromClient.readLine();
                        User user = UserDAO.verifyUserBy_SDT(new User(data)) ;
                        if(user != null){
                            toClient.write(Request.GetAccountNameSuccess +"\n");
                            toClient.write(user.getHo() + " "+user.getTen() + "\n");
                            System.out.println(user.getHo()+ " "+user.getTen());
                            toClient.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } ;
            new Thread(layDauLieu).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ThayDoiMatKhau(){
            checkStatus();
            Runnable checkDuLieu = new Runnable() {
                @Override
                public void run() {
                    try{
                        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                        BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
                        String sdt = fromClient.readLine() ;
                        String oldPassword = fromClient.readLine() ;
                        String newPassword = fromClient.readLine() ;
                        User user = UserDAO.verifyUser(new User(sdt,oldPassword)) ;
                        if(user != null){
                            UserDAO.updatePassword(user.getSDT(),newPassword);
                            toClient.write(Request.ChangePassSuccess+"\n");
                            toClient.flush();
                        }else {
                            toClient.write(Request.LastPasswordFail+"\n");
                            toClient.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            new Thread(checkDuLieu).start();
    }
    public void DoiMaPIN() {
        checkStatus();
        Runnable layMaPin = new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String sdt = fromClient.readLine();
                    String lastPin = fromClient.readLine();
                    String newPin = fromClient.readLine();
                    Private_User privateUser = UserDAO.verifyUserBy_SDT_For_user(new Private_User(sdt));
                    if (privateUser != null) {
                        if (lastPin.equals(privateUser.getPin())) {
                            UserDAO.UpdateMaPin(new Private_User(sdt, newPin));
                            toClient.write(Request.ChangeSuccessPin + "\n");
                            toClient.flush();
                        } else {
                            toClient.write(Request.ChangeFailedPin + "\n");
                            toClient.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(layMaPin).start();
    }
    public void XacThuc() {
        checkStatus();
        Runnable XacThucMaPin = new Runnable() {
            @Override
            public void run() {
                try{
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;

                    String sdt = fromClient.readLine();
                    String PIN = fromClient.readLine() ;

                    boolean check = UserDAO.CheckMaPIN(sdt,PIN) ;
                    if(check){
                        toClient.write(Request.Ma_PIN_Success + "\n");
                        toClient.flush();
                    }else{
                        toClient.write(Request.Ma_PIN_Fail + "\n");
                        toClient.flush();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(XacThucMaPin).start();
    }
    public void ChuyenTien(){
        checkStatus();
        Runnable ChuyenTien = new Runnable() {
            @Override
            public void run() {
               try{
                   BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
                   String currentDate = fromClient.readLine(); ;
                   String currentTime = fromClient.readLine();
                   String sdt = fromClient.readLine();
                   String sdtNguoiDcChuyen = fromClient.readLine();
                   String balance = fromClient.readLine() ;
                   String loinhan = fromClient.readLine();
                   BigDecimal tien = new BigDecimal(balance); // Chuyển đổi chuỗi thành BigDecimal

                   System.out.println(sdt + " "+ sdtNguoiDcChuyen + " "+balance);
                   boolean check = UserDAO.ChuyenTien(currentDate,currentTime, sdt,sdtNguoiDcChuyen,tien,loinhan) ;
                   if(check){
                       toClient.write(Request.CTien_Thanh_Cong+"\n");
                       toClient.flush();
                   }else{
                       toClient.write(Request.CTien_ThatBai+"\n");
                       toClient.flush();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        };
        new Thread(ChuyenTien).start();
    }
    public void LayDuLieu(){
        checkStatus();
        Runnable LayDuLieu = new Runnable() {
            @Override
            public void run() {
                try{
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;

                    String sdt = fromClient.readLine(); ;
                    String HoVaTen = UserDAO.ChangeName(sdt) ;
                    String balance = String.valueOf(UserDAO.UpdateBalance(sdt));

                    toClient.write(Request.Lay_Du_Lieu_Thanh_Cong+"\n");
                    toClient.write(HoVaTen+"\n");
                    toClient.write(balance+"\n");
                    toClient.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(LayDuLieu).start();
    }
    public void DoiBalance(){
        checkStatus();
        Runnable doi = new Runnable() {
            @Override
            public void run() {
                try{
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String sdt = fromClient.readLine();
                    String tien = String.valueOf(UserDAO.UpdateBalance(sdt));
                    toClient.write(Request.Update_Balance_Success+"\n");
                    toClient.write(tien + "\n");
                    toClient.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ;
        new Thread(doi).start();
    }

    public void checkStatus(){
        try{
            BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
            toClient.write(Request.OKE+"\n");
            toClient.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
