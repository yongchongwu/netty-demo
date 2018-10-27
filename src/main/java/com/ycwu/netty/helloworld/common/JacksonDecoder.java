package com.ycwu.netty.helloworld.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Created by wuyongchong on 2018/10/27.
 */
public class JacksonDecoder<T> extends ByteToMessageDecoder {
  private final Class<T> clazz;
  /**
   *
   */
  public JacksonDecoder(Class<T> clazz) {
    this.clazz = clazz;
  }

  /*
   * (non-Javadoc)
   *
   * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.
   * ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in,
      List<Object> out) throws Exception {
    ByteBufInputStream byteBufInputStream = new ByteBufInputStream(in);
    out.add(JacksonMapper.getInstance().readValue(byteBufInputStream, clazz));

  }
}
