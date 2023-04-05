package com.iskhak.DropBoxCloudStorage.Server.netty.corePipelineServer.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class MainStringInboundHandler extends ChannelInboundHandlerAdapter {
    private SimpleDateFormat format;

    public MainStringInboundHandler() {
       format = new SimpleDateFormat();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String str = (String) msg;
        log.debug("received: {}", str);
        String dateString = format.format(new Date());
        str = dateString + str;
        log.debug("processed message: {}",str);
        ctx.write(str);

    }
}
