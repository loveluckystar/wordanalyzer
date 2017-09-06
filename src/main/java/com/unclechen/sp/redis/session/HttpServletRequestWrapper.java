package com.unclechen.sp.redis.session;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import redis.clients.jedis.Protocol;

import com.unclechen.sp.util.MD5Util;
import com.unclechen.sp.util.RequestUtil;

public class HttpServletRequestWrapper extends
		javax.servlet.http.HttpServletRequestWrapper {
	
	public static final String SESSION_PRE= "sp_";

	String sid = "";


	public HttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		String sid = RequestUtil.getValue(request, HttpSessionSidWrapper.SESSIONID);
		this.sid = SESSION_PRE + sid;
	}
	
	public HttpServletRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		String sid = RequestUtil.getValue(request, HttpSessionSidWrapper.SESSIONID);
		if(null == sid){
			sid = UUID.randomUUID().toString();
			RequestUtil.setCookie(response, HttpSessionSidWrapper.SESSIONID, sid);
		}
		this.sid = SESSION_PRE + sid;
	}
	
	public HttpServletRequestWrapper(HttpServletRequest request,
			HttpServletResponse response, String userName) {
		super(request);
		sid = UUID.randomUUID().toString() + MD5Util.MD5(userName);
		RequestUtil.setCookie(response, HttpSessionSidWrapper.SESSIONID, sid);
		this.sid = SESSION_PRE + sid;
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionSidWrapper(this.sid,super.getSession(create));
	}

	public HttpSession getSession() {
		return new HttpSessionSidWrapper(this.sid,super.getSession());
	}

}
