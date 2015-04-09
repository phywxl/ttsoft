package ttsoft.osgi.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TtSoftOsgiSpringMVCRequestWrapper extends HttpServletRequestWrapper {
	private String symbolicName;
	private String ver;
	
	public TtSoftOsgiSpringMVCRequestWrapper(HttpServletRequest request, String symbolicName, String ver) {
		super(request);
		this.symbolicName = symbolicName;
		this.ver = ver;
	}

	@Override
	public String getPathInfo() {
		String str = super.getPathInfo(); 
		str = this.trimBundle(str);
		return str;
	}

	@Override
	public String getRequestURI() {
		String str = super.getRequestURI();
		str = this.trimBundle(str);
		
		return str;
	}

	@Override
	public StringBuffer getRequestURL() {
		StringBuffer sb = super.getRequestURL(); 
		String s = this.trimBundle(sb.toString());
		return new StringBuffer(s);
	}

	private String trimBundle(String str) {
		if (str == null) return null;
		
		if (str.indexOf("/" + this.symbolicName) > 0) {
			str = str.replaceFirst("/" + this.symbolicName, "");
		}
		if (this.ver != null) {
			if (str.indexOf("/" + this.ver) > 0) {
				str = str.replaceFirst("/" + this.ver, "");
			}
		}
		
		return str;
	}
}
