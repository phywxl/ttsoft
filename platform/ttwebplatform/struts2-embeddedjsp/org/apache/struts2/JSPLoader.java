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

import ttsoft.osgi.config.TtOsgiConfiguration;

import com.opensymphony.xwork2.FileManager;
import com.opensymphony.xwork2.FileManagerFactory;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.finder.UrlSet;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.compiler.MemoryClassLoader;
import org.apache.struts2.compiler.MemoryJavaFileObject;
import org.apache.struts2.jasper.JasperException;
import org.apache.struts2.jasper.JspC;
import org.apache.struts2.osgi.BundleClassLoaderInterface;
import org.apache.struts2.osgi.DefaultBundleAccessor;
import org.osgi.framework.Bundle;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspPage;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uses jasper to extract a JSP from the classpath to a file and compile it. The classpath used for
 * compilation is built by finding all the jar files using the current class loader (Thread), plus
 * directories.
 */
public class JSPLoader {
    private static final Logger LOG = LoggerFactory.getLogger(JSPLoader.class);

    /* phywxl 20130704. private -> public */
    public MemoryClassLoader classLoader = new MemoryClassLoader();
    private static final String DEFAULT_PACKAGE = "org.apache.struts2.jsp";

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package (.*?);");
    private static final Pattern CLASS_PATTERN = Pattern.compile("public final class (.*?) ");
    
    
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
    				if (entry.getValue().getSymbolicName().equals(location)) {
    					return entry.getValue();
    				}
    			}
    		}
    	}
    	
    	try {
    		String symbolicName = ServletActionContext.getActionMapping().getNamespace();
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
        			if (entry != null && entry.getValue() != null) {
	    				if (entry.getValue().getSymbolicName().equals(symbolicName)) {
	    					return entry.getValue();
	    				}
        			}
    			}
        	}
    	} catch (Exception e) {}
    	
    	return null;
    }

    public Servlet load(String location) throws Exception {
        location = StringUtils.substringBeforeLast(location, "?");
        
        Bundle bundle = DefaultBundleAccessor.getInstance() == null ? null : DefaultBundleAccessor.getInstance().getCurrentBundle();
        if (bundle == null)
        	bundle = this.getBundle(location);
        if (bundle != null) {
        	DefaultBundleAccessor.getInstance().setCurrentBundle(bundle);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compiling JSP [#0]", location);
        }

        //use Jasper to compile the JSP into java code
        JspC jspC = compileJSP(location);
        String source = jspC.getSourceCode();
        /* wangxianliang 20140414 add. 设置编码。 BEGIN */
        TtOsgiConfiguration config = TtOsgiConfiguration.getConfig();
        //String sencode = config.getString(config.JSP_COMPILER_ENCODING_SRC, "GBK");
        //String oencode = config.getString(config.JSP_COMPILER_ENCODING_OUT, "GBK");
        //source = new String(source.getBytes("ISO-8859-1"), oencode);
        /* wangxianliang 20140414 add. 设置编码。 END */

        String className = extractClassName(source);
        //System.out.println("====JSPLoader.load(String) className=" + className);
        
        /* phywxl 20130704 add. 输出java源码。 BEGIN */
        //VictorysoftOsgiConfiguration config = VictorysoftOsgiConfiguration.getConfig();

        boolean outJava = config.getBoolean(config.JSP_COMPILER_OUT_JAVA);
        if (outJava) {
        	java.io.File f = new java.io.File(config.getString(config.JSP_COMPILER_OUT_DIR));
        	if (!f.exists())
        		f.mkdirs();
        	StringBuffer sb = new StringBuffer();
        	for (int i = 0; i < className.length(); i++) {
        		if (className.charAt(i) == '.')
        			sb.append(java.io.File.separator);
        		else
        			sb.append(className.charAt(i));
        	}
        	String path = sb.toString();
        	int index = path.lastIndexOf(java.io.File.separator);
        	f = new java.io.File(f, path.substring(0, index));
        	if (!f.exists())
        		f.mkdirs();
        	f = new java.io.File(f, path.substring(index + 1) + ".java");
        	if (!f.exists())
        		f.createNewFile();
        	java.io.FileOutputStream out = new java.io.FileOutputStream(f);
        	out.write(source.getBytes());
        }
        /* phywxl 20130704 add. 输出java源码。 END */

        //use Java Compiler API to compile the java code into a class
        //the tlds that were discovered are added (their jars) to the classpath
        compileJava(location, className, source, jspC.getTldAbsolutePaths());

        //load the class that was just built
        Class clazz = classLoader.loadClass(location, className);//Class clazz = Class.forName(className, false, classLoader);
        return createServlet(clazz);
    }

    private String extractClassName(String source) {
        Matcher matcher = PACKAGE_PATTERN.matcher(source);
        matcher.find();
        String packageName = matcher.group(1);

        matcher = CLASS_PATTERN.matcher(source);
        matcher.find();
        String className = matcher.group(1);

        return packageName + "." + className;
    }

    /**
     * Creates and inits a servlet
     */
    private Servlet createServlet(Class clazz) throws IllegalAccessException, InstantiationException, ServletException {
        JSPServletConfig config = new JSPServletConfig(ServletActionContext.getServletContext());

        Servlet servlet = (Servlet) clazz.newInstance();
        servlet.init(config);

        /*
         there is no need to call JspPage.init explicitly because Jasper's
         JSP base classe HttpJspBase.init(ServletConfig) calls:
         jspInit();
         _jspInit();
         */

        return servlet;
    }

    /**
     * Compiles the given source code into java bytecode
     */
    private void compileJava(final String path, String className, final String source, Set<String> extraClassPath) throws IOException {
        if (LOG.isTraceEnabled())
            LOG.trace("Compiling [#0], source: [#1]", className, source);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        //the generated bytecode is fed to the class loader
        JavaFileManager jfm = new ForwardingJavaFileManager<StandardJavaFileManager>(
        		compiler.getStandardFileManager(diagnostics, null, null)) {
                    @Override
                    public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                        MemoryJavaFileObject fileObject = new MemoryJavaFileObject(name, kind);
                        classLoader.addMemoryJavaFileObject(path, name, fileObject);
                        return fileObject;
                    }
                };

        //read java source code from memory
        String fileName = className.replace('.', '/') + ".java";
        SimpleJavaFileObject sourceCodeObject = new SimpleJavaFileObject(toURI(fileName), JavaFileObject.Kind.SOURCE) {
            @Override
            public CharSequence getCharContent(boolean
                    ignoreEncodingErrors)
                    throws IOException, IllegalStateException,
                    UnsupportedOperationException {
                return source;
            }

        };

        //build classpath
        //some entries will be added multiple times, hence the set
        List<String> optionList = new ArrayList<String>();
        Set<String> classPath = new HashSet<String>();

        FileManager fileManager = ServletActionContext.getContext().getInstance(FileManagerFactory.class).getFileManager();

        URL tu = this.getClass().getResource("/ttsoft-osgi.properties");
        File f = FileUtils.toFile(tu);
        String p = f.getAbsolutePath();
        if (p.indexOf("WEB-INF") > 0) {
        	//System.out.println("====JSPLoader.compileJava(...) classes path=" + ( p.substring(0, p.indexOf("WEB-INF")) + "WEB-INF" + File.separator + "classes" ));
        	classPath.add( p.substring(0, p.indexOf("WEB-INF")) + "WEB-INF" + File.separator + "classes" );
        }
        
        //find available jars
        ClassLoaderInterface classLoaderInterface = getClassLoaderInterface();
        UrlSet urlSet = new UrlSet(classLoaderInterface);

        //find jars
        List<URL> urls = urlSet.getUrls();
        //System.out.println("====JSPLoader.compileJava(...) urls=" + urls);
        for (URL url : urls) {
            URL normalizedUrl = fileManager.normalizeToFileProtocol(url);
            File file = FileUtils.toFile(ObjectUtils.defaultIfNull(normalizedUrl, url));
            if (file.exists())
                classPath.add(file.getAbsolutePath());
        }
        
        /* wangxianliang 20140410 add. 增加WEB-INF/lib所有的jar。 BEGIN */
        classLoaderInterface = new ClassLoaderInterfaceDelegate(JSPLoader.class.getClassLoader());
        urlSet = new UrlSet(classLoaderInterface);

        //find jars
        urls = urlSet.getUrls();
        //System.out.println("====JSPLoader.compileJava(...) urls=" + urls);
        for (URL url : urls) {
            URL normalizedUrl = fileManager.normalizeToFileProtocol(url);
            File file = FileUtils.toFile(ObjectUtils.defaultIfNull(normalizedUrl, url));
            if (file.exists())
                classPath.add(file.getAbsolutePath());
        }
        /* wangxianliang 20140410 add. 增加WEB-INF/lib所有的jar。 END */

        //these should be in the list already, but I am feeling paranoid
        //this jar
        classPath.add(getJarUrl(EmbeddedJSPResult.class));
        //servlet api
        classPath.add(getJarUrl(Servlet.class));
        //jsp api
        classPath.add(getJarUrl(JspPage.class));
        //el api
        classPath.add(getJarUrl(javax.el.ELContext.class));

        try {
            Class annotationsProcessor = Class.forName("org.apache.AnnotationProcessor");
            classPath.add(getJarUrl(annotationsProcessor));
        } catch (ClassNotFoundException e) {
            //ok ignore
        }

        //add extra classpath entries (jars where tlds were found will be here)
        for (Iterator<String> iterator = extraClassPath.iterator(); iterator.hasNext();) {
            String entry = iterator.next();
            classPath.add(entry);
        }

        String classPathString = StringUtils.join(classPath, File.pathSeparator);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Compiling [#0] with classpath [#1]", className, classPathString);
        }

        optionList.addAll(Arrays.asList("-classpath", classPathString));

        //compile
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, jfm, diagnostics, optionList, null,
                Arrays.asList(sourceCodeObject));

        if (!task.call()) {
            throw new StrutsException("Compilation failed:" + diagnostics.getDiagnostics().get(0).toString());
        }
    }

    protected String getJarUrl(Class clazz) {
        ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URL loc = codeSource.getLocation();
        File file = FileUtils.toFile(loc);
        return file.getAbsolutePath();
    }

    private JspC compileJSP(String location) throws JasperException {
        JspC jspC = new JspC();
        jspC.setClassLoaderInterface(getClassLoaderInterface());
        /* phywxl 20130704 add. 设置编码。 BEGIN */
        TtOsgiConfiguration config = TtOsgiConfiguration.getConfig();
        String sencode = config.getString(config.JSP_COMPILER_ENCODING_SRC, "GBK");
        jspC.setJavaEncoding(sencode);
        /* phywxl 20130704 add. 设置编码。 END */
        jspC.setCompile(false);
        jspC.setJspFiles(location);
        
        /* wangxianliang 20130822 解决不同插件相同jsp类名相同问题. BEGIN*/
        //jspC.setPackage(DEFAULT_PACKAGE);
        boolean hasPlugin = false;
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
					hasPlugin = true;
					break;
				}
			}
    	}
    	if (symbolicName == null) {
    		symbolicName = DefaultBundleAccessor.getInstance().getCurrentBundle() == null ? null : DefaultBundleAccessor.getInstance().getCurrentBundle().getSymbolicName();
    	}
    	if (symbolicName != null) {
    		hasPlugin = true;
    	}
    	
    	if (hasPlugin) {
    		symbolicName = symbolicName.replaceAll("\\.", "_");
    		jspC.setPackage(DEFAULT_PACKAGE + "." + symbolicName);
    	} else {
    		jspC.setPackage(DEFAULT_PACKAGE);
    	}
    	/* wangxianliang 20130822 解决不同插件相同jsp类名相同问题. END*/
    	
        
        jspC.execute();
        return jspC;
    }

    private ClassLoaderInterface getClassLoaderInterface() {
        ClassLoaderInterface classLoaderInterface = null;
        ServletContext ctx = ServletActionContext.getServletContext();
        if (ctx != null)
            classLoaderInterface = (ClassLoaderInterface) ctx.getAttribute(ClassLoaderInterface.CLASS_LOADER_INTERFACE);

        return (ClassLoaderInterface) ObjectUtils.defaultIfNull(classLoaderInterface, new ClassLoaderInterfaceDelegate(JSPLoader.class.getClassLoader()));
    }

    private static URI toURI(String name) {
        try {
            return new URI(name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
   
}
