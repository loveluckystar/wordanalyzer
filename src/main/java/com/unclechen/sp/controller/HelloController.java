package com.unclechen.sp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unclechen.sp.domain.AdminUser;
import com.unclechen.sp.mongodb.index.LocusIndex;
import com.unclechen.sp.redis.session.HttpServletRequestWrapper;

@Controller
public class HelloController {
	private static Logger log = Logger.getLogger(HelloController.class);

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("hello.action")
	public String hello(HttpServletRequest request, HttpServletResponse response) {
		String userflag=request.getParameter("userflag");
		log.info("userflag:"+userflag);
		request.setAttribute("userflag",userflag);
		if(userflag.equals("1")){
			request.setAttribute("user_name",((AdminUser)new HttpServletRequestWrapper(request).getSession().getAttribute("adminuser")).getUser_name());	
		}
		LocusIndex.createLocus("2016-01-24", "127.0.0.1", "0009", "nanchong");
		return "hello";
	}
}
