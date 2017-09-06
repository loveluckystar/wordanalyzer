/**
 * 
 */
package com.unclechen.sp;

import com.unclechen.sp.dao.INewsDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

import com.unclechen.sp.dao.IAdminUserDao;

/**
 * @author unclechen
 */

@Component("facade")
public class Facade {
	protected static final Logger log = Logger.getLogger(Facade.class);

	@Autowired
	public IAdminUserDao adminUserDao;

	@Autowired
	public INewsDao newsDao;

	@Autowired
	public JedisPool jedisPool;

//	@Autowired
//	public MongoTemplate mongoTemplate;

}
