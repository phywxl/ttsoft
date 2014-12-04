package org.apache.struts2.osgi;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.struts2.StrutsException;
import org.apache.struts2.osgi.host.OsgiHost;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener that starts Osgi host
 */
public class StrutsOsgiListener implements ServletContextListener {

    public static final String OSGI_HOST = "__struts_osgi_host";
    public static final String PLATFORM_KEY = "struts.osgi.host";

    private static final Logger LOG = LoggerFactory.getLogger(StrutsOsgiListener.class);

    private OsgiHost osgiHost;

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        String platform = servletContext.getInitParameter(PLATFORM_KEY);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Defined OSGi platform as [#0] via context-param [#1]", platform, PLATFORM_KEY);
        }
        osgiHost = OsgiHostFactory.createOsgiHost(platform);
        servletContext.setAttribute(OSGI_HOST, osgiHost);
        try {
            osgiHost.init(servletContext);
        } catch (Exception e) {
            throw new StrutsException("Cannot init OSGi platform!", e);
        }
        /* phywxl 20130704, �����ص��ڴ��jsp class. Begin */
        osgiHost.getBundleContext().addBundleListener(new org.osgi.framework.BundleListener(){
			public void bundleChanged(org.osgi.framework.BundleEvent event) {
				if (org.osgi.framework.BundleEvent.STOPPED == event.getType() || org.osgi.framework.BundleEvent.UNINSTALLED == event.getType()) {
					//System.out.println("====StrutsOsgiListener.contextInitialized(...) clear begin.");
					/* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. Begin */
					/*org.apache.struts2.JSPRuntime.clearCache();
					org.apache.struts2.JSPLoader.classLoader.clearBundleMemoryJavaFileObject();
					org.apache.struts2.JSPLoader.classLoader = new org.apache.struts2.compiler.MemoryClassLoader();*/
					org.apache.struts2.JSPRuntime.clearCache(event.getBundle());
					/*org.apache.struts2.JSPLoader.classLoader.clearBundleMemoryJavaFileObject(event.getBundle().getSymbolicName());
					org.apache.struts2.compiler.MemoryClassLoader m = new org.apache.struts2.compiler.MemoryClassLoader();
					m.copyMemoryJavaFileObject(org.apache.struts2.JSPLoader.classLoader);
					org.apache.struts2.JSPLoader.classLoader = m;*/
					/* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. End */
					//System.out.println("====StrutsOsgiListener.contextInitialized(...) clear end.");
				}
			}
        });
        /* phywxl 20130704, �����ص��ڴ��jsp class. END */
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            osgiHost.destroy();
        } catch (Exception e) {
            throw new StrutsException("Cannot stop OSGi platform!", e);
        }
    }

}