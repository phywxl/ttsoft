package ttsoft.osgi.base;

import java.util.List;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.osgi.context.BundleContextAware;

public class BundleContextPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements BundleContextAware {
	private Logger logger = LogManager.getLogger(BundleContextPropertyPlaceholderConfigurer.class);
	
	private BundleContext bundleContext;
	private List<String> propertyNames;
	
	public BundleContextPropertyPlaceholderConfigurer(){
	}
	
	public void initilize() {
		if (bundleContext == null) {
			return;
		}
		
		Properties p = new Properties();
		
		if (propertyNames != null && propertyNames.size() > 0) {
			String v = null;
			for (String pn : propertyNames) {
				if (pn == null || (pn = pn.trim()).equals("")) {
					continue;
				}
				if ((v = bundleContext.getProperty(pn)) != null ) {
					p.put(pn.trim(), v);
				} else {
					logger.warn("无法得到 " + pn + "配置项信息.");
				}
			}
		} else {
			logger.warn("没有配置  BundleContextPropertyPlaceholderConfigurer.propertyNames 属性信息.");
		}
		
		super.setProperties(p);
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	public List<String> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
	
	public String getProperty(String propertyName) {
		if (propertyName == null || (propertyName = propertyName.trim()).equals(""))
			return null;
		Properties[] ps = super.localProperties;
		if (ps == null) return null;
		String v = null;
		for (Properties p : ps) {
			if (p != null && (v = p.getProperty(propertyName)) != null && !v.trim().equals(""))
				return v;
		}
		return null;
	}
}
