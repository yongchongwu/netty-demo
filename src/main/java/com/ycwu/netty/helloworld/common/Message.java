package com.ycwu.netty.helloworld.common;

import java.io.Serializable;

/**
 * Created by wuyongchong on 2018/10/27.
 */
@org.msgpack.annotation.Message
public class Message implements Serializable{
  private int code;
  private String msg;
  private String data;

  public Message() {
  }

  public Message(int code) {
    this.code = code;
  }

  public Message(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Message(int code, String msg, String data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Message{" +
        "code=" + code +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }
}
