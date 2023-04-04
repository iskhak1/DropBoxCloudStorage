package com.iskhak.DropBoxCloudStorage.Server.nio;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.start();
    }

}
