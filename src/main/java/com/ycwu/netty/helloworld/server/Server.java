package com.ycwu.netty.helloworld.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by wuyongchong on 2018/10/26.
 */
public class Server {

  public static void main(String[] args) {
    //配置服务端
    //创建两个EventLoopGroup对象
    //一个是boss线程组,用于接收客户端的连接
    //一个是work线程组,用于进行socketChannel的数据数据读写
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap= new ServerBootstrap();
      //设置使用的EventLoopGroup
      bootstrap.group(bossGroup,workGroup);
      //设置要被实例化的为 NioServerSocketChannel 类
      bootstrap.channel(NioServerSocketChannel.class);
      //设置属性
      bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
      // 设置 NioServerSocketChannel 的处理器
      bootstrap.handler(new LoggingHandler(LogLevel.INFO));
      // 设置连入服务端的 Client 的 SocketChannel 的处理器
      bootstrap.childHandler(new ServerInitializer());
      // 绑定端口，并同步等待成功，即启动服务端
      ChannelFuture f = bootstrap.bind(8888).sync();
      // 监听服务端关闭，并阻塞等待
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

}
