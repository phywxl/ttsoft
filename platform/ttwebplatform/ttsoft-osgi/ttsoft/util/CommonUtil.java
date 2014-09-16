package ttsoft.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ttsoft.user.UserUtil;

public class CommonUtil {
	private static Logger _logger = LogManager.getLogger(UserUtil.class);
	
	/**
	 * 判断启用标志
	 * @param enabled
	 * @return
	 */
	public static boolean isEnabled(String enabled) {
		if (enabled == null || (enabled = enabled.trim()).equals("")) {
			_logger.warn("参数 enabled 为空！");
			return false;
		}
		
		if (enabled.equalsIgnoreCase("y") || enabled.equalsIgnoreCase("t") || enabled.equals("1") 
				|| enabled.equalsIgnoreCase("yes") || enabled.equalsIgnoreCase("true") || enabled.equalsIgnoreCase("是")) {
			return true;
		}
		
		return false;
	}
}
