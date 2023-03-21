package com.iskhak.DropBoxCloudStorage.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ChatHandler implements Runnable {
    private String serverDir = "Server/base";
    private DataInputStream is;
    private DataOutputStream os;

    public ChatHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        System.out.println("client accepted");
        List<String> files = getFiles(serverDir);
        for (String file : files) {
            os.writeUTF(file);
        }
        os.flush();
    }
    private void setListOfFile(String str) throws IOException {
        os.writeUTF("#list#");
        List<String> files = getFiles(str);
        os.writeInt(files.size());
        for (String file : files) {
            os.writeUTF(file);
        }
        os.flush();
    }
    private List<String> getFiles(String dir){
        String [] list = new File(dir).list();
        return Arrays.asList(list);
    }


    @Override
    public void run() {
        try{
            while(true){
                String msg = is.readUTF();
                System.out.println("received");
                os.writeUTF(msg);
                os.flush();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
