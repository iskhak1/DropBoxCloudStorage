package com.iskhak.DropBoxCloudStorage.Server.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private String serverDir;
    private String dir;
    private ServerSocketChannel server;
    private Selector selector;

    public NioServer() throws IOException {
        serverDir = System.getProperty("user.home");
        server = ServerSocketChannel.open();
        selector = Selector.open();
        server.bind(new InetSocketAddress(53742));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        while (server.isOpen()) {
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept();
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        StringBuilder s = new StringBuilder();
        while (channel.isOpen()) {
            int read = channel.read(buf);
            if (read < 0) {
                channel.close();
                return;
            }
            if (read == 0) {
                break;
            }
            buf.flip();
            while (buf.hasRemaining()) {
                s.append((char) buf.get());
            }
            buf.clear();
        }
        s.append("# ");
        byte[] message = s.toString().getBytes(StandardCharsets.UTF_8);
//        byte[] ls = "ls".getBytes(StandardCharsets.UTF_8);
        String msg = new String(message, StandardCharsets.UTF_8);
        String value = msg.split("\r\n")[0];
//        channel.write(ByteBuffer.wrap(message));

        functions(channel, value);

    }

    private void functions(SocketChannel channel, String value) throws IOException {
        dir = "Iskhak@UBUNTU:~" + " " + serverDir + " ";
        channel.write(ByteBuffer.wrap(dir.getBytes(StandardCharsets.UTF_8)));
        String [] values = value.split(" ");

        switch (value.split(" ")[0]) {
            case "ls":
                ls(channel, Path.of(serverDir));
                break;
            case "cat":
                if(values.length>=2) {
                    System.out.println("operations CAT file -> " + value.split(" ")[1]);
                    cat(channel, value.split(" ")[1]);
                }
                break;
            case "cd":
                cd(channel);
                break;
            case "quit":
                channel.write(ByteBuffer.wrap("BYE!!!\n".getBytes(StandardCharsets.UTF_8)));
                quit();
                break;
        }


    }

    private void quit() throws IOException {
        server.close();
    }

    private void cd(SocketChannel channel) {
    }

    private void cat(SocketChannel channel, String name) throws IOException {
        File file = Path.of(serverDir).resolve(serverDir + "/" + name).toFile();
        System.out.println("file -> " + file.getPath());
        
    }
        private void ls (SocketChannel channel, Path path) throws IOException {
            channel.write(ByteBuffer.wrap("\n".getBytes()));
            String nameOfDir = path.getFileName().toFile().getName();
            File[] files = path.toFile().listFiles();
            for (int i = 0; i < files.length; i++) {
                String nameF = files[i].getName() + "\n";
                channel.write(ByteBuffer.wrap(nameF.getBytes()));
            }
            channel.write(ByteBuffer.wrap(dir.getBytes()));
        }

        private void handleAccept () throws IOException {
            try {
                SocketChannel channel = server.accept();
                if (channel != null) {
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                    channel.write(ByteBuffer.wrap("Wellcome to Iskhak Terminal\n".getBytes(StandardCharsets.UTF_8)));
                    channel.write(ByteBuffer.wrap("oem@oem-X75VC:~$".getBytes(StandardCharsets.UTF_8)));

                }
            } catch (IOException ex) {
                ex.getMessage();
            }

        }


    }
