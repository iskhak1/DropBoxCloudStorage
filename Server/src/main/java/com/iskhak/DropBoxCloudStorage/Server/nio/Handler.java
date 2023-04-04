package com.iskhak.DropBoxCloudStorage.Server.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

final class Handler implements Runnable {
    final SocketChannel c;
    final SelectionKey key;
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    public Handler(Selector selector, SocketChannel c) throws IOException {
        this.c = c;
        c.configureBlocking(false);
        System.out.println("client connected: " + c);
        c.write(ByteBuffer.wrap("Wellcome to Iskhak Terminal".getBytes(StandardCharsets.UTF_8)));
        key = c.register(selector, 0);
        key.attach(this);
        key.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }


    @Override
    public void run() {
        try {
            SocketChannel client = (SocketChannel) key.channel();
            client.read(buffer);
            if (new String(buffer.array()).trim().equals("close")) {
                client.close();
                System.out.println("close connection");
            }

            buffer.flip();
            client.write(buffer);
            buffer.clear();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}