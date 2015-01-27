package ttsoft.osgi.servlet;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.osgi.host.OsgiHost;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

/**
 * spring mvc处理类
 * @author wangxianliang
 *
 */
public class TtSoftOsgiSpringMVCServlet extends GenericServlet {
	private DispatcherServlet dispather;
	
	@Override
	public void service(ServletRequest srequest, ServletResponse sresponse)
			throws ServletException, IOException {
		if (sresponse.isCommitted())
			return;
		
		if (dispather == null) {
			dispather = new MyDispatcherServlet(this);
			dispather.init();
		}
		
		HttpServletRequest request = (HttpServletRequest)srequest;
		
		//判断是否请求插件
		TtSoftOsgiSpringMVCBundleHolder holder = this.getBundleHolder(request);
		if (holder != null) {
			TtSoftOsgiSpringMVCContext.setContext(new TtSoftOsgiSpringMVCContext(holder));
			try {
				MyDispatcherServlet2 d2 = new MyDispatcherServlet2(this, holder);
				d2.init();
				
				TtSoftOsgiSpringMVCRequestWrapper w = new TtSoftOsgiSpringMVCRequestWrapper(request, holder.getSymbolicName(), holder.getVer());
				holder.setWebApplicationContext(d2.getWebApplicationContext());
				d2.service(w, sresponse);
			} catch (ServletException e) {
				
			} catch (IOException e) {
				
			} finally {
				TtSoftOsgiSpringMVCContext.setContext(null);
			}
		} 
		//不请求插件，请求web容器内的spring
		else {
			dispather.service(srequest, sresponse);
		}
	}
	
	
	
	@Override
	public void destroy() {
		if (dispather != null) {
			dispather.destroy();
		}
		
		super.destroy();
	}

	
	private TtSoftOsgiSpringMVCBundleHolder getBundleHolder(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		String path = helper.getLookupPathForRequest(request);
		if (path == null || (path = path.trim()).equals("")) {
			return null;
		}
		
		while (path.startsWith("/")) {
			path = path.substring(1);
		}
		//获得所有插件
		Bundle[] bundles = this.getBundles(request);
		if (bundles == null) {
			return null;
		}
		//获得路径中的插件名
		String synblicName = null;
		String v = null;
		int index = path.indexOf("/");
		if (index > 0) {
			synblicName = path.substring(0, index);
		} else {
			synblicName = path;
		}
		if (synblicName == null || (synblicName = synblicName.trim()).equals("")) {
			return null;
		}
		//获得路径中的插件版本
		int index2 = path.indexOf("/", index + 1);
		if (index > 0 && index2 > 0) {
			v = path.substring(index + 1, index2);
		}
		Version version = null;
		if (v != null) {
			try {
				version = Version.parseVersion(v);
			} catch (Throwable e) {
				version = null;
			}
		}
		//获得特定插件
		Bundle bundle = null;
		for (Bundle b : bundles) {
			if (b.getBundleId() == 0) {
				continue;
			}
			
			if (b.getSymbolicName().equals(synblicName)) {
				if (version != null) {
					if (version.equals(b.getVersion())) {
						bundle = b;
						break;
					}
				} else {
					if (bundle == null) {
						bundle = b;
					} else {
						if (b.getVersion().compareTo(bundle.getVersion()) > 0) {
							bundle = b;
						}
					}
				}
			}
		}
		
		//特定插件不为空
		if (bundle != null) {
			ConfigurableApplicationContext applicationContext = this.getService(bundle);
			if (applicationContext == null) {
				return null;
			}
			
			return new TtSoftOsgiSpringMVCBundleHolder(synblicName, version == null ? null : v, bundle, request, applicationContext);
		}
		
		return null;
	}
	
	/**
	 * 获得所有插件
	 * @param srequest
	 * @return
	 */
	private Bundle[] getBundles(ServletRequest srequest) {
		BundleContext bundleContext = (BundleContext)this.getServletConfig().getServletContext().getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
		if (bundleContext != null) {
			return bundleContext.getBundles();
		}
		
		return null;
	}
	/**
	 * 获得特定插件的spring容器
	 * @param bundle
	 * @return
	 */
	private ConfigurableApplicationContext getService(Bundle bundle) {
		BundleContext bundleContext = (BundleContext)this.getServletConfig().getServletContext().getAttribute(OsgiHost.OSGI_BUNDLE_CONTEXT);
        if (bundleContext != null) {
        	try {
	        	ServiceReference[] refs = bundleContext.getServiceReferences("org.springframework.context.ApplicationContext", null);
	        	if (refs == null) {
	        		return null;
	        	}
	        	for (ServiceReference ref : refs) {
	        		if (ref.getBundle().equals(bundle)) {
	        			return (ConfigurableApplicationContext)bundleContext.getService(ref);
	        		}
	        	}
            } catch (InvalidSyntaxException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
	
	/**
	 * bundle持有者
	 * @author a
	 *
	 */
	/*class BundleHolder {
		private String symbolicName;
		private String ver;
		Bundle bundle;
		HttpServletRequest request;
		ConfigurableApplicationContext applicationContext;
		
		BundleHolder(String symbolicName, String ver, Bundle bundle, HttpServletRequest request, ConfigurableApplicationContext applicationContext) {
			this.symbolicName = symbolicName; this.ver = ver; this.bundle = bundle; this.request = request; this.applicationContext = applicationContext;
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
	}*/

	/**
	 * Spring DispatcherServlet 子类，重新定义servlet环境
	 * 修改了超类 HttpServletBean：把final方法去掉了
	 * @author wangxianliang
	 *
	 */
	class MyDispatcherServlet extends DispatcherServlet {
		public Servlet owner;
		
		public MyDispatcherServlet(Servlet owner) {
			this.owner = owner;
		}

		@Override
		public String getServletName() {
			return (owner.getServletConfig() != null ? owner.getServletConfig().getServletName() : null);
		}

		@Override
		public ServletContext getServletContext() {
			return (owner.getServletConfig() != null ? owner.getServletConfig().getServletContext() : null);
		}

		@Override
		public ServletConfig getServletConfig() {
			return owner.getServletConfig();
		}
	}
	
	class MyDispatcherServlet2 extends MyDispatcherServlet {
		public TtSoftOsgiSpringMVCServlet owner;
		private TtSoftOsgiSpringMVCBundleHolder holder;
		
		public MyDispatcherServlet2(TtSoftOsgiSpringMVCServlet owner, TtSoftOsgiSpringMVCBundleHolder holder) {
			super(owner);
			this.owner = owner;
			this.holder = holder;
		}
		
		/**
		 * Initialize and publish the WebApplicationContext for this servlet.
		 * <p>Delegates to {@link #createWebApplicationContext} for actual creation
		 * of the context. Can be overridden in subclasses.
		 * @return the WebApplicationContext instance
		 * @see #FrameworkServlet(WebApplicationContext)
		 * @see #setContextClass
		 * @see #setContextConfigLocation
		 */
		protected WebApplicationContext initWebApplicationContext() {
			ConfigurableWebApplicationContext context = new ConfigurableWebApplicationContext() {
				@Override
				public ServletContext getServletContext() {
					return owner.getServletConfig().getServletContext();
				}
				@Override
				public String getId() {
					return holder.getApplicationContext().getId();
				}
				@Override
				public String getApplicationName() {
					return holder.getApplicationContext().getApplicationName();
				}
				@Override
				public String getDisplayName() {
					return holder.getApplicationContext().getDisplayName();
				}

				@Override
				public long getStartupDate() {
					return holder.getApplicationContext().getStartupDate();
				}
				@Override
				public ApplicationContext getParent() {
					return holder.getApplicationContext().getParent();
				}
				@Override
				public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
					return holder.getApplicationContext().getAutowireCapableBeanFactory();
				}
				public ConfigurableEnvironment getEnvironment() {
					return holder.getApplicationContext().getEnvironment();
				}
				@Override
				public boolean containsBeanDefinition(String beanName) {
					return holder.getApplicationContext().containsBeanDefinition(beanName);
				}
				@Override
				public int getBeanDefinitionCount() {
					return holder.getApplicationContext().getBeanDefinitionCount();
				}
				@Override
				public String[] getBeanDefinitionNames() {
					return holder.getApplicationContext().getBeanDefinitionNames();
				}
				@Override
				public String[] getBeanNamesForType(Class<?> type) {
					return holder.getApplicationContext().getBeanNamesForType(type);
				}
				@Override
				public String[] getBeanNamesForType(Class<?> type,
						boolean includeNonSingletons, boolean allowEagerInit) {
					return holder.getApplicationContext().getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
				}
				@Override
				public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
					return holder.getApplicationContext().getBeansOfType(type);
				}
				@Override
				public <T> Map<String, T> getBeansOfType(Class<T> type,
						boolean includeNonSingletons, boolean allowEagerInit)
						throws BeansException {
					return holder.getApplicationContext().getBeansOfType(type, includeNonSingletons, allowEagerInit);
				}
				@Override
				public Map<String, Object> getBeansWithAnnotation(
						Class<? extends Annotation> annotationType)
						throws BeansException {
					return holder.getApplicationContext().getBeansWithAnnotation(annotationType);
				}
				@Override
				public <A extends Annotation> A findAnnotationOnBean(
						String beanName, Class<A> annotationType) {
					return holder.getApplicationContext().findAnnotationOnBean(beanName, annotationType);
				}
				@Override
				public Object getBean(String name) throws BeansException {
					return holder.getApplicationContext().getBean(name);
				}
				@Override
				public <T> T getBean(String name, Class<T> requiredType)
						throws BeansException {
					return holder.getApplicationContext().getBean(name, requiredType);
				}
				@Override
				public <T> T getBean(Class<T> requiredType)
						throws BeansException {
					return holder.getApplicationContext().getBean(requiredType);
				}
				@Override
				public Object getBean(String name, Object... args)
						throws BeansException {
					return holder.getApplicationContext().getBean(name, args);
				}

				@Override
				public boolean containsBean(String name) {
					return holder.getApplicationContext().containsBean(name);
				}

				@Override
				public boolean isSingleton(String name)
						throws NoSuchBeanDefinitionException {
					return holder.getApplicationContext().isSingleton(name);
				}

				@Override
				public boolean isPrototype(String name)
						throws NoSuchBeanDefinitionException {
					return holder.getApplicationContext().isPrototype(name);
				}

				@Override
				public boolean isTypeMatch(String name, Class<?> targetType)
						throws NoSuchBeanDefinitionException {
					return holder.getApplicationContext().isTypeMatch(name, targetType);
				}

				@Override
				public Class<?> getType(String name)
						throws NoSuchBeanDefinitionException {
					return holder.getApplicationContext().getType(name);
				}

				@Override
				public String[] getAliases(String name) {
					return holder.getApplicationContext().getAliases(name);
				}

				@Override
				public BeanFactory getParentBeanFactory() {
					return holder.getApplicationContext().getParentBeanFactory();
				}

				@Override
				public boolean containsLocalBean(String name) {
					return holder.getApplicationContext().containsLocalBean(name);
				}

				@Override
				public String getMessage(String code, Object[] args,
						String defaultMessage, Locale locale) {
					return holder.getApplicationContext().getMessage(code, args, defaultMessage, locale);
				}

				@Override
				public String getMessage(String code, Object[] args,
						Locale locale) throws NoSuchMessageException {
					return holder.getApplicationContext().getMessage(code, args, locale);
				}
				@Override
				public String getMessage(MessageSourceResolvable resolvable,
						Locale locale) throws NoSuchMessageException {
					return holder.getApplicationContext().getMessage(resolvable, locale);
				}
				@Override
				public void publishEvent(ApplicationEvent event) {
					holder.getApplicationContext().publishEvent(event);
				}
				@Override
				public Resource[] getResources(String locationPattern)
						throws IOException {
					return holder.getApplicationContext().getResources(locationPattern);
				}
				@Override
				public Resource getResource(String location) {
					return holder.getApplicationContext().getResource(location);
				}
				@Override
				public ClassLoader getClassLoader() {
					return holder.getApplicationContext().getClassLoader();
				}
				@Override
				public void setId(String id) {
					holder.getApplicationContext().setId(id);
				}
				@Override
				public void setParent(ApplicationContext parent) {
					holder.getApplicationContext().setParent(parent);
				}
				@Override
				public void setEnvironment(ConfigurableEnvironment environment) {
					holder.getApplicationContext().setEnvironment(environment);
				}
				@Override
				public void addBeanFactoryPostProcessor(
						BeanFactoryPostProcessor beanFactoryPostProcessor) {
					holder.getApplicationContext().addBeanFactoryPostProcessor(beanFactoryPostProcessor);
				}
				@Override
				public void addApplicationListener(
						ApplicationListener<?> listener) {
					holder.getApplicationContext().addApplicationListener(listener);
				}
				@Override
				public void refresh() throws BeansException,
						IllegalStateException {
					holder.getApplicationContext().refresh();
				}
				@Override
				public void registerShutdownHook() {
					holder.getApplicationContext().registerShutdownHook();
				}
				@Override
				public void close() {
					holder.getApplicationContext().close();
				}
				@Override
				public boolean isActive() {
					return holder.getApplicationContext().isActive();
				}
				@Override
				public ConfigurableListableBeanFactory getBeanFactory()
						throws IllegalStateException {
					return holder.getApplicationContext().getBeanFactory();
				}
				@Override
				public void start() {
					holder.getApplicationContext().start();
				}
				@Override
				public void stop() {
					holder.getApplicationContext().stop();
				}
				@Override
				public boolean isRunning() {
					return holder.getApplicationContext().isRunning();
				}
				@Override
				public void setServletContext(ServletContext servletContext) {
					
				}
				@Override
				public void setServletConfig(ServletConfig servletConfig) {
					
				}
				@Override
				public ServletConfig getServletConfig() {
					return owner.getServletConfig();
				}
				@Override
				public void setNamespace(String namespace) {
					
				}
				@Override
				public String getNamespace() {
					return null;
				}
				@Override
				public void setConfigLocation(String configLocation) {
					
				}
				@Override
				public void setConfigLocations(String[] configLocations) {
					
				}
				@Override
				public String[] getConfigLocations() {
					return null;
				}
			};
			
			super.onRefresh(context);
			
			return context;
		}
	}
}
