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

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.inject.Inject;

import org.apache.struts2.ServletActionContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import java.util.Map;

import javax.servlet.ServletContext;

/**
 * This Object factory uses the ActionContext(s) published by Spring OSGi
 * to lookup beans
 */
public class SpringOsgiObjectFactory extends ObjectFactory {
	//private final static String SPRING_SERVICE_NAME = "org.springframework.context.ApplicationContext";
	/* wangxianliang 201308201132 BEGIN */
    public final static String SPRING_SERVICE_NAME = "org.springframework.context.ApplicationContext";
    /* wangxianliang 201308201132 END */
    
    private BundleAccessor bundleAccessor;

    public Object buildBean(String className, Map<String, Object> extraContext, boolean injectInternal) throws Exception {
        if (containsBean(className))
            return getBean(className);
        /* 20140310 wangxianliang 解决找不到web容器的spring ApplicationContext问题. BEGIN  */
        else if (containsBean2(className))
        	return getBean2(className);
        /* 20140310 wangxianliang 解决找不到web容器的spring ApplicationContext问题. END  */
        else {
            Class clazz = ClassLoaderUtil.loadClass(className, SpringOsgiObjectFactory.class);
            Object object = clazz.newInstance();
            if (injectInternal)
                injectInternalBeans(object);
            return object;
        }

    }

    public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
        return clazz.newInstance();
    }

    public Class getClassInstance(String className) throws ClassNotFoundException {
        return containsBean(className) ? getBean(className).getClass() :  ClassLoaderUtil.loadClass(className, SpringOsgiObjectFactory.class);
    }

    protected Object getBean(String beanName) {
    	/* 20130807 wangxianliang 解决struts2 action bean从spring注入错误的问题. BEGIN  */
    	Bundle bundle = DefaultBundleAccessor.getInstance().getCurrentBundle();
    	
    	if (bundle == null) {
    		try {
		    	String symbolicName = ServletActionContext.getActionMapping().getNamespace();
		    	if (symbolicName != null) {
		    		symbolicName = symbolicName.trim();
		    		
		    		while (symbolicName.startsWith("/")) {
		    			symbolicName = symbolicName.substring(1);
		    		}
		    		int index = symbolicName.indexOf("/");
		    		if (index > 0) {
		    			symbolicName = symbolicName.substring(0, index);
		    		}
		    	}
		    	Map<String, Bundle> maps = DefaultBundleAccessor.getInstance().getActiveBundles();
		    	for (Bundle b : maps.values()) {
		    		if (b != null && b.getSymbolicName().equals(symbolicName)) {
		    			bundle =  b;
		    			break;
		    		}
		    	}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	/* 20130807 wangxianliang 解决struts2 action bean从spring注入错误的问题. END  */
    	
        ServiceReference[] refs = bundleAccessor.getAllServiceReferences(SPRING_SERVICE_NAME);
        if (refs != null) {
        	/* 20130807 wangxianliang 解决struts2 action bean从spring注入错误的问题. BEGIN  */
        	if (bundle != null)
	            for (ServiceReference ref : refs) {
	            	if (ref.getBundle().getSymbolicName().equals(bundle.getSymbolicName())) {
	            		Object context = bundleAccessor.getService(ref);
	                    if (OsgiUtil.containsBean(context, beanName))
	                        return OsgiUtil.getBean(context, beanName);
	            	}
	            }
            /* 20130807 wangxianliang 解决struts2 action bean从spring注入错误的问题. END  */
            
            for (ServiceReference ref : refs) {
                Object context = bundleAccessor.getService(ref);
                if (OsgiUtil.containsBean(context, beanName))
                    return OsgiUtil.getBean(context, beanName);
            }
        }

        return null;
    }

    protected boolean containsBean(String beanName) {
        ServiceReference[] refs = bundleAccessor.getAllServiceReferences(SPRING_SERVICE_NAME);
        if (refs != null) {
            for (ServiceReference ref : refs) {
                Object context = bundleAccessor.getService(ref);
                if (OsgiUtil.containsBean(context, beanName))
                    return true;
            }
        }

        return false;
    }

    @Inject
    public void setBundleAccessor(BundleAccessor bundleAccessor) {
        this.bundleAccessor = bundleAccessor;
    }
    
    /* 20140310 wangxianliang 解决找不到web容器的spring ApplicationContext问题. BEGIN  */
    protected boolean containsBean2(String beanName) {
    	ServletContext sc = ServletActionContext.getServletContext();
    	if (sc == null)
    		return false;
    	
    	Object attr = sc.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		if (attr == null) {
			return false;
		}
		if (attr instanceof RuntimeException) {
			throw (RuntimeException) attr;
		}
		if (attr instanceof Error) {
			throw (Error) attr;
		}
		if (attr instanceof Exception) {
			throw new IllegalStateException((Exception) attr);
		}
		
    	//Object ac = WebApplicationContextUtils.getWebApplicationContext(sc);
    	if (attr == null)
    		return false;
    	
    	if (OsgiUtil.containsBean(attr, beanName))
            return true;
    	
    	return false;
    }
    
    protected Object getBean2(String beanName) {
    	ServletContext sc = ServletActionContext.getServletContext();
    	if (sc == null)
    		return null;
    	Object attr = sc.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		if (attr == null) {
			return false;
		}
		if (attr instanceof RuntimeException) {
			throw (RuntimeException) attr;
		}
		if (attr instanceof Error) {
			throw (Error) attr;
		}
		if (attr instanceof Exception) {
			throw new IllegalStateException((Exception) attr);
		}
		
    	//Object ac = WebApplicationContextUtils.getWebApplicationContext(sc);
    	if (attr == null)
    		return null;
    	
    	if (OsgiUtil.containsBean(attr, beanName))
            return OsgiUtil.getBean(attr, beanName);
    	
    	return null;
    }
    /* 20140310 wangxianliang 解决找不到web容器的spring ApplicationContext问题. END  */
    public static void main(String[] args) {
    	String symbolicName = "symbolicName_1.0";
    	if (symbolicName != null) {
    		if (symbolicName.startsWith("/")) {
    			symbolicName = symbolicName.substring(1);
    		}
    		int index = symbolicName.indexOf("/");
    		if (index > 0) {
    			symbolicName = symbolicName.substring(0, index);
    		}
    		symbolicName = symbolicName.trim();
    	}
    	System.out.println(symbolicName);
    }
}
