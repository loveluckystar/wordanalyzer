package com.unclechen.sp.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.PipelineBlock;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.TransactionBlock;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.unclechen.sp.Constant;

/**
 * redis
 * 
 * @author unclechen
 */
public class RedisManager {
	public static int EXPIRE_SECONDS = 3600;// ��Ч��60����

	public static int EXPIRE_DAY = 86400; // һ��

	public static int EXPIRE_HALFDAY = 43200; // ����

	protected static final Logger logger = Logger.getLogger(RedisManager.class);

	public RedisManager() {

	}

	public static String set(String key, String value, int seconds) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
		try {
			String status = jedis.set(key, value);
			if (seconds > 0)
				jedis.expire(key, seconds);
			return status;
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

	public static long sadd(String key, String value, int seconds) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
		try {
			long statusNum = jedis.sadd(key, value);
			if (seconds > 0)
				jedis.expire(key, seconds);
			return statusNum;
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

	public static String set(String key, String value) {
		return set(key, value, -1);
	}

	public static String get(String key) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static Boolean exists(String key) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static Long expire(String key, int seconds) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static java.util.List<Object> multi(String key,
			TransactionBlock jedisTransaction) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static java.util.List<Object> pipelined(String key,
			PipelineBlock pipelineBlock) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static Long hset(String hkey, String field, String value) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static Map<String, String> hgetAll(String hkey) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static byte[] hget(String hkey, String field) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static String hgetString(String hkey, String field) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
		try {
			return jedis.hget(hkey, field);
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

	public static Long hset(String hkey, String field, byte[] value, int seconds) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
		try {
			long statusNum = jedis.hset(hkey.getBytes(Protocol.CHARSET),
					field.getBytes(Protocol.CHARSET), value);
			if (seconds > 0)
				jedis.expire(hkey.getBytes(Protocol.CHARSET), seconds);
			return statusNum;
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

	public static String hmset(String hkey, Map<String, String> map, int seconds) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		try {
			String status = jedis.hmset(hkey, map);
			if (seconds > 0)
				jedis.expire(hkey, seconds);
			return status;
		} finally {
			if (jedis != null) {
				try {
					_pool.returnResource(jedis);
				} catch (Throwable thex) {
				}
			}
		}
	}

	public static String hmset(String hkey, Map<String, String> map) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		try {
			return jedis.hmset(hkey, map);
		} finally {
			if (jedis != null) {
				try {
					_pool.returnResource(jedis);
				} catch (Throwable thex) {
				}
			}
		}
	}

	public static Long hdel(String hkey, String field) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static Long del(String key) {
		JedisPool _pool = Constant.FACADE.jedisPool;
		Jedis jedis = _pool.getResource();
		// jedis.select(Constant.REDIS_INDEX);
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

	public static byte[] serialize(Object o) {
		ObjectOutputStream OOS = null;
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
			logger.error("���л�ʱ�������� ", ex);
		} finally {
			if (OOS != null) {
				try {
					OOS.close();
				} catch (Throwable thex) {
				}
			}
			if (BAOS != null) {
				try {
					BAOS.close();
				} catch (Throwable thex) {
				}
			}
		}
		return null;
	}

	public static Object deserialize(byte[] b) {
		if (b == null || b.length == 0)
			return null;
		ObjectInputStream OIS = null;
		ByteArrayInputStream BAIS = null;
		try {

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
		} finally {
			if (OIS != null) {
				try {
					OIS.close();
				} catch (Throwable thex) {
				}
			}
			if (BAIS != null) {
				try {
					BAIS.close();
				} catch (Throwable thex) {
				}
			}
		}
		return null;
	}

	/**
	 *
	 * 
	 * @param key
	 */
	public static Object getObject(String key) {
		Object obj = null;
		Jedis jedis = null;
		JedisPool pool = Constant.FACADE.jedisPool;
		try {
			jedis = pool.getResource();
			byte[] value = jedis.get(key.getBytes("utf-8"));
			if (value != null && value.length > 0) {
				obj = deserialize(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JedisConnectionException("redis ��ȡ���ݴ���!", e);
		} finally {
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		}
		return obj;
	}

	/**
	 * =
	 * 
	 * @param key
	 * @param obj
	 * @param seconds
	 */
	public static void setObject(String key, Object obj, int seconds) {
		Jedis jedis = null;
		JedisPool pool = Constant.FACADE.jedisPool;
		try {
			jedis = pool.getResource();
			jedis.set(key.getBytes("utf-8"), serialize(obj));
			if (seconds > 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JedisConnectionException("redis �洢���ݴ���!", e);
		} finally {
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		}
	}

	/**
	 *=
	 * 
	 * @param keys
	 * @return
	 */
	public static String[] getKeys(String key) {
		Jedis jedis = null;
		Set<String> set = null;
		String[] result = null;
		JedisPool pool = Constant.FACADE.jedisPool;
		try {
			jedis = pool.getResource();
			set = jedis.keys(key);
			if (set != null && set.size() > 0) {
				result = set.toArray(new String[] {});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JedisConnectionException("redis ����!", e);
		} finally {
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		}
		return result;
	}

	/**
	 *
	 * 
	 * @param key
	 * @return
	 */
	public static long delete(String... keys) {
		long value = -1;
		Jedis jedis = null;
		JedisPool pool = Constant.FACADE.jedisPool;
		try {
			jedis = pool.getResource();
			value = jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JedisConnectionException("redis ɾ�����ݴ���!", e);
		} finally {
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		}
		return value;
	}

	/**
	 *
	 * 
	 * @param key
	 */
	public static void clean(String key) {
		if (!StringUtils.isEmpty(key)) {
			String[] keys = getKeys(key);
			if (keys != null && keys.length > 0) {
				delete(keys);
			}
		}
	}

}
