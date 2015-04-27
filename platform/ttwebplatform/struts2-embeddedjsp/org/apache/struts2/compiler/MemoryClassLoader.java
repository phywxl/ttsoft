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
package org.apache.struts2.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.osgi.DefaultBundleAccessor;
import org.osgi.framework.Bundle;

/**
 * Keeps a cache of class name -> MemoryJavaFileObject. If the requested class name is in the cache
 * a new class is defined for it, otherwise findClass delegates to the parent class loader
 */
public class MemoryClassLoader extends ClassLoader {
    private Map<String, MemoryJavaFileObject> cachedObjects = new ConcurrentHashMap<String, MemoryJavaFileObject>();
    private Map<String, Map<String, MemoryJavaFileObject>> bundleCachedObjects = new ConcurrentHashMap<String, Map<String, MemoryJavaFileObject>>();

    public MemoryClassLoader() {
        //without this, the tests will not run, because the tests are loaded by a custom classloader
        //so the classes referenced from the compiled code will not be found by the System Class Loader because
        //the target dir is not part of the classpath used when calling the jvm to execute the tests
        //super(Thread.currentThread().getContextClassLoader());
        /* phywxl 20170704 add. ���ȼ��ز������. BEGIN. ע�⣺ע��������1����� */
        super(new java.net.URLClassLoader(new java.net.URL[]{}, Thread.currentThread().getContextClassLoader()){
        	@Override
    		protected Class<?> findClass(String name)
    				throws ClassNotFoundException {
    			//System.out.println("====MemoryClassLoader.findClass(\"" + name + "\")");
    			Class<?> clazz= null;
    			try{
    				clazz = DefaultBundleAccessor.getInstance().loadClass(name);
    				if(clazz!=null){
        				return clazz;
        			}
    			} catch(Exception e){
    				clazz = null;
    			}
    			clazz = MemoryClassLoader.class.getClassLoader().loadClass(name);
    			if(clazz!=null){
    				return clazz;
    			}
    			System.out.println("=============MemoryClassLoader classloader = " + MemoryClassLoader.class.getClassLoader());
    			return super.findClass(name);
    		}
    		
    		@Override
    		public Class<?> loadClass(String name)
    				throws ClassNotFoundException {
    			//System.out.println("====MemoryClassLoader.loadClass(\"" + name + "\")");
    			Class<?> clazz= null;
    			try{
    				clazz = DefaultBundleAccessor.getInstance().loadClass(name);
    				if(clazz!=null){
        				return clazz;
        			}
    			} catch(Exception e){
    				clazz = null;
    			}
    			clazz = MemoryClassLoader.class.getClassLoader().loadClass(name);
    			if(clazz!=null){
    				return clazz;
    			}
    			return super.loadClass(name);
    		}
        });
        /* phywxl 20170704 add. ���ȼ��ز������. END  */
    }
    
    /* phywxl 20170704 add. BEGIN  */
    public Class<?> loadClass(String location, String name) throws ClassNotFoundException {
    	Bundle bundle = this.getBundle(location);
    	if (bundle != null) {
    		Map<String, MemoryJavaFileObject> map = bundleCachedObjects.get(bundle.getSymbolicName());
    		if (map != null) {
	    		MemoryJavaFileObject fileObject = map.get(name);
	    		if (fileObject != null) {
	                byte[] bytes = fileObject.toByteArray();
	                return defineClass(name, bytes, 0, bytes.length);
	            }
    		}
    	}
    	return super.loadClass(name);
    }
    /* phywxl 20170704 add. END  */

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        MemoryJavaFileObject fileObject = cachedObjects.get(name);
        if (fileObject != null) {
            byte[] bytes = fileObject.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }
    public void addMemoryJavaFileObject(String jsp, MemoryJavaFileObject memoryJavaFileObject) {
        cachedObjects.put(jsp, memoryJavaFileObject);
    }
    /* phywxl 20130704 add. BEGIN */
    public void addMemoryJavaFileObject(String location, String jsp, MemoryJavaFileObject memoryJavaFileObject) {
    	Bundle bundle = this.getBundle(location);
    	if (bundle == null) {
    		cachedObjects.put(jsp, memoryJavaFileObject);
    	} else {
    		Map<String, MemoryJavaFileObject> map = bundleCachedObjects.get(bundle.getSymbolicName());
    		if (map == null) {
    			map = new HashMap<String, MemoryJavaFileObject>();
    			bundleCachedObjects.put(bundle.getSymbolicName(), map);
    		}
    		map.put(jsp, memoryJavaFileObject);
    	}
    }
    private Bundle getBundle(String location) {
    	if (location == null || location.trim().equals(""))
    		return null;
    	location = location.trim();
    	
    	while (location.startsWith("/")) location = location.substring(1);
    	while (location.startsWith("\\")) location = location.substring(1);
    	while (location.startsWith("/")) location = location.substring(1);
    	int i = location.indexOf("/");
    	if (i > 0) {
    		location = location.substring(0, i);
    		if (location != null && location.length() > 0) {
    			for (Map.Entry<String, Bundle> entry : DefaultBundleAccessor.getInstance().getActiveBundles().entrySet()) {
    				if (entry.getValue() == null || entry.getValue().getSymbolicName() == null) continue;
    				if (entry.getValue().getSymbolicName().equals(location)) {
    					return entry.getValue();
    				}
    			}
    		}
    	}
    	
    	String symbolicName = ServletActionContext.getActionMapping() == null ? null : ServletActionContext.getActionMapping().getNamespace();
    	if (symbolicName != null) {
    		if (symbolicName.startsWith("/")) {
    			symbolicName = symbolicName.substring(1);
    		}
    		int index = symbolicName.indexOf("/");
    		if (index > 0) {
    			symbolicName = symbolicName.substring(0, index);
    		}
    		symbolicName = symbolicName.trim();
    		
    		for (Map.Entry<String, Bundle> entry : DefaultBundleAccessor.getInstance().getActiveBundles().entrySet()) {
    			if (entry.getValue() == null || entry.getValue().getSymbolicName() == null) continue;
				if (entry.getValue().getSymbolicName().equals(symbolicName)) {
					return entry.getValue();
				}
			}
    	}
    	
    	return null;
    }
    public void clearBundleMemoryJavaFileObject() {
    	cachedObjects.clear();
    	bundleCachedObjects.clear();
    }
    /* phywxl 20130704 add. END */
    /* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. Begin */
    public void clearBundleMemoryJavaFileObject(String symbolicName) {
    	bundleCachedObjects.remove(symbolicName);
    }
    public void copyMemoryJavaFileObject(MemoryClassLoader m) {
    	this.cachedObjects.putAll(m.cachedObjects);
    	this.bundleCachedObjects.putAll(m.bundleCachedObjects);
    }
    /* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. End */
}