package com.iskhak.DropBoxCloudStorage.Server.terminal;

import javax.swing.event.ChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Terminal {

    private int port;
    private DataInputStream is ;
    private DataOutputStream os;


    public static void main(String[] args) {
        String host = "localhost";
        network(host,9999);
    }


    private static void network(String host, int port){

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server start");
            while(true){
                Socket socket = serverSocket.accept();
                TerminalHandler terminalHandler = new TerminalHandler(socket);
                new Thread(terminalHandler).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
