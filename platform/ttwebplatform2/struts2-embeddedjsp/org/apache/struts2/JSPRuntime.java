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

import ttsoft.osgi.embeddedjsp.OsgiRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.views.util.UrlHelper;
import org.osgi.framework.Bundle;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;
import java.util.Map;

/**
 * Maintains a cache of jsp locations -> servlet instances for those jsps. When a jsp is requested
 * from the cache, the cache will block if the jsp was not compiled already, and wait for the compilation
 */
public abstract class JSPRuntime {
    //maps from jsp path -> pagelet
    protected static final ServletCache servletCache = new ServletCache();

    public static void clearCache() {
        servletCache.clear();
    }
    /* phywxl 20131211, 仅清除变化的bunlde的加载到内存的jsp class. Begin */
    public static void clearCache(Bundle bundle) {
        servletCache.clear(bundle);
    }
    /* phywxl 20131211, 仅清除变化的bunlde的加载到内存的jsp class. End */

    public static void handle(String location) throws Exception {
        handle(location, false);
    }

    public static void handle(String location, boolean flush) throws Exception {
        final HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        final UrlHelper urlHelper = ServletActionContext.getContext().getInstance(UrlHelper.class);

        int i = location.indexOf("?");
        if (i > 0) {
            //extract params from the url and add them to the request
            Map<String, Object> parameters = ActionContext.getContext().getParameters();
            String query = location.substring(i + 1);
            Map<String, Object> queryParams = urlHelper.parseQueryString(query, true);
            if (queryParams != null && !queryParams.isEmpty()) {
                parameters.putAll(queryParams);
                
                /* phywxl 20130704 modify. 修正无法通过request.getParamter("");获得请求参数的问题 . BEGIN */
                OsgiRequestWrapper reqwrap = new OsgiRequestWrapper(request);
                java.util.Iterator<?> it= queryParams.entrySet().iterator();
        		while(it.hasNext()){
        			java.util.Map.Entry<?,?> entry = (java.util.Map.Entry<?,?>)it.next();
        			String key = String.valueOf(entry.getKey());
        			Object obj = entry.getValue();
        			if (obj instanceof String[]) {
        				String[] ss = (String[])obj;
        				if (ss != null) {
        					for (String s : ss) {
        						reqwrap.setParameter(key, s);
        						//System.out.println("JSPRuntime.handle(...) location=" + location + ", querystring key=" + key + ", value=" + s);
        					}
        				}
        			} else {
        				String value = String.valueOf(obj);
        				reqwrap.setParameter(key, value);
        				//System.out.println("JSPRuntime.handle(...) location=" + location + ", querystring key=" + key + ", value=" + value);
        			}
        			
        		}
        		request = reqwrap;
        		ServletActionContext.setRequest(reqwrap);
        		/* phywxl 20130704 modify. 修正无法通过request.getParamter("");获得请求参数的问题 . END */
            }
            location = location.substring(0, i);
        }

        Servlet servlet = servletCache.get(location);
        HttpJspPage page = (HttpJspPage) servlet;

        page._jspService(request, response);
        if (flush)
            response.flushBuffer();
    }
    
    /**
     * 增加此方法
     * 解决jsp:include指令输出时被包含的文件先输出，主文件后输出的问题
     * 同时修改了Generator.GenerateVisitor类visit(Node.IncludeAction n)方法
     * @param location
     * @param out
     * @param flush
     * @throws Exception
     */
    public static void handle(String location, javax.servlet.jsp.JspWriter out, boolean flush) throws Exception {
        if ((flush) && !(out instanceof javax.servlet.jsp.tagext.BodyContent)) {
            out.flush();
        }
        final HttpServletResponse response = ServletActionContext.getResponse();
    	ServletActionContext.setResponse(new org.apache.struts2.jasper.runtime.ServletResponseWrapperInclude(response, out));
    	
    	handle(location, flush);
    }
}
