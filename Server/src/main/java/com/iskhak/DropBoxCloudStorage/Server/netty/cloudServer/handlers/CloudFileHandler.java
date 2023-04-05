package com.iskhak.DropBoxCloudStorage.Server.netty.cloudServer.handlers;

import com.iskhak.DropBoxCloudStorage.Broker.FileMessage;
import com.iskhak.DropBoxCloudStorage.Broker.FileRequest;
import com.iskhak.DropBoxCloudStorage.Broker.ListFiles;
import io.netty.channel.ChannelHandlerContext;
import com.iskhak.DropBoxCloudStorage.Broker.CloudMessage;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Files;
import java.nio.file.Path;

public class CloudFileHandler extends SimpleChannelInboundHandler<CloudMessage> {

    private Path currenDir;
    public CloudFileHandler(){
        currenDir = Path.of("Server/base");
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new ListFiles(currenDir));
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, CloudMessage cloudMessage) throws Exception {
        if(cloudMessage instanceof FileRequest fileRequest){
            ctx.writeAndFlush(new FileMessage(currenDir.resolve(fileRequest.getName())));
        } else if( cloudMessage instanceof FileMessage fileMessage){
            Files.write(currenDir.resolve(fileMessage.getName()),fileMessage.getData());
            ctx.writeAndFlush(new ListFiles(currenDir));
        }
    }
}
