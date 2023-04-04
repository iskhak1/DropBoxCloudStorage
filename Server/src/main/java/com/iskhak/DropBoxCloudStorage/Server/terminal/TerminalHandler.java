package com.iskhak.DropBoxCloudStorage.Server.terminal;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TerminalHandler implements Runnable{

    private String serverDir = "Server/base";
    private DataInputStream is;
    private DataOutputStream os;

    public TerminalHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client Accepted");

    }

    private void sendListOfFile(String dir) throws IOException {
        os.writeUTF("#list#");
        List<String> files = getFiles(dir);
        os.writeInt(files.size());
        for(String file: files){
            os.writeUTF(file);
        }
        os.flush();
    }

    private List<String> getFiles(String dir) {
        String[] list = new File(dir).list();
        return Arrays.asList(list);
    }


    @Override
    public void run() {
        byte[] buf = new byte[256];
        try{

            while(true){
                String comand = is.readUTF();
                System.out.println("received: "+comand);

                switch (comand.split(" ")[0]){

                    case "cd":
                        System.out.println("1");
                        break;
                    case "ls":
                        System.out.println("2");
                        break;
                    case "cat":
                        System.out.println("3");
                        break;
                }

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
