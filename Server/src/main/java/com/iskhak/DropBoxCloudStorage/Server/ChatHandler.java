package com.iskhak.DropBoxCloudStorage.Server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
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
        sendListOfFile(serverDir);
    }

    private void sendListOfFile(String str) throws IOException {
        os.writeUTF("#list#");
        List<String> files = getFiles(str);
        os.writeInt(files.size());
        for (String file : files) {
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
        try {
            while (true) {
                String command = is.readUTF();
                System.out.println("received");
                if (command.equals("#file#")) {
                    String fileName = is.readUTF();
                    long len = is.readLong();
                    File file = Path.of(serverDir).resolve(fileName).toFile();
                    try(FileOutputStream fos = new FileOutputStream(file)) {
                        for (int i = 0; i < (len + 255) / 256; i++) {
                            int read = is.read(buf);
                                fos.write(buf,0,read);
                        }
                    }catch (Exception e){
                     e.printStackTrace();
                    }
                    sendListOfFile(serverDir);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
