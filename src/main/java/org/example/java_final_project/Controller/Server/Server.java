package org.example.java_final_project.Controller.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket ;
    private Socket connection ;
    private int portServer ;
    private ObjectOutputStream objectOutputClient ;
    private ObjectInputStream objectInputStream ;
    public boolean isStop = false, isExit = false ;
    public Server(int port) throws Exception {
        this.serverSocket = new ServerSocket(port) ;
        this.portServer = port ;
        (new WaitForConnect()).start();
    }

    private boolean waitForConnection() throws Exception {
        connection = serverSocket.accept() ;
        return true ;
    }

    public class WaitForConnect extends Thread {
        @Override
        public void run() {
            try{
                while(!isStop) {
                    System.out.println("Đã mở được server");
                    System.out.println(portServer);
//                    if(waitForConnection()) {
//                        System.out.println("Đã mở được server");
//                        System.out.println(connection);
//                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopServer() throws Exception {
        serverSocket.close();
        isStop = true ;
    }
}
