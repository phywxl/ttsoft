package ttsoft.osgi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.osgi.host.OsgiHost;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class TtSoftOsgiServletProxy extends GenericServlet {
	private BundleContext bundleContext;
	private ServiceListener listener;
	private List<Servlet> initServlets;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.initServlets = new ArrayList<Servlet>();
		bundleContext = (BundleContext)config.getServletContext().getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
	}

	@Override
	public void destroy() {
		initServlets = null;
		if (listener != null)
			bundleContext.removeServiceListener(listener);
		bundleContext = null;
		
		super.destroy();
	}
	
	@Override
	public void service(ServletRequest srequest, ServletResponse sresponse)
			throws ServletException, IOException {
		if (sresponse.isCommitted())
			return;
		
		HttpServletRequest request = (HttpServletRequest)srequest;
		
		Map<Pattern, Servlet> servlets = obtainAllDefinedServlets(getServletContext());
		if (servlets == null || servlets.size() == 0)
			return;
		
		initServlets(servlets);
		
		String url =  UrlUtils.buildRequestUrl(request.getServletPath(), request.getRequestURI(),
				request.getContextPath(), request.getPathInfo(), request.getQueryString());
		if (url == null || (url = url.trim()).equals("") )
			return;
		
		for (Pattern pattern : servlets.keySet()) {
			if (pattern.matcher(url).matches()) {
				if (servlets.get(pattern) != null) {
					servlets.get(pattern).service(srequest, sresponse);
					return;
				}
			}
		}
	}
	
	
	

	private Map<Pattern, Servlet> obtainAllDefinedServlets(ServletContext servletContext) {
		if (bundleContext == null) {
			return null;
		}
		
		//插件service监听
		if (listener == null) {
			listener = new ServiceListener() {
				@Override
				public void serviceChanged(ServiceEvent event) {
					ServiceReference sre = event.getServiceReference();
					Object obj = sre.getBundle().getBundleContext().getService(sre);
					//如果是servlet卸载，销毁并在集合中清除servlet
					if (initServlets != null && (event.getType() == ServiceEvent.UNREGISTERING)) {
						if (initServlets.contains(obj)) {
							((Servlet)obj).destroy();
							initServlets.remove(obj);
						}
					}
				}
			};
			try {
				//增加插件service监听
				bundleContext.addServiceListener(listener, "(osgi.web.servlet=true)");
			} catch (InvalidSyntaxException e1) {
				e1.printStackTrace();
				bundleContext.addServiceListener(listener);
			}
		}
		
		//获得所有定义osgi.web.filter属性的插件service
		ServiceReference[] sres;
		try {
			sres = bundleContext.getAllServiceReferences(Servlet.class.getName(), "(osgi.web.servlet=true)");
			if (sres == null || sres.length == 0)
				return null;
			
			Map<Pattern, Servlet> l = new HashMap<Pattern, Servlet>();
			ServiceReference sre = null;
			Object obj = null;
			String urlPattern = null;
			for (int i = 0; i < sres.length; i++) {
				sre = sres[i];
				if (sre != null) {
					obj = bundleContext.getService(sre);
					if (obj instanceof Servlet) {
						urlPattern = (String)sre.getProperty("osgi.web.servlet.urlPattern");
						if (urlPattern == null || (urlPattern = urlPattern.trim()).equals(""))
							continue;
						l.put(Pattern.compile(urlPattern), (Servlet)obj);
					}
				}
			}
			
			if (l.size() == 0)
				return null;
			
			return l;
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void initServlets(Map<Pattern, Servlet> servlets) {
		if (servlets == null || servlets.size() == 0)
			return;
		
		for (Servlet servlet : servlets.values()) {
			if (servlet != null) {
				try {
					if (!initServlets.contains(servlet)) {
						servlet.init(this.getServletConfig());
						initServlets.add(servlet);
					}
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private final static class UrlUtils {
		private static String buildFullRequestUrl(String scheme,
				String serverName, int serverPort, String contextPath,
				String requestUrl, String servletPath, String requestURI,
				String pathInfo, String queryString) {
			boolean includePort = true;

			if (("http".equals(scheme.toLowerCase())) && (serverPort == 80)) {
				includePort = false;
			}

			if (("https".equals(scheme.toLowerCase())) && (serverPort == 443)) {
				includePort = false;
			}

			return scheme
					+ "://"
					+ serverName
					+ ((includePort) ? ":" + serverPort : "")
					+ contextPath
					+ buildRequestUrl(servletPath, requestURI, contextPath,
							pathInfo, queryString);
		}

		private static String buildRequestUrl(String servletPath,
				String requestURI, String contextPath, String pathInfo,
				String queryString) {
			String uri = servletPath;

			if (uri == null) {
				uri = requestURI;
				uri = uri.substring(contextPath.length());
			}

			return uri
					+ ((pathInfo == null) ? "" : pathInfo)
					+ ((queryString == null) ? "" : new StringBuffer()
							.append("?").append(queryString).toString());
		}
	}

}
