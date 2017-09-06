package com.unclechen.sp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台管理用户实体类
 * 
 * @author unclechen
 * 
 */
public class AdminUser implements Serializable {
	private static final long serialVersionUID = -1;

	public AdminUser() {
	}

	private String user_name;// 用户名
	
	private String pwd;//用户密码

	private String real_name;// 真实姓名

	private int grade;// 权限:0无权限 1终极管理员 2管理员 3待定

	private Date post_time;// 创建时间

	private Date last_time;// 上次登录时间

	private String id;// 管理员ID

	private int del; // 是否已删除:0未删除 1禁用 2已删除

	private String email;// 邮箱

	private String telephone;// 手机

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getPost_time() {
		return post_time;
	}

	public void setPost_time(Date post_time) {
		this.post_time = post_time;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
