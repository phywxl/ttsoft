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
package org.apache.struts2;

import javax.servlet.Servlet;

import org.apache.struts2.compiler.MemoryClassLoader;
import org.apache.struts2.osgi.DefaultBundleAccessor;
import org.osgi.framework.Bundle;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Caches servlet instances by jsp location. If a requested jsp is not in the cache, "get"
 * will block and wait for the jsp to be loaded
 */
public class ServletCache {
	/*protected final ConcurrentMap<String, Future<Servlet>> cache
            = new ConcurrentHashMap<String, Future<Servlet>>();*/
	
	/* wangxianliang 20130822 �����ͬ�����ͬjsp��������. BEGIN*/
    protected final ConcurrentMap<String, Future<Servlet>> cacheJsp
            = new ConcurrentHashMap<String, Future<Servlet>>();

    protected final ConcurrentMap<Bundle, ConcurrentMap<String, Future<Servlet>>> cacheBundle
    = new ConcurrentHashMap<Bundle, ConcurrentMap<String, Future<Servlet>>>();
    /* wangxianliang 20130822 �����ͬ�����ͬjsp��������. END*/

    private final JSPLoader jspLoader = new JSPLoader();

    /* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. Begin */
    public void clear(Bundle bundle) {
        cacheBundle.remove(bundle);
        cacheJsp.clear();
        
        jspLoader.classLoader.clearBundleMemoryJavaFileObject(bundle.getSymbolicName());
		org.apache.struts2.compiler.MemoryClassLoader m = new org.apache.struts2.compiler.MemoryClassLoader();
		m.copyMemoryJavaFileObject(jspLoader.classLoader);
		jspLoader.classLoader = m;
    }
    /* phywxl 20131211, �����仯��bunlde�ļ��ص��ڴ��jsp class. End */
    
    public void clear() {
        //cache.clear();        
        /* wangxianliang 20130822 �����ͬ�����ͬjsp��������. BEGIN*/
    	cacheJsp.clear();
        cacheBundle.clear();
        jspLoader.classLoader = new org.apache.struts2.compiler.MemoryClassLoader();
        /* wangxianliang 20130822 �����ͬ�����ͬjsp��������. END*/
    }

    public Servlet get(final String location) throws InterruptedException {
        /*while (true) {
            Future<Servlet> future = cache.get(location);
            if (future == null) {
                Callable<Servlet> loadJSPCallable = new Callable<Servlet>() {
                    public Servlet call() throws Exception {
                        return jspLoader.load(location);
                    }
                };
                FutureTask<Servlet> futureTask = new FutureTask<Servlet>(loadJSPCallable);
                future = cache.putIfAbsent(location, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(location, future);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }*/
    	/* wangxianliang 20130822 �����ͬ�����ͬjsp��������. BEGIN*/
    	Bundle bundle = null;
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
				if (entry.getValue().getSymbolicName().equals(symbolicName)) {
					bundle = entry.getValue();
				}
			}
    	}
    	
        while (true) {
            Future<Servlet> future = null;
            if (bundle == null)
            	cacheJsp.get(location);
            else {
            	ConcurrentMap<String, Future<Servlet>> tmp = cacheBundle.get(bundle);
            	if (tmp != null)
            		future = tmp.get(location);
            }
            if (future == null) {
                Callable<Servlet> loadJSPCallable = new Callable<Servlet>() {
                    public Servlet call() throws Exception {
                        return jspLoader.load(location);
                    }
                };
                FutureTask<Servlet> futureTask = new FutureTask<Servlet>(loadJSPCallable);
                if (bundle == null) {
                	future = cacheJsp.putIfAbsent(location, futureTask);
                } else {
                	if (cacheBundle.get(bundle) == null) {
                		cacheBundle.put(bundle, new ConcurrentHashMap<String, Future<Servlet>>());
                	}
                	cacheBundle.get(bundle).putIfAbsent(location, futureTask);
                }
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
            	if (bundle == null) {
            		cacheJsp.remove(location, future);
            	} else {
            		cacheBundle.get(bundle).remove(location, future);
            	}
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
        /* wangxianliang 20130822 �����ͬ�����ͬjsp��������. END*/
    }

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException(t);
    }

}

