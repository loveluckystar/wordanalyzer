/**
 * 
 */
package com.unclechen.sp.dao;

import java.util.List;

import com.unclechen.sp.domain.AdminUser;


/**
 * @author unclechen
 */
public interface IAdminUserDao {

	

	/**
	 * ???,?????????????,??????null
	 * 
	 * @param username
	 * @param passwd
	 * @return
	 */
	public AdminUser logIn(AdminUser user);
  
  /**
   * ????????
   * @return
   */
  public int updateLastTime(int id);
  
}
