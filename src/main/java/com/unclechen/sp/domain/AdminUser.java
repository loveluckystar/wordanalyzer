package com.unclechen.sp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ��̨�����û�ʵ����
 * 
 * @author unclechen
 * 
 */
public class AdminUser implements Serializable {
	private static final long serialVersionUID = -1;

	public AdminUser() {
	}

	private String user_name;// �û���
	
	private String pwd;//�û�����

	private String real_name;// ��ʵ����

	private int grade;// Ȩ��:0��Ȩ�� 1�ռ�����Ա 2����Ա 3����

	private Date post_time;// ����ʱ��

	private Date last_time;// �ϴε�¼ʱ��

	private String id;// ����ԱID

	private int del; // �Ƿ���ɾ��:0δɾ�� 1���� 2��ɾ��

	private String email;// ����

	private String telephone;// �ֻ�

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
