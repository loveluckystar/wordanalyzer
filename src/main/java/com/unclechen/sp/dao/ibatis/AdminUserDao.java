/**
 *
 */
package com.unclechen.sp.dao.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.unclechen.sp.dao.IAdminUserDao;
import com.unclechen.sp.domain.AdminUser;
import com.unclechen.sp.util.MD5Util;

/**
 * @author unclechen
 * 
 */
@Repository
public class AdminUserDao implements IAdminUserDao {

	@Autowired
	@Qualifier("iBatisTemplate")
	private SqlMapClientTemplate iBatisTemplate;

	@Override
	public AdminUser logIn(AdminUser user) {
		user.setPwd(MD5Util.MD5AdminPWD(user.getUser_name(), user.getPwd()));
		return (AdminUser) iBatisTemplate.queryForObject("admin_user.getAdminByLogin", user);
	}

	@Override
	public int updateLastTime(int id) {
		return 0;
	}

}
