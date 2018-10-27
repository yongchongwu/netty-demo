package com.ycwu.netty.helloworld.client;

import com.ycwu.netty.helloworld.common.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuyongchong on 2018/10/26.
 */
@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    super.userEventTriggered(ctx, evt);
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      if (event.state().equals(IdleState.WRITER_IDLE)) {
        //ctx.writeAndFlush("ping" + "\r\n");
        ctx.writeAndFlush(new Message(0,null,"ping"));
      }
    }
  }

  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message)
      throws Exception {
    System.err.println(message);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
    //重新连接服务器
    System.out.println("断线重连...");
    ctx.channel().eventLoop().schedule(new Runnable() {
      public void run() {
        Client.doConnect();
      }
    }, 3, TimeUnit.SECONDS);
    ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
