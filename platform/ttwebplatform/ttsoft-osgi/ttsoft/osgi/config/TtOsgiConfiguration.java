package ttsoft.osgi.config;

import java.io.IOException;
import java.util.Properties;

public class TtOsgiConfiguration {
	//JSP编译选项
	public static String JSP_COMPILER_OUT_DIR      = "jsp.compiler.out.dir"; //输出目录
	public static String JSP_COMPILER_OUT_JAVA     = "jsp.compiler.out.java";//是否输出JSP源码
	public static String JSP_COMPILER_ENCODING_SRC = "jsp.compiler.encoding.src";
	public static String JSP_COMPILER_ENCODING_OUT = "jsp.compiler.encoding.out";
	
	//Felix额外系统包
	public static String SYSTEM_PACKAGTE_INCLUDES = "system.package.includes";
	
	//插件资源搜索
	public static String PLUGIN_RESOURCE_SEARCH_HASSYMBOLICNAME = "plugin.resource.search.hassymbolicname";
	public static String PLUGIN_RESOURCE_SEARCH_PREFIX          = "plugin.resource.search.prefix";
	public static String PLUGIN_RESOURCE_SEARCH_TYPE            = "plugin.resource.search.type";
	
	//保持单例
	private static TtOsgiConfiguration config;
		
	//配置信息
	private Properties properties;
	
	/**
	 * 构造方法私有
	 */
	private TtOsgiConfiguration() {
	}
	
	/**
	 * 获得配置对象
	 * @return
	 */
	public static TtOsgiConfiguration getConfig() {
		if (config == null) {
			config = new TtOsgiConfiguration();
			config.init();
		}
		
		return config;
	}
	
	/**
	 * 初始化配置信息
	 */
	public void init() {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(getClass().getResourceAsStream("/ttsoft-osgi.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 刷新配置信息
	 */
	public void refresh() {
		this.properties = null;
		this.init();
	}
	
	/**
	 * 获得String类型配置
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String str = null;
		Object obj = this.properties.get(key);
		if (obj != null && !(obj instanceof String))
			str = obj.toString();
		else
			str = (String)obj;
		return str;
	}
	
	/**
	 * 获得String类型配置，缺省为defaultv
	 * @param key
	 * @return
	 */
	public String getString(String key, String defaultv) {
		String str = this.getString(key);
		if (str == null || str.trim().equals(""))
			return defaultv;
		return str;
	}
	
	/**
	 * 获得boolean类型配置，缺省为false
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		String value = this.getString(key);
		if (value == null || value.trim().equals(""))
			return false;
		
		value = value.trim();
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("y") 
				|| value.equalsIgnoreCase("true") || value.equalsIgnoreCase("t"))
			return true;
		
		return false;
	}
	
	/**
	 * 获得有特定前缀的键值集
	 * @param key
	 * @return
	 */
	public java.util.Enumeration<String> getStrings(String key) {
		java.util.List<String> l = new java.util.ArrayList<String>();
		
		if (key != null && !key.trim().equals("")) {
			key = key.trim();
			
			java.util.Enumeration<Object> enu = this.properties.keys();
			Object okey = null;
			String tkey = null;
			while (enu.hasMoreElements()) {
				okey = enu.nextElement();
				if (okey != null && okey instanceof String) {
					tkey = (String)okey;
					if (tkey.trim().startsWith(key)) {
						l.add(this.getString(tkey));
					}
				}
			}
		}
		
		return java.util.Collections.enumeration(l);
	}
	
	/**
	 * 获得所有键集
	 * @return
	 */
	public java.util.Enumeration<String> getNames() {
		java.util.List<String> l = new java.util.ArrayList<String>();
		
			
		java.util.Enumeration<Object> enu = this.properties.keys();
		Object okey = null;
		String tkey = null;
		while (enu.hasMoreElements()) {
			okey = enu.nextElement();
			if (okey != null && okey instanceof String) {
				tkey = (String)okey;
				l.add(tkey);
			}
		}
		
		return java.util.Collections.enumeration(l);
	}
	
	
	
	public static void main(String[] args) {
		//Configuration.getConfig();
		//System.out.println("ss.ss".replaceAll("\\.", "/"));
		//System.out.println("s.jpg".endsWith(".jpg"));
		
		java.util.Enumeration<String> enums = TtOsgiConfiguration.getConfig().getStrings(PLUGIN_RESOURCE_SEARCH_PREFIX);
		if (enums != null) {
			while (enums.hasMoreElements())
				System.out.println(enums.nextElement());
		}
		
		/*java.util.Enumeration<String> enums = VictorysoftOsgiConfiguration.getConfig().getNames();
		if (enums != null) {
			while (enums.hasMoreElements())
				System.out.println(enums.nextElement());
		}*/
	}
}
