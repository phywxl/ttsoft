package ttsoft.osgi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.osgi.host.OsgiHost;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceListener;

public class TtSoftOsgiFilterChainProxy implements Filter {
	private FilterConfig filterConfig;
	private ServletContext servletContext;
	private BundleContext bundleContext;
	private ServiceListener listener;
	private List<Filter> initFilters;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//Filter配置，传进插件内的filter
		this.filterConfig = filterConfig;
		servletContext = filterConfig.getServletContext();
		//插件内filter是否初始化记录
		initFilters = new ArrayList<Filter>();
		bundleContext = (BundleContext)servletContext.getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
	}
	
	@Override
	public void destroy() {
		filterConfig = null;
		servletContext = null;
		initFilters.clear();
		initFilters = null;
		if (listener != null)
			bundleContext.removeServiceListener(listener);
		bundleContext = null;
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		if (sresponse.isCommitted())
			return;
		
		FilterInvocation fi = new FilterInvocation(srequest, sresponse, chain);
		
		//获得所有插件内特定的插件
		Filter[] filters = obtainAllDefinedFilters(servletContext);
		if (filters == null || filters.length == 0) {
			chain.doFilter(srequest, sresponse);
			return;
		}
		
		//初始化所有Filter
		initFilters(filters);
		
		//Filter链调用
		VirtualFilterChain virtualFilterChain = new VirtualFilterChain(fi, filters);
	    virtualFilterChain.doFilter(fi.getRequest(), fi.getResponse());
	}
	
	/**
	 * 获得所有插件内特定的插件集
	 * @param servletContext
	 * @return
	 */
	private Filter[] obtainAllDefinedFilters(ServletContext servletContext) {
		//BundleContext bundleContext = (BundleContext)servletContext.getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
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
					//如果是filter卸载，销毁并在集合中清除filter
					if (initFilters != null && (event.getType() == ServiceEvent.UNREGISTERING)) {
						if (initFilters.contains(obj)) {
							((Filter)obj).destroy();
							initFilters.remove(obj);
						}
					}
				}
			};
			try {
				//增加插件service监听
				bundleContext.addServiceListener(listener, "(osgi.web.filter=true)");
			} catch (InvalidSyntaxException e1) {
				e1.printStackTrace();
				bundleContext.addServiceListener(listener);
			}
		}
		
		try {
			//获得所有定义osgi.web.filter属性的插件service
			ServiceReference[] sres = bundleContext.getAllServiceReferences(Filter.class.getName(), "(osgi.web.filter=true)");
			if (sres == null || sres.length == 0)
				return null;

			Arrays.sort(sres, new Comparator<ServiceReference>(){
				@Override
				public int compare(ServiceReference r1, ServiceReference r2) {
					if (r1 == null && r2 == null)
						return 0;
					
					if (r1 != null && r2 == null)
						return 1;
					
					if (r1 == null && r2 != null)
						return -1;
					
					//filter优先级
					String p = (String)r1.getProperty("osgi.web.filter.priority");
					float i1 = p == null ? -Float.MAX_VALUE : Float.parseFloat(p);
					p = (String)r2.getProperty("osgi.web.filter.priority");
					float i2 = p == null ? -Float.MAX_VALUE : Float.parseFloat(p);
					
					if (i1 > i2)
						return 1;
					
					if (i1 < i2)
						return -1;
					
					return 0;
				}
			});
			
			List<Filter> l = new ArrayList<Filter>();
			ServiceReference sre = null;
			Object obj = null;
			for (int i = 0; i < sres.length; i++) {
				sre = sres[i];
				if (sre != null) {
					obj = bundleContext.getService(sre);
					if (obj instanceof Filter)
						l.add((Filter)obj);
				}
			}
			
			if (l.size() == 0)
				return null;
			
			return l.toArray(new Filter[0]);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 初始化所有filter
	 * @param filters
	 */
	private void initFilters(Filter[] filters) {
		if (filters == null)
			return;
		
		for (Filter filter : filters) {
			if (filter != null)
				try {
					//如果没有初始化filter
					if (!initFilters.contains(filter)) {
						filter.init(filterConfig);
						initFilters.add(filter);
					}
				} catch (ServletException e) {
					e.printStackTrace();
				}
		}
	}

	
	private class VirtualFilterChain implements FilterChain {
		private FilterInvocation fi;
		private Filter[] additionalFilters;
		private int currentPosition = 0;

		public VirtualFilterChain(FilterInvocation filterInvocation,
				Filter[] additionalFilters) {
			this.fi = filterInvocation;
			this.additionalFilters = additionalFilters;
		}

		private VirtualFilterChain() {
		}

		public void doFilter(ServletRequest request, ServletResponse response)
				throws IOException, ServletException {
			if (response.isCommitted())
				return;
			if (this.additionalFilters == null || this.currentPosition == this.additionalFilters.length) {
				this.fi.getChain().doFilter(request, response);
			} else {
				this.currentPosition += 1;

				this.additionalFilters[(this.currentPosition - 1)].doFilter(
						request, response, this);
			}
		}
	}
	
	private class FilterInvocation {
		private FilterChain chain;
		private ServletRequest request;
		private ServletResponse response;

		public FilterInvocation(ServletRequest request,
				ServletResponse response, FilterChain chain) {
			if ((request == null) || (response == null) || (chain == null)) {
				throw new IllegalArgumentException(
						"Cannot pass null values to constructor");
			}

			if (!(request instanceof HttpServletRequest)) {
				throw new IllegalArgumentException(
						"Can only process HttpServletRequest");
			}

			if (!(response instanceof HttpServletResponse)) {
				throw new IllegalArgumentException(
						"Can only process HttpServletResponse");
			}

			this.request = request;
			this.response = response;
			this.chain = chain;
		}

		public FilterChain getChain() {
			return this.chain;
		}

		public String getFullRequestUrl() {
			return UrlUtils.getFullRequestUrl(this);
		}

		public HttpServletRequest getHttpRequest() {
			return (HttpServletRequest) this.request;
		}

		public HttpServletResponse getHttpResponse() {
			return (HttpServletResponse) this.response;
		}

		public ServletRequest getRequest() {
			return this.request;
		}

		public String getRequestUrl() {
			return UrlUtils.getRequestUrl(this);
		}

		public ServletResponse getResponse() {
			return this.response;
		}

		public String toString() {
			return "FilterInvocation: URL: " + getRequestUrl();
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

		public static String getFullRequestUrl(FilterInvocation fi) {
			HttpServletRequest r = fi.getHttpRequest();

			return buildFullRequestUrl(r.getScheme(), r.getServerName(),
					r.getServerPort(), r.getContextPath(), r.getRequestURL()
							.toString(), r.getServletPath(), r.getRequestURI(),
					r.getPathInfo(), r.getQueryString());
		}

		public static String getRequestUrl(FilterInvocation fi) {
			HttpServletRequest r = fi.getHttpRequest();

			return buildRequestUrl(r.getServletPath(), r.getRequestURI(),
					r.getContextPath(), r.getPathInfo(), r.getQueryString());
		}
	}
}
