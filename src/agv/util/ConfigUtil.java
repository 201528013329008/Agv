package agv.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author China
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

}
