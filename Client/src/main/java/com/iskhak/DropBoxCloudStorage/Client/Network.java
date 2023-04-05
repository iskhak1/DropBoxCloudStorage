package com.iskhak.DropBoxCloudStorage.Client;

import com.iskhak.DropBoxCloudStorage.Broker.CloudMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Network<CloudMessage>{

    private int port;
    private ObjectDecoderInputStream is;
    private ObjectEncoderOutputStream os;

    public Network(int port) throws IOException {
        this.port = port;
        Socket socket = new Socket("localhost",port);
        is = new ObjectDecoderInputStream(socket.getInputStream());
        os = new ObjectEncoderOutputStream(socket.getOutputStream());
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

    public ObjectDecoderInputStream getIs() {
        return is;
    }

    public ObjectEncoderOutputStream getOs() {
        return os;
    }

    public CloudMessage read() throws IOException, ClassNotFoundException {
        return (CloudMessage) is.readObject();
    }

    public void write(CloudMessage msg) throws IOException {
        os.writeObject(msg);
        os.flush();
    }

}
