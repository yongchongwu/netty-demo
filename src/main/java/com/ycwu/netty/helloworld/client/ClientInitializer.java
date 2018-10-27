package com.ycwu.netty.helloworld.client;

import com.ycwu.netty.helloworld.common.MsgpackDecoder;
import com.ycwu.netty.helloworld.common.MsgpackEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuyongchong on 2018/10/26.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

  private static final int WRITE_IDEL_TIME_OUT = 5; // 写超时

  private static final StringDecoder DECODER = new StringDecoder();
  private static final StringEncoder ENCODER = new StringEncoder();

  private static final ClientHandler CLIENT_HANDLER = new ClientHandler();


  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline pipeline = socketChannel.pipeline();
    //这里设置通过增加包头表示报文长度来避免粘包
    pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0, 2,0,2));
    //增加解码器
    pipeline.addLast("msgpack decoder",new MsgpackDecoder());
    //这里设置读取报文的包头长度来避免粘包
    pipeline.addLast("frameEncoder",new LengthFieldPrepender(2));
    //增加编码器
    pipeline.addLast("msgpack encoder",new MsgpackEncoder());
    //超时处理
    pipeline.addLast(new IdleStateHandler(0,
        WRITE_IDEL_TIME_OUT, 0, TimeUnit.SECONDS));
    //业务处理
    pipeline.addLast(CLIENT_HANDLER);
  }
}
