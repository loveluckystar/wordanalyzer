package com.unclechen.sp.redis.session;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.unclechen.sp.redis.RedisManager;

public class HttpSessionSidWrapper extends HttpSessionWrapper {
  
  protected static final Logger logger = Logger.getLogger(HttpSessionSidWrapper.class);

  private String sid = "";
  private static int EXPIRE_SECONDS = 3600;//�������ʱ��60����
  
  public static String SESSIONID = "SP_MY_JSESSIONID";//cookie��key

  public HttpSessionSidWrapper(String sid, HttpSession session) {
    super(session);
    this.sid = sid;

  }
  public Object getAttribute(String name) {
    Object value = null;
//    		super.getAttribute(name);
//    if (value != null) {
//      logger.info("session_not_null:" + this.sid + ":" + name + ":" + super.getMaxInactiveInterval());
//      RedisManager.expire(this.sid, EXPIRE_SECONDS);//����session�������Чʱ��
//      return value;
//    }
    try {// redis
      byte[] bytesValue = RedisManager.hget(this.sid, name);
      if (bytesValue == null) {
//      	logger.info("session_null:" + this.sid + ":" + name);
        return value;
      }
//      logger.info("session_not_null:" + this.sid + ":" + name + ":" + super.getMaxInactiveInterval());
      RedisManager.expire(this.sid, EXPIRE_SECONDS);//
      value = RedisManager.deserialize(bytesValue);
      this.setMaxInactiveInterval(EXPIRE_SECONDS);
      this.setAttribute(name, value);//
      return value;
    } catch (Exception ex) {
      logger.error("error:" + ex.toString());
    }
//    logger.info("session_null:" + this.sid + ":" + name);
    return value;
  }

  public void invalidate() {
    super.invalidate();
    if (this.sid == null || this.sid.length() == 0)
      return;
    try {
      RedisManager.del(this.sid);
    } catch (Exception ex) {
      logger.error("error:" + ex.toString());
    }
  }

  public void removeAttribute(String name) {
    super.removeAttribute(name);
    try {
      RedisManager.hdel(this.sid, name);
    } catch (Exception ex) {
      logger.error("error:" + ex.toString());
    }
  }

  public void setAttribute(String name, Object value) {
    super.setMaxInactiveInterval(EXPIRE_SECONDS);
    super.setAttribute(name, value);
    if (value == null) {
      return;
    }
//    logger.info("session_set:" + this.sid + ":" + name + ";value=" + value);
    try {//
      RedisManager.hset(this.sid, name, RedisManager.serialize(value),EXPIRE_SECONDS);
    } catch (Exception ex) {
      logger.error("error:" + ex.toString());
    }

  }

}
