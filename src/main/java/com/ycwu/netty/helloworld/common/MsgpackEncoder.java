package com.ycwu.netty.helloworld.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by wuyongchong on 2018/10/27.
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

  protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out)
      throws Exception {
    MessagePack msgpack = new MessagePack();
    out.writeBytes(msgpack.write(msg));
  }
}
