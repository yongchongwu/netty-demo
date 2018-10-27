package com.ycwu.netty.helloworld.client;

import com.ycwu.netty.helloworld.common.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wuyongchong on 2018/10/26.
 */
public class Client {

  private static EventLoopGroup group;

  private static Bootstrap bootstrap;

  private static Channel channel;

  public static void doConnect() {
    if (channel != null && channel.isActive()) {
      return;
    }
    final ChannelFuture future;
    synchronized (bootstrap) {
      future = bootstrap.connect("127.0.0.1", 8888);
    }
    future.addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture f) throws Exception {
        if (f.isSuccess()) {
          channel = f.channel();
          System.out.println("connect server successfully");
        } else {
          System.out.println("Failed to connect to server, try connect after 3s");
          f.channel().pipeline().fireChannelInactive();
        }
      }
    });
  }

  public static void run() {
    group = new NioEventLoopGroup();
    try {
      bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(new ClientInitializer());
      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.option(ChannelOption.TCP_NODELAY, true);
      bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

      doConnect();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void send(String msg) throws InterruptedException {
    if (channel != null && channel.isActive()) {
      //channel.writeAndFlush(msg + "\r\n");
      channel.writeAndFlush(new Message(0,null,msg));
      if ("bye".equals(msg.toLowerCase())) {
        channel.closeFuture().sync();
      }
    }
  }

  public static void shutdown() {
    if (group != null) {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Client.run();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for (; ; ) {
      String line = in.readLine();
      if (line == null) {
        break;
      }
      Client.send(line);
    }
  }
}
