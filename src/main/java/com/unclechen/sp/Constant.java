/**
 * 
 */
package com.unclechen.sp;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author unclechen
 */
public class Constant {
	public static Facade FACADE = null;
	public static BeanFactory BF = null;
	public static int PAGESIZE = 20;
	public static String SEPARATOR = "##";

	public static String READ = "R";
	public static String WRITE = "W";

	public static String[] abc = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };

	static {
		if (BF == null) {
			BF = ApplicationContextInit.getApplicationContext();
		}
		FACADE = (Facade) BF.getBean("facade");
	}

	public static String[] QQnewsTypeArray = {"ent","sports","finance","tech","games","auto","edu","house"};

	/**redis前缀 begin**/
	public static String TYPE_KEYWORD_PRE = "keyword:type";
	public static String ONLY_KEYWORD_PRE = "keyword:only";
	public static String SUMMARY_KEYWORD_PRE = "keyword:summary";
	public static String PROBABLILY_KEYWORD_PRE = "keyword:probablily";
}
