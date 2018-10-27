package com.ycwu.netty.helloworld.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by wuyongchong on 2018/10/27.
 */
public class JacksonEncoder extends MessageToByteEncoder<Object> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
      throws Exception {

    ObjectMapper mapper = JacksonMapper.getInstance(); // create once, reuse
//		byte[] body =  mapper.writeValueAsBytes(msg); // 将对象转换为byte
//        out.writeBytes(body);  // 消息体中包含我们要发送的数据
    ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(out);
    mapper.writeValue(byteBufOutputStream, msg);
  }

}