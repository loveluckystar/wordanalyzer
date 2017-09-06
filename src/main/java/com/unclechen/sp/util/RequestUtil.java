package com.unclechen.sp.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

/**
 * request处理类
 */
public class RequestUtil {

	public static final String SEPERATOR = "##";

	/**
	 * 取得表单里面所有的参数/值,并保存在map里面返回(不上传图片),多个值的以符号隔开保存
	 * 
	 * @param request
	 * @return
	 */
	public static HashMap<String, String> getParam(HttpServletRequest request) {
		HashMap<String, String> row = new HashMap<String, String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 判断是否是表单文件类型
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			try {
				List items = sfu.parseRequest(request);// 从request得到所有上传域的列表
				if (null == items || items.size() == 0) {
					isMultipart = false;
				} else {
					for (Object object : items) {
						FileItem fileItem = (FileItem) object;
						if (null != fileItem && fileItem.isFormField()) {
							String key = fileItem.getFieldName();
							String value = fileItem.getString(request
									.getCharacterEncoding());
							// 添加对参数的trim处理
							if (value != null) {
								value = value.trim();
							}
							if (StringUtils.isNotBlank(row.get(key))) {
								row.put(key, row.get(key) + SEPERATOR + value);
							} else {
								row.put(key, value);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!isMultipart) {
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement().toString();
				String[] paramValues = request.getParameterValues(paramName);
				String paramValue = "";
				if (paramValues.length < 1) {
					paramValue =null;
				} else if (paramValues.length == 1) {
					paramValue = SpStringUtil.parseString(paramValues[0]);

				} else {
					for (int i = 0; i < paramValues.length; i++) {
						// 添加对参数的trim处理
						if (paramValues[i] != null)
							paramValue += SpStringUtil
									.parseString(paramValues[i]);
						if ((i + 1) < paramValues.length) {
							paramValue += SEPERATOR;
						}
					}
				}
				row.put(paramName, paramValue);
			}
		}
		return row;
	}

	public static <T> T buildBean(Class<T> clazz, Map parameterMap) {
		try {
			T bean = clazz.newInstance();
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(bean, parameterMap);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

	}

	public static <T> T smartBeanBuilder(Class<T> clazz,
			HttpServletRequest request) {
		HashMap<String, String> map = getParam(request);
		return buildBean(clazz, map);

	}
	
	
	  /**
	   * 获取Cookie的值
	   */
	  public static String getValue(HttpServletRequest request, String name) {
	    Cookie cookie = getCookie(request, name);
	    if (cookie == null)
	      return null;
	    String value = cookie.getValue();
	    if ("null".equals(value))
	      return null;
	    try {
	      return java.net.URLDecoder.decode(value, "gbk");
	    } catch (UnsupportedEncodingException e) {
	    }
	    return value;
	  }
	  /**
	   * 获取Cookie
	   */
	  public static Cookie getCookie(HttpServletRequest request, String name) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	      for (int i = 0; i < cookies.length; i++) {
	        if (cookies[i].getName().equals(name)) {
	          return cookies[i];
	        }
	      }
	    }
	    return null;
	  }
	  
	  /**
	   * 生成cookie
	   */
	  public static void setCookie(HttpServletResponse response, String domain,
	      String name, String value, int time, boolean secure) {
	    if (value == null)
	      value = "";
	    try {
	      value = java.net.URLEncoder.encode(value, "gbk");
	    } catch (UnsupportedEncodingException e) {
	    }
	    Cookie cookie = new Cookie(name, value);
	    cookie.setSecure(secure); // 表示是否Cookie只能通过加密的连接（即SSL）发送。
	    cookie.setPath("/"); // 设置Cookie适用的路径
	    cookie.setDomain(domain); // 设置Cookie适用的域
	    cookie.setMaxAge(time); // 设置Cookie有效时间
	    response.addCookie(cookie);
	  }

	  /** setCookie同名方法，简化版，关闭浏览器则无效 */
	  public static void setCookie(HttpServletResponse response, String name,
	      String value) {
	    setCookie(response,".unclechen.com", name, value, -1, false);
	  }

	  public static void setCookie(HttpServletResponse response, String name,
	      String value, int time) {
	    setCookie(response,".unclechen.com", name, value, time, false);
	  }


}
