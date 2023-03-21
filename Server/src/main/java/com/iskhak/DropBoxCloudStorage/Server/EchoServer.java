package com.iskhak.DropBoxCloudStorage.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(8189)){
            System.out.println("Server started ");
            while(true) {
                Socket socket = serverSocket.accept();
                ChatHandler handler = new ChatHandler(socket);
                new Thread(handler).start();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
