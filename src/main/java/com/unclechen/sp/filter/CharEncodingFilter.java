/**
 * 
 */
package com.unclechen.sp.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author unclechen
 * 
 */
public class CharEncodingFilter implements Filter {

	private static Log mLogger = LogFactory.getFactory().getInstance(
			CharEncodingFilter.class);
	
  /**
   * The filter configuration object we are associated with.  If this value
   * is null, this filter instance is not currently configured.
   */
  protected FilterConfig filterConfig = null;

  /**
   * The default character encoding to set for requests that pass through
   * this filter.
   */
  protected String encoding = null;

  /**
   * Should a character encoding specified by the client be ignored?
   */
  protected boolean ignore = true;


  /**
   * Place this filter into service.
   *
   * @param filterConfig The filter configuration object
   */
  public void init(FilterConfig filterConfig) throws ServletException {

  		this.filterConfig = filterConfig;
      this.encoding = filterConfig.getInitParameter("encoding");
      String value = filterConfig.getInitParameter("ignore");
      if (value == null)
          this.ignore = true;
      else if (value.equalsIgnoreCase("true"))
          this.ignore = true;
      else if (value.equalsIgnoreCase("yes"))
          this.ignore = true;
      else
          this.ignore = false;

  }


  /**
   * Take this filter out of service.
   */
  public void destroy() {
      this.encoding = null;
      this.filterConfig = null;
  }


  /**
   * Select and set (if specified) the character encoding to be used to
   * interpret request parameters for this request.
   *
   * @param request The servlet request we are processing
   * @param result The servlet response we are creating
   * @param chain The filter chain we are processing
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet error occurs
   */

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		//	 Conditionally select and set the character encoding to be used
    if (ignore || (req.getCharacterEncoding() == null)) {			
			try {
        if (encoding != null){
        	mLogger.info(encoding);
        	req.setCharacterEncoding(encoding);
        }
			} catch (UnsupportedEncodingException e) {
				// This should never happen since UTF-8 is a Java-specified required encoding.
				throw new ServletException("Can't set incoming encoding");
			}
	
			// Keep JSTL and Struts Locale's in sync
			// NOTE: The session here will get created if it is not present. This code was taken from its
			// earlier incarnation in RequestFilter, which also caused the session to be created.
			// HttpSession session = ((HttpServletRequest) req).getSession();
			// if (mLogger.isDebugEnabled()) mLogger.debug("Synchronizing JSTL and Struts locales");
			// Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
			// if (locale == null)
			// {
			// locale = req.getLocale();
			// }
			// if (req.getParameter("locale") != null)
			// {
			// locale = new Locale(req.getParameter("locale"));
			// }
			// session.setAttribute(Globals.LOCALE_KEY, locale);
			// Config.set(session, Config.FMT_LOCALE, locale);
    }
		chain.doFilter(req, res);
	}

}