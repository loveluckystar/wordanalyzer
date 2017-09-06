package com.unclechen.sp.util;

import com.unclechen.sp.Constant;
import org.apache.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * redis管理器
 *
 * @author syyang
 */
public class RedisManager {
  public static int EXPIRE_SECONDS = 3600;// 有效期60分钟

  public static int EXPIRE_DAY = 86400;  //一天

  public static int EXPIRE_HALFDAY = 43200;  //半天

  public static int EXPIRE_QUARTER = 15*60;

  protected static final Logger logger = Logger.getLogger(RedisManager.class);
  public RedisManager() {

  }

  //设置单个字符串
  public static String set(String key, String value, int seconds) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      if (seconds > 0)
        jedis.expire(key, seconds);
      return jedis.set(key, value);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //将值放入队列
  public static long sadd(String key, String value, int seconds) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      if (seconds > 0)
        jedis.expire(key, seconds);
      return jedis.sadd(key, value);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //设置单个字符串
  public static String set(String key, String value) {
    return set(key, value, -1);
  }

  //获取单个对象
  public static String get(String key) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.get(key);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  // 判断是否存在
  public static Boolean exists(String key) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.exists(key);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //设置过期时间
  public static Long expire(String key, int seconds) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.expire(key, seconds);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //批量设置
  public static java.util.List<Object> multi(String key,
                                             TransactionBlock jedisTransaction) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.multi(jedisTransaction);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }

  }

  //管道批量设置
  public static java.util.List<Object> pipelined(String key,
                                                 PipelineBlock pipelineBlock) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.pipelined(pipelineBlock);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }

  }

  public static Long hincrby(String key,String field,int increment,int expiretime) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
    try {
      if (expiretime > 0) {
        jedis.expire(key.getBytes(Protocol.CHARSET), expiretime);
      }
      return jedis.hincrBy(key, field, increment);
    } catch(Exception e){
      e.printStackTrace();
      return -1l;
    }finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
          thex.printStackTrace();
        }
      }
    }

  }

  //设置hash
  public static Long hset(String hkey, String field, String value) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.hset(hkey, field, value);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //获取hash的所有对象
  public static Map<String, String> hgetAll(String hkey) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.hgetAll(hkey);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //获取hash的单个对象
  public static byte[] hget(String hkey, String field) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.hget(hkey.getBytes(Protocol.CHARSET),
              field.getBytes(Protocol.CHARSET));
    } catch (IOException e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //获取hash的单个对象
  public static String hgetString(String hkey, String field) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.hget(hkey,field);
    } catch (Exception e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //设置hash的单个对象
  public static Long hset(String hkey, String field, byte[] value, int seconds) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      if (seconds > 0)
        jedis.expire(hkey.getBytes(Protocol.CHARSET), seconds);
      return jedis.hset(hkey.getBytes(Protocol.CHARSET),
              field.getBytes(Protocol.CHARSET), value);
    } catch (IOException e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //删除hash的单个对象
  public static Long hdel(String hkey, String field) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.hdel(hkey, field);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //删除
  public static Long del(String key) {
    JedisPool _pool = Constant.FACADE.jedisPool;
    Jedis jedis = _pool.getResource();
//    jedis.select(Constant.REDIS_INDEX);
    try {
      return jedis.del(key.getBytes(Protocol.CHARSET));
    } catch (IOException e) {
      throw new JedisConnectionException(e);
    } finally {
      if (jedis != null) {
        try {
          _pool.returnResource(jedis);
        } catch (Throwable thex) {
        }
      }
    }
  }

  //序列化
  public static byte[] serialize(Object o){
    ObjectOutputStream OOS = null;
    // 序列化后数据流给ByteArrayOutputStream 来保存。
    // ByteArrayOutputStream 可转成字符串或字节数组
    ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
    try {
      OOS = new ObjectOutputStream(BAOS);
      OOS.writeObject(o);
      byte[] abc = BAOS.toByteArray();
      OOS.close();
      BAOS.close();
      return abc;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      logger.error("序列化时产生错误 ", ex);
    }finally {
      if(OOS != null){
        try{
          OOS.close();
        }catch(Throwable thex){}
      }
      if(BAOS != null){
        try{
          BAOS.close();
        }catch(Throwable thex){}
      }
    }
    return null;
  }

  //反序列化
  public static Object deserialize(byte[] b){
    if(b == null || b.length == 0) return null;
    ObjectInputStream OIS = null;
    ByteArrayInputStream BAIS = null;
    try{

      BAIS = new ByteArrayInputStream(b);
      OIS = new ObjectInputStream(BAIS);
      Object o = OIS.readObject();
      OIS.close();
      BAIS.close();
      return o;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally {
      if(OIS != null){
        try{
          OIS.close();
        }catch(Throwable thex){}
      }
      if(BAIS != null){
        try{
          BAIS.close();
        }catch(Throwable thex){}
      }
    }
    return null;
  }


  /**
   * 获取对象
   * @param key
   */
  public static Object getObject(String key)
  {
    Object obj = null;
    Jedis jedis = null;
    JedisPool pool = Constant.FACADE.jedisPool;
    try
    {
      jedis = pool.getResource();
      byte[] value = jedis.get(key.getBytes("utf-8"));
      if(value!=null&&value.length>0)
      {
        obj = deserialize(value);
      }
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new JedisConnectionException("redis 获取数据错误!",e);
    }
    finally
    {
      if(jedis!=null)
      {
        pool.returnBrokenResource(jedis);
      }
    }
    return obj;
  }



  /**
   * 存储单个对象
   * @param key
   * @param obj
   * @param seconds
   */
  public static void setObject(String key,Object obj,int seconds)
  {
    Jedis jedis = null;
    JedisPool pool = Constant.FACADE.jedisPool;
    try
    {
      jedis = pool.getResource();
      jedis.set(key.getBytes("utf-8"), serialize(obj));
      if(seconds>0)
      {
        jedis.expire(key, seconds);
      }
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new JedisConnectionException("redis 存储数据错误!",e);
    }finally
    {
      if(jedis!=null)
      {
        pool.returnBrokenResource(jedis);
      }
    }
  }



  /**
   * 模糊匹配所有符合条件的key
   * @param keys
   * @return
   */
  public static String[] getKeys(String key)
  {
    Jedis jedis = null;
    Set<String> set = null;
    String[] result = null;
    JedisPool pool = Constant.FACADE.jedisPool;
    try
    {
      jedis = pool.getResource();
      set = jedis.keys(key);
      if(set!=null&&set.size()>0)
      {
        result = set.toArray(new String[]{});
      }
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new JedisConnectionException("redis 错误!",e);
    }finally
    {
      if(jedis!=null)
      {
        pool.returnBrokenResource(jedis);
      }
    }
    return result;
  }


  /**
   * 删除值
   * @param key
   * @return
   */
  public static long delete(String ...keys)
  {
    long value = -1;
    Jedis jedis = null;
    JedisPool pool = Constant.FACADE.jedisPool;
    try
    {
      jedis = pool.getResource();
      value = jedis.del(keys);
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new JedisConnectionException("redis 删除数据错误!",e);
    }
    finally
    {
      if(jedis!=null)
      {
        pool.returnBrokenResource(jedis);
      }
    }
    return value;
  }

  /**
   * 模糊删除redis取值
   * @param key
   */
  public static void clean(String key)
  {
    if(!StringUtil.isEmpty(key))
    {
      String[] keys = getKeys(key);
      if(keys!=null&&keys.length>0)
      {
        delete(keys);
      }
    }
  }

}