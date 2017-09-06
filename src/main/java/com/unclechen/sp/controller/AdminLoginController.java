package com.unclechen.sp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unclechen.sp.Constant;
import com.unclechen.sp.domain.AdminUser;
import com.unclechen.sp.redis.session.HttpServletRequestWrapper;
import com.unclechen.sp.util.RequestUtil;

@Controller
public class AdminLoginController {
	private static Logger log = Logger.getLogger(AdminLoginController.class);
	

	@RequestMapping("tologin.action")
	public String toLogin(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	
	@RequestMapping("login.action")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		AdminUser user=RequestUtil.smartBeanBuilder(AdminUser.class, request);
		user=Constant.FACADE.adminUserDao.logIn(user);
		if(user!=null){
			HttpSession session=new HttpServletRequestWrapper(request, response, user.getUser_name())
			.getSession();
			session.setMaxInactiveInterval(3600);
			session.setAttribute("adminuser", user);
			
			return "redirect:/hello.action?userflag=1&user_name="+user.getUser_name();
		}
		return "redirect:/hello.action?userflag=2";
	}
	
}
