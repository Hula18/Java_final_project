package org.example.java_final_project.Client.Controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.example.java_final_project.Model.Request;

import java.io.*;
import java.net.Socket;



// Class này dùng để gửi đi dữ liệu tới server
public class ClientCore {
    public ClientCore(String SDT, String password, String request, Label information){
        this.socket = Client.getConnect();
        System.out.println(SDT + " : " + password);
        try {
            Runnable clientCore = new Runnable() {
                @Override
                public void run() {
                    try {
                        SendRequest(request, socket);
                        Send_Login_Value(SDT, password, socket);
                        loginResult = getSuccess(socket);
                        Platform.runLater(() -> {
                            if (loginResult == 1) {
                                information.setVisible(false);
                                information.setText("");
                            } else {
                                information.setVisible(true);
                                information.setText("SDT hoặc mật khẩu không đúng vui lòng thử lại");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Client.getClose(socket);
                    }
                }
            };
            new Thread(clientCore).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Dăng ký*/
    public ClientCore(String Ho, String Ten, String SDT, String gmail, String password, String request, Label check) {
        this.socket = Client.getConnect();
        System.out.println(Ho + " : " + Ten + " : " + SDT + " : " + gmail + " : " + password);
        try {
            Runnable clientCore = new Runnable() {
                @Override
                public void run() {
                    try {
                        SendRequest(request, socket);
                        Send_SignUp_Value(Ho, Ten, SDT, gmail, password, socket);
                        signUpResult = getServerResponse(socket);
                        setSignUpResult(signUpResult);
                        Platform.runLater(() -> {
                            if (signUpResult == 1) {
                                check.setVisible(false);
                                check.setText("");
                            } else {
                                check.setVisible(true);
                                check.setText("SDT đã đăng ký vui lòng thử lại");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Client.getClose(socket);
                    }
                }
            };
            new Thread(clientCore).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SendRequest(String request, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            toServer.write(request + "\n");
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Gửi yêu cầu tới server*/
    public void Send_Login_Value(String SDT, String password, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            if (access.equals("<Oke>")) {
                toServer.write(SDT + "\n");
                toServer.write(password + "\n");
            }
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Send_SignUp_Value(String Ho, String Ten, String SDT, String gmail, String password, Socket socket) {
        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            if (access.equals("<Oke>")) {
                toServer.write(Ho + "\n");
                toServer.write(Ten + "\n");
                toServer.write(SDT + "\n");
                toServer.write(gmail + "\n");
                toServer.write(password + "\n");
            }
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSuccess(Socket socket) {
        try {
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            System.out.println(access);
            if (access.equals(Request.LoginSuccess)) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getServerResponse(Socket socket) {
        try {
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String access = fromServer.readLine();
            System.out.println(access);
            if(access.equals(Request.SignUpSuccess)) {return 1 ;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Socket socket;
    private int loginResult;
    private int signUpResult;


    public void setSignUpResult(int signUpResult) {
        this.signUpResult = signUpResult;
    }

    public int getSignUpResult() {
        return signUpResult;
    }
    public int getLoginResult() {
        return loginResult;
    }
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}