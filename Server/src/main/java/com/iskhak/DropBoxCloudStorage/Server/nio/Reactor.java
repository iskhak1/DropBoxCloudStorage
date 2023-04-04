package com.iskhak.DropBoxCloudStorage.Server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Logger;
import java.nio.*;

class Reactor implements Runnable {

    private static Logger LogManager;


     Selector selector;
      ServerSocketChannel serverSocket;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        if(sk.isAcceptable()) sk.attach(new Acceptor());
        System.out.println("server started.");
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            for (final Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); it.remove()) {
                dispatch(it.next());
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) key.attachment();
        if (r != null) {
            r.run();
        }
    }

    private final class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                System.out.println("client connected: " + c);
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }
    private void readable(){

    }

    public static void main(String[] args) throws IOException {
        new Reactor(9000).run();
    }
}