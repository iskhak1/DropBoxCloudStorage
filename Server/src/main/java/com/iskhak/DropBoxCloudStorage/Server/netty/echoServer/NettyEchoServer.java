package com.iskhak.DropBoxCloudStorage.Server.netty.echoServer;

import com.iskhak.DropBoxCloudStorage.Server.netty.corePipelineServer.handlers.MainStringInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoServer {

    public static void main(String[] args) {

        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            ServerBootstrap server = new ServerBootstrap();
            server.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new StringDecoder(),
                                    new StringEncoder(),
                                    new MainStringInboundHandler()
                            );
                        }
                    });
            ChannelFuture future = server.bind(8189).sync();
            log.debug("Server is ready");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
