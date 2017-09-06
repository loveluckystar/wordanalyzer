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
 * request������
 */
public class RequestUtil {

	public static final String SEPERATOR = "##";

	/**
	 * ȡ�ñ��������еĲ���/ֵ,��������map���淵��(���ϴ�ͼƬ),���ֵ���Է��Ÿ�������
	 * 
	 * @param request
	 * @return
	 */
	public static HashMap<String, String> getParam(HttpServletRequest request) {
		HashMap<String, String> row = new HashMap<String, String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// �ж��Ƿ��Ǳ��ļ�����
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			try {
				List items = sfu.parseRequest(request);// ��request�õ������ϴ�����б�
				if (null == items || items.size() == 0) {
					isMultipart = false;
				} else {
					for (Object object : items) {
						FileItem fileItem = (FileItem) object;
						if (null != fileItem && fileItem.isFormField()) {
							String key = fileItem.getFieldName();
							String value = fileItem.getString(request
									.getCharacterEncoding());
							// ��ӶԲ�����trim����
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
						// ��ӶԲ�����trim����
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
	   * ��ȡCookie��ֵ
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
	   * ��ȡCookie
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
	   * ����cookie
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
	    cookie.setSecure(secure); // ��ʾ�Ƿ�Cookieֻ��ͨ�����ܵ����ӣ���SSL�����͡�
	    cookie.setPath("/"); // ����Cookie���õ�·��
	    cookie.setDomain(domain); // ����Cookie���õ���
	    cookie.setMaxAge(time); // ����Cookie��Чʱ��
	    response.addCookie(cookie);
	  }

	  /** setCookieͬ���������򻯰棬�ر����������Ч */
	  public static void setCookie(HttpServletResponse response, String name,
	      String value) {
	    setCookie(response,".unclechen.com", name, value, -1, false);
	  }

	  public static void setCookie(HttpServletResponse response, String name,
	      String value, int time) {
	    setCookie(response,".unclechen.com", name, value, time, false);
	  }


}
