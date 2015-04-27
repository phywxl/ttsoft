package ttsoft.osgi.servlet;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import org.apache.struts2.JSPLoader;
import org.apache.struts2.JSPRuntime;
import org.apache.struts2.osgi.DefaultBundleAccessor;
import org.osgi.framework.Bundle;
import org.springframework.web.servlet.view.JstlView;

public class TtSoftOsgiSpringMVCJspView extends JstlView {
	private JSPLoader jspLoader = new JSPLoader();
	
	private String prefix;
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Determine which request handle to expose to the RequestDispatcher.
		HttpServletRequest requestToExpose = getRequestToExpose(request);

		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);

		// Expose helpers as request attributes, if any.
		//exposeHelpers(requestToExpose);

		// Determine the path for the request dispatcher.
		String dispatcherPath = prepareForRendering(requestToExpose, response);
		
		TtSoftOsgiSpringMVCBundleHolder holder = TtSoftOsgiSpringMVCContext.getContext() == null ? null : TtSoftOsgiSpringMVCContext.getContext().getBundleHolder() == null ? null : TtSoftOsgiSpringMVCContext.getContext().getBundleHolder();
		if (holder != null) {
			if (dispatcherPath != null) {
				String path = dispatcherPath;
				//String path = request.getPathInfo();
				if (path != null) {
					if (this.prefix != null) {
						if (path.startsWith(prefix));
						path = path.substring(this.prefix.length());
					}
					
					Bundle bundle = holder.getBundle();
					if (bundle != null) {
						DefaultBundleAccessor.getInstance().setCurrentBundle(bundle);
						
						Servlet servlet = jspLoader.load(path);
				        HttpJspPage page = (HttpJspPage) servlet;
				        page._jspService(request, response);
				        
						return;
					}
				}
			}
		}
		
		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		RequestDispatcher rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		if (rd == null) {
			throw new ServletException("Could not get RequestDispatcher for [" + getUrl() +
					"]: Check that the corresponding file exists within your web application archive!");
		}

		// If already included or response already committed, perform include, else forward.
		if (useInclude(requestToExpose, response)) {
			response.setContentType(getContentType());
			if (logger.isDebugEnabled()) {
				logger.debug("Including resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.include(requestToExpose, response);
		}

		else {
			// Note: The forwarded resource is supposed to determine the content type itself.
			exposeForwardRequestAttributes(requestToExpose);
			if (logger.isDebugEnabled()) {
				logger.debug("Forwarding to resource [" + getUrl() + "] in InternalResourceView '" + getBeanName() + "'");
			}
			rd.forward(requestToExpose, response);
		}
	}
}
