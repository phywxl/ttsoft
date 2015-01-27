/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.struts2.osgi;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.osgi.host.OsgiHost;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import ttsoft.osgi.config.TtOsgiConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class that find resources and loads classes from the list of bundles
 */
public class DefaultBundleAccessor implements BundleAccessor {

    private static DefaultBundleAccessor self;
    private static final Logger LOG = LoggerFactory.getLogger(DefaultBundleAccessor.class);

    private BundleContext bundleContext;
    private Map<String, String> packageToBundle = new HashMap<String, String>();
    private Map<Bundle, Set<String>> packagesByBundle = new HashMap<Bundle, Set<String>>();
    private OsgiHost osgiHost;

    public DefaultBundleAccessor() {
        self = this;
    }

    public static DefaultBundleAccessor getInstance() {
        return self;
    }

    public Object getService(ServiceReference ref) {
        return bundleContext != null ? bundleContext.getService(ref) : null;
    }

    public ServiceReference getServiceReference(String className) {
        return bundleContext != null ? bundleContext.getServiceReference(className) : null;
    }

    public ServiceReference[] getAllServiceReferences(String className) {
        if (bundleContext != null) {
            try {
                return bundleContext.getServiceReferences(className, null);
            } catch (InvalidSyntaxException e) {
                //cannot happen we are passing null as the param
                if (LOG.isErrorEnabled())
                    LOG.error("Invalid syntax for service lookup", e);
            }
        }

        return null;
    }

    public ServiceReference[] getServiceReferences(String className, String params) throws InvalidSyntaxException {
        return bundleContext != null ? bundleContext.getServiceReferences(className, params) : null;
    }

    /**
     *  Add as Bundle -> Package mapping 
     * @param bundle the bundle where the package was loaded from
     * @param packageName the anme of the loaded package
     */
    public void addPackageFromBundle(Bundle bundle, String packageName) {
        this.packageToBundle.put(packageName, bundle.getSymbolicName());
        Set<String> pkgs = packagesByBundle.get(bundle);
        if (pkgs == null) {
            pkgs = new HashSet<String>();
            packagesByBundle.put(bundle, pkgs);
        }
        pkgs.add(packageName);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
    	Class cls = null;
        Bundle bundle = getCurrentBundle();
        if (bundle != null) {
        	try {
	            cls = bundle.loadClass(className);
	            if (cls != null) { 
	            	if (LOG.isTraceEnabled())
	            		LOG.trace("Located class [#0] in bundle [#1]", className, bundle.getSymbolicName());
	            	return cls;
	            }
        	} catch (ClassNotFoundException e) {}
        	
        	/*ServiceReference[] refs = this.getAllServiceReferences(SpringOsgiObjectFactory.SPRING_SERVICE_NAME);
       	 	if (refs != null) {
                for (ServiceReference ref : refs) {
                	if (ref.getBundle().getSymbolicName().equals(bundle.getSymbolicName())) {
                		Object context = this.getService(ref);
                        if (OsgiUtil.containsBean(context, className) && OsgiUtil.getBean(context, className) != null)
                            return OsgiUtil.getBean(context, className).getClass();
                	}
                }
            }*/
        }
        
        /* phywxl 20130704 add. BEGIN */
        /* phywxl 20140219 comment. BEGIN */
        //从所有的bundle获取class
        /*Map<String, Bundle> maps = this.osgiHost.getActiveBundles();
        for (Bundle b : maps.values()) {
        	try {
	        	cls = b.loadClass(className);
	        	if (cls != null)
	        		return cls;
        	} catch (ClassNotFoundException e) {}
        }*/
        /* phywxl 20140219 comment. END */
        
        //从application获取class
        try {
	        cls = this.getClassLoaderInterface().loadClass(className);
	        if (cls != null)
	        	return cls;
        } catch (ClassNotFoundException e) {}
        /* phywxl 20130704 add. END */

        throw new ClassNotFoundException("Unable to find class " + className);
    }
    
    /* phywxl 20130704 add. BEGIN */
    public Map<String, Bundle> getActiveBundles() {
    	return this.osgiHost.getActiveBundles();
    }
    /* phywxl 20130704 add. END */

    /* wangxianliang 201308201152 把方法改为公共方法 */
    public Bundle getCurrentBundle() {
        ActionContext ctx = ActionContext.getContext();
        /* wangxianliang 201402191451 注释了一下代码，增加了一些代码. BEGIN */
        /*String bundleName = (String) ctx.get(CURRENT_BUNDLE_NAME);
        if (bundleName == null) {
            ActionInvocation inv = ctx.getActionInvocation();
            ActionProxy proxy = inv.getProxy();
            ActionConfig actionConfig = proxy.getConfig();
            bundleName = packageToBundle.get(actionConfig.getPackageName());
        }*/
        String bundleName = null;
        if (bundleName == null) {
            ActionInvocation inv = ctx.getActionInvocation();
            if (inv != null) {
	            ActionProxy proxy = inv.getProxy();
	            ActionConfig actionConfig = proxy.getConfig();
	            bundleName = packageToBundle.get(actionConfig.getPackageName());
            }
        }
        if (bundleName == null) {
        	String namespace = ServletActionContext.getActionMapping() == null ? null : ServletActionContext.getActionMapping().getNamespace();
        	if (namespace != null) {
        		namespace = namespace.trim();
        		while (namespace.startsWith("/"))
        			namespace.substring(1);
        		int index = namespace.indexOf("/");
        		if (index > 0) {
        			namespace = namespace.substring(0, index);
        		}
        		if (namespace.length() > 0) {
        			for (Bundle b : osgiHost.getActiveBundles().values()) {
        				if (b != null && b.getSymbolicName() != null && b.getSymbolicName().equals(namespace)) {
        					setCurrentBundle(b);
        					return b;
        				}
        			}
        		}
        	}
        }
        if (bundleName == null) {
        	bundleName = (String) ctx.get(CURRENT_BUNDLE_NAME);
        }
        /* wangxianliang 201402191451 注释了一下代码，增加了一些代码. END */
        if (bundleName != null) {
            return osgiHost.getActiveBundles().get(bundleName);
        }
        return null;
    }
    
    /* phywxl 20130704 add. BEGIN */
    public void setCurrentBundle(Bundle bundle) {
        ActionContext ctx = ActionContext.getContext();
        ctx.put(CURRENT_BUNDLE_NAME, bundle.getSymbolicName());
    }
    /* phywxl 20130704 add. END */

    public List<URL> loadResources(String name) throws IOException {
        return loadResources(name, false);
    }

    public List<URL> loadResources(String name, boolean translate) throws IOException {
    	/* phywxl 20130704 add. BEGIN */
    	PluginResourcesHold hold = this.getResourcesFromBundle(name);
    	if (hold != null && hold.getPlugin() != null && hold.getResources() != null) {
    		List<URL> resources = new ArrayList<URL>();
    		for (URL url : hold.getResources()) {
    			resources.add(translate ? OsgiUtil.translateBundleURLToJarURL(url, hold.getPlugin()) : url);
    		}
    		if (resources.size() > 0)
    			return resources;
    	}
    	/* phywxl 20130704 add. END */
    	
        Bundle bundle = getCurrentBundle();
        if (bundle != null) {
            List<URL> resources = new ArrayList<URL>();
            Enumeration e = bundle.getResources(name);
            if (e != null) {
                while (e.hasMoreElements()) {
                    resources.add(translate ? OsgiUtil.translateBundleURLToJarURL((URL) e.nextElement(), getCurrentBundle()) : (URL) e.nextElement());
                }
            }
            return resources;
        }
        
        /* phywxl 20130704 add. BEGIN */
        List<URL> urls = new ArrayList<URL>();
        //从application获取资源
        Enumeration<URL> us = this.getClassLoaderInterface().getResources(name);
        if (us != null) {
        	while (us.hasMoreElements()) {
        		urls.add(us.nextElement());
        	}
        }
        //从webapp目录获取
        String path = ServletActionContext.getServletContext().getRealPath(name);
        //System.out.println("DefaultBundleAccessor.loadResource(...) realPath=" + path);
        if (path != null) {
        	try {
        		urls.add((new java.io.File(path)).toURL());
				return urls;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
        /* phywxl 20130704 add. END */

        return null;
    }

    public URL loadResourceFromAllBundles(String name) throws IOException {
    	/* phywxl 20130704 add. BEGIN */
    	PluginResourcesHold hold = this.getResourcesFromBundle(name);
    	if (hold != null && hold.getPlugin() != null && hold.getResources() != null) {
    		List<URL> resources = new ArrayList<URL>();
    		for (URL url : hold.getResources()) {
    			if (url != null)
    				return url;
    		}
    	}
    	/* phywxl 20130704 add. END */
    	
        for (Map.Entry<String, Bundle> entry : osgiHost.getActiveBundles().entrySet()) {
            Enumeration e = entry.getValue().getResources(name);
            if (e != null && e.hasMoreElements()) {
                return (URL) e.nextElement();
            }
        }

        return null;
    }

    public InputStream loadResourceFromAllBundlesAsStream(String name) throws IOException {
        URL url = loadResourceFromAllBundles(name);
        if (url != null) {
            return url.openStream();
        }
        return null;
    }

    public URL loadResource(String name) {
        return loadResource(name, false);
    }

    public URL loadResource(String name, boolean translate) {
    	URL url = null;
    	
    	/* phywxl 20130704 add. BEGIN */
    	try {
	    	PluginResourcesHold hold = this.getResourcesFromBundle(name);
	    	if (hold != null && hold.getPlugin() != null && hold.getResources() != null) {
	    		List<URL> resources = new ArrayList<URL>();
	    		for (URL u : hold.getResources()) {
	    			if (u != null)
	    				return translate ? OsgiUtil.translateBundleURLToJarURL(u, hold.getPlugin()) : u;
	    		}
	    	}
    	} catch (IOException e) {
    		if (LOG.isErrorEnabled()) {
                LOG.error("Unable to get URL from bundle", e);
            }
    	}
    	/* phywxl 20130704 add. END */
    	
        Bundle bundle = getCurrentBundle();
        if (bundle != null) {
            url = bundle.getResource(name);
            try {
                return translate ? OsgiUtil.translateBundleURLToJarURL(url, getCurrentBundle()) : url;
            } catch (Exception e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Unable to translate bundle URL to jar URL", e);
                }

                return null;
            }
        }
        
        /* phywxl 20130704 add. BEGIN */
        //从application获取资源
        url = this.getClassLoaderInterface().getResource(name);
        if (url != null)
        	return url;
        //从webapp目录获取
        String path = ServletActionContext.getServletContext().getRealPath(name);
        //System.out.println("DefaultBundleAccessor.loadResource(...) realPath=" + path);
        if (path != null) {
        	try {
				return (new java.io.File(path)).toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
        /* phywxl 20130704 add. END */

        return null;
    }

    public Set<String> getPackagesByBundle(Bundle bundle) {
        return packagesByBundle.get(bundle);
    }

    public InputStream loadResourceAsStream(String name) throws IOException {
        URL url = loadResource(name);
        if (url != null) {
            return url.openStream();
        }
        return null;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public void setOsgiHost(OsgiHost osgiHost) {
        this.osgiHost = osgiHost;
    }

    /* phywxl 20130704 add 获得当前web app classloader。 BEGIN */
    public ClassLoaderInterface getClassLoaderInterface() {
    	return new ClassLoaderInterfaceDelegate(this.getClass().getClassLoader());
    }
    public PluginResourcesHold getResourcesFromBundle(String name) throws IOException {
    	//空路径
    	if (name == null || name.trim().equals(""))
    		return null;
    	
    	//获得资源在插件中的前置路径
    	Enumeration<String> enums = this.getBundleResourcePrefix();
    	
    	//去除空白，前导斜线
    	String p = name;
    	if (p != null) p = p.trim();
    	while (p.startsWith("/")) p = p.substring(1);
    	while (p.startsWith("\\")) p = p.substring(1);
    	while (p.startsWith("/")) p = p.substring(1);
    	int i = p.indexOf("/");
    	if (i > 0) {
    		//取得后续路径
    		String resname = p.substring(i + 1);
    		//取得插件名
    		p = p.substring(0, i);
    		if (p != null && p.length() > 0) {
    			for (Map.Entry<String, Bundle> entry : osgiHost.getActiveBundles().entrySet()) {
    				//如果确实是特定插件
    				if (entry.getValue().getSymbolicName().equals(p)) {
    					//资源集合
    					List<URL> urls = new ArrayList<URL>();
    					
    					//获得路径集合
    					List<String> resources = new ArrayList<String>();
    					while (enums.hasMoreElements()) {
    						resources.add(enums.nextElement() + "/" + resname);
    					}
    					resources.add(resname);
    					
    					for (String path : resources) {
    						Enumeration<URL> e = entry.getValue().getResources(path);
    	    	            if (e != null ) {
    	    	            	while (e.hasMoreElements()) {
    	    	            		urls.add(e.nextElement());
    	    	            	}
    	    	            }
    					}
    					
    					if (urls.size() > 0) {
    						PluginResourcesHold hold = new PluginResourcesHold(entry.getValue(), p, urls);
    						return hold;
    					}
    				}
    			}
    		}
    	}
    	
    	try {
	    	Bundle bundle = this.getCurrentBundle();
	    	if (bundle != null) {
	    		List<URL> urls = new ArrayList<URL>();
	    		
	    		List<String> resources = new ArrayList<String>();
	    		enums = this.getBundleResourcePrefix();
	    		while (enums.hasMoreElements()) {
					resources.add(enums.nextElement() + "/" + name);
				}
				resources.add(name);
				
				for (String path : resources) {
					Enumeration<URL> e = bundle.getResources(path);
		            if (e != null ) {
		            	while (e.hasMoreElements()) {
		            		urls.add(e.nextElement());
		            	}
		            }
				}
				
				if (urls.size() > 0) {
					PluginResourcesHold hold = new PluginResourcesHold(bundle, bundle.getSymbolicName(), urls);
					return hold;
				}
	    	}
    	}catch (Exception e) {}
    	
    	return null;
    }
    public Enumeration<String> getBundleResourcePrefix() {
    	Enumeration<String> enums = TtOsgiConfiguration.getConfig().getStrings(TtOsgiConfiguration.PLUGIN_RESOURCE_SEARCH_PREFIX);
		if (enums == null) {
			enums = new Enumeration<String>() {
				private String[] str = {"WebRoot"};
				private int index = 0;
				@Override
				public boolean hasMoreElements() {
					return index < str.length;
				}

				@Override
				public String nextElement() {
					String s = str[index];
					index++;
					return s;
				}
			};
		}
		return enums;
    }
    private class PluginResourcesHold {
    	private Bundle plugin;
    	private String pluginName;
    	private List<URL> resources;
    	public PluginResourcesHold(Bundle plugin, String pluginName, List<URL> resources) {
    		this.plugin = plugin;
    		this.pluginName = pluginName;
    		this.resources = resources;
    	}
    	public Bundle getPlugin() {
			return plugin;
		}
		public String getPluginName() {
			return pluginName;
		}
		public List<URL> getResources() {
			return resources;
		}
    }
    /* phywxl 20130704 add. END */
}
