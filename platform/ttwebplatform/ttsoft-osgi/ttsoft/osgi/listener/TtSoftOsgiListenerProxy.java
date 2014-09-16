package ttsoft.osgi.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.struts2.osgi.host.OsgiHost;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class TtSoftOsgiListenerProxy implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, 
                                                     HttpSessionAttributeListener, HttpSessionActivationListener, ServletRequestListener, 
                                                     ServletRequestAttributeListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletContextListener.class);
		if (objs == null)
			return;
		ServletContextListener[] listeners = new ServletContextListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletContextListener) {
				listeners[i] = (ServletContextListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].contextDestroyed(event);
		}
	}
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletContextListener.class);
		if (objs == null)
			return;
		ServletContextListener[] listeners = new ServletContextListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletContextListener) {
				listeners[i] = (ServletContextListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].contextInitialized(event);
		}
	}
	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletContextAttributeListener.class);
		if (objs == null)
			return;
		ServletContextAttributeListener[] listeners = new ServletContextAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletContextAttributeListener) {
				listeners[i] = (ServletContextAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeAdded(event);
		}
	}
	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletContextAttributeListener.class);
		if (objs == null)
			return;
		ServletContextAttributeListener[] listeners = new ServletContextAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletContextAttributeListener) {
				listeners[i] = (ServletContextAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeRemoved(event);
		}
	}
	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletContextAttributeListener.class);
		if (objs == null)
			return;
		ServletContextAttributeListener[] listeners = new ServletContextAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletContextAttributeListener) {
				listeners[i] = (ServletContextAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeReplaced(event);
		}
	}
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionListener.class);
		if (objs == null)
			return;
		HttpSessionListener[] listeners = new HttpSessionListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionListener) {
				listeners[i] = (HttpSessionListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].sessionCreated(event);
		}
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {		
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionListener.class);
		if (objs == null)
			return;
		HttpSessionListener[] listeners = new HttpSessionListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionListener) {
				listeners[i] = (HttpSessionListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].sessionDestroyed(event);
		}
	}
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionAttributeListener.class);
		if (objs == null)
			return;
		HttpSessionAttributeListener[] listeners = new HttpSessionAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionAttributeListener) {
				listeners[i] = (HttpSessionAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeAdded(event);
		}
	}
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionAttributeListener.class);
		if (objs == null)
			return;
		HttpSessionAttributeListener[] listeners = new HttpSessionAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionAttributeListener) {
				listeners[i] = (HttpSessionAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeRemoved(event);
		}
	}
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionAttributeListener.class);
		if (objs == null)
			return;
		HttpSessionAttributeListener[] listeners = new HttpSessionAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionAttributeListener) {
				listeners[i] = (HttpSessionAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeReplaced(event);
		}
	}
	@Override
	public void sessionDidActivate(HttpSessionEvent event) {	
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionActivationListener.class);
		if (objs == null)
			return;
		HttpSessionActivationListener[] listeners = new HttpSessionActivationListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionActivationListener) {
				listeners[i] = (HttpSessionActivationListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].sessionDidActivate(event);
		}
	}
	@Override
	public void sessionWillPassivate(HttpSessionEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getSession().getServletContext(), HttpSessionActivationListener.class);
		if (objs == null)
			return;
		HttpSessionActivationListener[] listeners = new HttpSessionActivationListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof HttpSessionActivationListener) {
				listeners[i] = (HttpSessionActivationListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].sessionWillPassivate(event);
		}
	}
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletRequestListener.class);
		if (objs == null)
			return;
		ServletRequestListener[] listeners = new ServletRequestListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletRequestListener) {
				listeners[i] = (ServletRequestListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].requestDestroyed(event);
		}
	}
	@Override
	public void requestInitialized(ServletRequestEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletRequestListener.class);
		if (objs == null)
			return;
		ServletRequestListener[] listeners = new ServletRequestListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletRequestListener) {
				listeners[i] = (ServletRequestListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].requestInitialized(event);
		}
	}
	@Override
	public void attributeAdded(ServletRequestAttributeEvent event) {		
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletRequestAttributeListener.class);
		if (objs == null)
			return;
		ServletRequestAttributeListener[] listeners = new ServletRequestAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletRequestAttributeListener) {
				listeners[i] = (ServletRequestAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeAdded(event);
		}
	}
	@Override
	public void attributeRemoved(ServletRequestAttributeEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletRequestAttributeListener.class);
		if (objs == null)
			return;
		ServletRequestAttributeListener[] listeners = new ServletRequestAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletRequestAttributeListener) {
				listeners[i] = (ServletRequestAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeRemoved(event);
		}
	}
	@Override
	public void attributeReplaced(ServletRequestAttributeEvent event) {
		Object[] objs = obtainAllDefinedListeners(event.getServletContext(), ServletRequestAttributeListener.class);
		if (objs == null)
			return;
		ServletRequestAttributeListener[] listeners = new ServletRequestAttributeListener[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null && objs[i] instanceof ServletRequestAttributeListener) {
				listeners[i] = (ServletRequestAttributeListener)objs[i];
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] != null)
				listeners[i].attributeReplaced(event);
		}
	}
	
	/**
	 * 获得所有插件内特定的服务集
	 * @param servletContext
	 * @return
	 */
	private Object[] obtainAllDefinedListeners(ServletContext servletContext, Class clazz) {
		if (servletContext == null)
			return null;
		if (clazz == null)
			return null;
		
		BundleContext bundleContext = (BundleContext)servletContext.getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
		if (bundleContext == null) {
			return null;
		}
		
		try {
			//获得所有定义osgi.web.filter属性的插件service
			ServiceReference[] sres = bundleContext.getAllServiceReferences(clazz.getName(), "(osgi.web.listener=true)");
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
					String p = (String)r1.getProperty("osgi.web.listener.priority");
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
			
			List<Object> l = new ArrayList<Object>();
			ServiceReference sre = null;
			Object obj = null;
			for (int i = 0; i < sres.length; i++) {
				sre = sres[i];
				if (sre != null) {
					obj = bundleContext.getService(sre);
					if (obj != null && clazz.isInstance(obj))
						l.add(obj);
				}
			}
			
			if (l.size() == 0)
				return null;
			
			return l.toArray(new Object[0]);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
