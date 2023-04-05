package com.iskhak.DropBoxCloudStorage.Server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;

public class Main {

    public static void main(String[] args) {

        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try{

            ServerBootstrap server = new ServerBootstrap();
            server.channel(NioSctpServerChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        }
                    });
            ChannelFuture future = server.bind(8189).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
