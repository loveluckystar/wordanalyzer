/**
 * Version    Date      By        Description
 * ======================================================================================
 *            20090608  hrchen    添加方法getFilePath(String)和uploadNotAdd(Row, HttpServletRequest, String)
 */
package com.unclechen.sp.controller;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public abstract class BaseController{

  protected static final Logger logger = Logger.getLogger(BaseController.class);

  protected static final int BUFFER_SIZE = 10 * 1024;

  protected static final String[] IMAGETYPE = { "image/jpeg", "image/jpg",
          "image/pjpeg", "image/gif", "image/png", "image/x-png" };


  protected void setHeader(HttpServletResponse response) {
    response.setContentType("text/html;charset=GBK");

  }

  protected void noCache(HttpServletResponse resp){
    resp.setHeader("Pragma", "No-cache");
    resp.setHeader("Cache-Control", "no-cache");
    resp.setDateHeader("Expires", 0);
  }

  protected void renderText(String text, HttpServletResponse response ) {
    // 这里不加的话，AJAX会缓存每次取的数据，从而造成数据不能更新
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    setHeader(response);
    try {
      response.getWriter().write(text);
    } catch (Exception e) {
      logger.error(e);
    }
  }

  /**
   * 获得前一页面
   *
   * @return
   */
  protected String getReferer(HttpServletRequest request) {
    return request.getHeader("REFERER");
  }

  /** 页面重定向 */
  protected String sendRedirect(String url, HttpServletResponse response) {
    try {
      response.sendRedirect(url);
    } catch (Exception e) {
      logger.error(e);
    }
    return null;
  }

  /**
   * 弹窗
   *
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderScriptText(String content, String url, HttpServletResponse response) {
    String text = "<script>alert('" + content + "');window.location.href='"
            + url + "';</script>";
    renderText(text, response);
  }

  /**
   * 弹窗
   *
   * @param response
   * @param content
   * @param url
   * @throws Exception
   */
  public void renderParentJs(String content, String url, HttpServletResponse response) {
    String text = "<script>alert('" + content
            + "');parent.window.location.href='" + url + "';</script>";
    renderText(text, response);
  }

  /**
   * 输出JS代码
   *
   * @param response
   * @param jscode
   * @throws Exception
   */
  public void renderScript(String jscode, HttpServletResponse response) {
    String text = "<script>" + jscode + "</script>";
    renderText(text, response);
  }

  /**
   * 子窗口返回数据，把值传给父窗口后，关闭子窗口
   *
   * @param response
   * @param content
   * @param
   * @throws Exception
   */
  public void renderScriptText(String content, HttpServletResponse response) {
//    renderText("<script>document.write('<div style=\"font-size:12px;text-align:center;color:red\">"
//            + content
//            + "<br><input type=\"button\" value=\"确定并关闭窗口\" onclick=\"parent.location.href=parent.location.href;parent.Dialog.close();\"></div>');</script>", response);
  }

  /**
   * 弹出提示框后返回上一页面并刷新（保证了参数与第一次进入上一页面相同）
   *
   * @param content
   */
  public void alert(String content, HttpServletResponse response) {
    String text = "<script>alert('" + content
            + "');window.history.back();location.reload();</script>";
    renderText(text, response);
  }


  protected boolean isPost(HttpServletRequest request) {
    if (request.getMethod().indexOf("P") > -1) {
      return true;
    }
    return false;
  }

}
