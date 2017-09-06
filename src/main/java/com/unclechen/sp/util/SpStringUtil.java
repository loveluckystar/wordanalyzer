package com.unclechen.sp.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class SpStringUtil {
    /**
     * ????????
     * 
     * @param input
     *            ????
     * @param escapeHTML
     *            html???
     * @param escapeJavaScript
     *            JavaScript???
     * @param escapeXML
     *            xml???
     * @return
     */
    public static String parseString(String input, boolean escapeHTML, boolean escapeJavaScript, boolean escapeXML) {
        if(StringUtils.isBlank(input)){
            return "";
        }
        String output = input.trim();
        if (escapeHTML) {
            output = StringEscapeUtils.escapeHtml(output);
        }
        if (escapeJavaScript) {
            output = StringEscapeUtils.escapeJavaScript(output);
        }
        if (escapeXML) {
            output = StringEscapeUtils.escapeXml(output);
        }
        return output;
    }
    
    public static String parseString(String input) {
        return parseString(input, true, true, true);
    }
}
