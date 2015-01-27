package ttsoft.osgi.servlet;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class TtSoftOsgiSpringMVCBundleHolder {
	private String symbolicName;
	private String ver;
	private Bundle bundle;
	private HttpServletRequest request;
	private ConfigurableApplicationContext applicationContext;
	private WebApplicationContext webApplicationContext;
	
	public TtSoftOsgiSpringMVCBundleHolder(String symbolicName, String ver, Bundle bundle, HttpServletRequest request, ConfigurableApplicationContext applicationContext) {
		this.symbolicName = symbolicName; this.ver = ver; this.bundle = bundle; this.request = request; this.applicationContext = applicationContext;
	}

	public WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}
	public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}
	public Bundle getBundle() {
		return bundle;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public String getSymbolicName() {
		return symbolicName;
	}
	public String getVer() {
		return ver;
	}
	
}
