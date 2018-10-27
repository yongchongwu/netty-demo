package com.ycwu.netty.helloworld.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import org.msgpack.MessagePack;

/**
 * Created by wuyongchong on 2018/10/27.
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg,
      List<Object> out) throws Exception {
    final int length = msg.readableBytes();
    byte[] b = new byte[length];
    msg.getBytes(msg.readerIndex(), b, 0, length);
    MessagePack msgpack = new MessagePack();
    out.add(msgpack.read(b));
  }
}
