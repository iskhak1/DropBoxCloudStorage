package com.iskhak.DropBoxCloudStorage.Server.terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {

    private int port ;
    private String host;
    private DataInputStream is;
    private DataOutputStream os;

    public Network(){
        port = 9999;
        host = "localhost";

        try(Socket socket = new Socket(host,port)){
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String readString() throws IOException {
        return is.readUTF();
    }

    public int readInt() throws IOException {
        return is.readInt();
    }

    public void writeMessage(String message) throws IOException {
        os.writeUTF(message);
        os.flush();
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }
}
