package com.ycwu.netty.helloworld.server;

import com.ycwu.netty.helloworld.common.Message;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by wuyongchong on 2018/10/26.
 */
@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

  //心跳丢失次数
  private int counter = 0;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
    //ctx.write("It is " + new Date() + " now.\r\n");
    ctx.write(new Message(0,null,"Welcome to " + InetAddress.getLocalHost().getHostName() + "!"));
    ctx.write(new Message(0,null,"It is " + new Date() + " now."));
    ctx.flush();
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      if (event.state().equals(IdleState.READER_IDLE)) {
        if (counter >= 3) {
          ctx.close();
          System.out.println("已与" + ctx.channel().remoteAddress() + "断开连接");
        } else {
          counter++;
          System.out.println("丢失了第 " + counter + " 个心跳包");
        }
      }
    }
  }

  //业务逻辑处理
  protected void channelRead0(ChannelHandlerContext ctx, Object message)
      throws Exception {
    String response;
    boolean close = false;
    System.out.println("receive message: "+message);
    if (message==null || message=="") {
      //response = "Please type something.\r\n";
      response = "Please type something.";
    }else if ("bye".equals(message.toString().toLowerCase())) {
      //response = "Have a good day!\r\n";
      response = "Have a good day!";
      close = true;
    } else {
      //response = "Did you say '" + message + "'?\r\n";
      response = "Did you say " + message.toString() + "?";
    }
    ChannelFuture future = ctx.write(new Message(0,null,response));
    if (close) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  //异常处理
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
