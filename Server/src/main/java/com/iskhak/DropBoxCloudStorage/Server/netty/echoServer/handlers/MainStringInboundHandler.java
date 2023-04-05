package com.iskhak.DropBoxCloudStorage.Server.netty.echoServer.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class MainStringInboundHandler extends SimpleChannelInboundHandler<String> {
    private SimpleDateFormat format;

    public MainStringInboundHandler() {
       format = new SimpleDateFormat();
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String str) throws Exception {
        log.debug("received: {}", str);
        String dateString = format.format(new Date());
        str = dateString + str;
        log.debug("processed message: {}",str);
        ctx.writeAndFlush(str);
    }
}
