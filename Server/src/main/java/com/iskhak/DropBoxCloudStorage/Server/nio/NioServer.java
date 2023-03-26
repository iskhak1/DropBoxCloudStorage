package com.iskhak.DropBoxCloudStorage.Server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private ServerSocketChannel server;
    private Selector selector;

    public NioServer() throws IOException {
        server = ServerSocketChannel.open();
        selector = Selector.open();
        server.bind(new InetSocketAddress(9999));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        while(server.isOpen()){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()) {
                    handleAccept();
                }
                if(key.isReadable()){
                    System.out.println(key);
                    System.out.println(key.channel());
                }
            }
        }
    }

    private void handleAccept() throws IOException {
        SocketChannel channel =   server.accept();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_READ);
        channel.write(ByteBuffer.wrap("Wellcome to Iskhak terminal\n".getBytes(StandardCharsets.UTF_8)));

    }

}
