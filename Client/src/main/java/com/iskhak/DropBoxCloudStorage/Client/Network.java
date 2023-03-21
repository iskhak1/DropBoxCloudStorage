package com.iskhak.DropBoxCloudStorage.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private int port;
    private DataInputStream is;
    private DataOutputStream os;

    public Network(int port) throws IOException {
        this.port = port;
        Socket socket = new Socket("localhost",port);
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
    }

    public String readMessage() throws IOException {
        return is.readUTF();
    }

    public void writeMessage(String message) throws IOException {
        os.writeUTF(message);
        os.flush();
    }
}
