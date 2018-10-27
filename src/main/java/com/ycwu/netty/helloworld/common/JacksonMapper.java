package com.ycwu.netty.helloworld.common;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by wuyongchong on 2018/10/27.
 */
public class JacksonMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   *  create once, reuse
   * @return ObjectMapper 单例
   */
  public static ObjectMapper getInstance() {

    return MAPPER;
  }

}