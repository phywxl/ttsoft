package ttsoft.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.main.AutoProcessor;
import org.apache.felix.shell.ShellService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsException;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;

import ttsoft.osgi.config.TtOsgiConfiguration;

import com.opensymphony.xwork2.FileManager;
import com.opensymphony.xwork2.FileManagerFactory;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.finder.ResourceFinder;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	protected static final Pattern versionPattern = Pattern.compile("([\\d])+[\\.-]");
	
	protected Felix felix;
	
	public static void main(String[] args) {
		Main main =  new Main();
		main.startFelix();
	}
	
	public void startFelix() {
		//load properties from felix embedded file
        Properties configProps = getProperties("default.properties");

        // Copy framework properties from the system properties.
        org.apache.felix.main.Main.copySystemProperties(configProps);
        replaceSystemPackages(configProps);

        //struts, xwork and felix exported packages
        Properties strutsConfigProps = getProperties("struts-osgi.properties");
        //System.out.println("====FelixOsgiHost.startFelix() strutsConfigProps=" + strutsConfigProps);
        addExportedPackages(strutsConfigProps, configProps);

        //find bundles and adde em to autostart property
        addAutoStartBundles(configProps);

        /* phywxl modify 20130703, �������ԵĻ���Ŀ¼ �� BEGIN */
        String storageDir = null;
        try {
	        String metadatapath = new File("").getAbsolutePath();
	        java.io.File metadatafile = new java.io.File(metadatapath);
	        if (!metadatafile.exists()) metadatafile.mkdirs();
	        if (metadatafile.isFile()) { metadatafile.delete(); metadatafile.mkdirs(); }
	        storageDir = metadatapath + java.io.File.separator + getServletContextParam("struts.osgi.storage", ".felix-cache");
        } catch (Throwable e) {
        	storageDir = System.getProperty("java.io.tmpdir") + getServletContextParam("struts.osgi.storage", ".felix-cache");
        }
        configProps.setProperty(Constants.FRAMEWORK_STORAGE, storageDir);
        /* phywxl modify 20130703, �������ԵĻ���Ŀ¼ �� END. ע�⣺ע��������Ĵ��� */
        /*// Bundle cache
        String storageDir = System.getProperty("java.io.tmpdir") + ".felix-cache";
        configProps.setProperty(Constants.FRAMEWORK_STORAGE, storageDir);*/
        if (LOG.isDebugEnabled())
            LOG.debug("Storing bundles at [#0]", storageDir);

        String cleanBundleCache = getServletContextParam("struts.osgi.clearBundleCache", "true");
        if ("true".equalsIgnoreCase(cleanBundleCache)) {
            if (LOG.isDebugEnabled())
                LOG.debug("Clearing bundle cache");
            configProps.put(FelixConstants.FRAMEWORK_STORAGE_CLEAN, FelixConstants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
        }

        //other properties
        configProps.put(FelixConstants.SERVICE_URLHANDLERS_PROP, "false");
        configProps.put(FelixConstants.LOG_LEVEL_PROP, getServletContextParam("struts.osgi.logLevel", "1"));
        configProps.put(FelixConstants.BUNDLE_CLASSPATH, ".");
        configProps.put(FelixConstants.FRAMEWORK_BEGINNING_STARTLEVEL, getServletContextParam("struts.osgi.runLevel", "3"));

        /* phywxl modify 20130703, ���Ӷ������� �� BEGIN */
        addOtherConfigProperteis(configProps);
        /* phywxl modify 20130703, ���Ӷ������� �� END */
        
        try {
        	//System.out.println("====" + configProps);
            felix = new Felix(configProps);
            felix.init();
            AutoProcessor.process(configProps, felix.getBundleContext());
            felix.start();

            if (LOG.isTraceEnabled()) {
                LOG.trace("Apache Felix is running");
            }
        }
        catch (Exception ex) {
            throw new ConfigurationException("Couldn't start Apache Felix", ex);
        }
	}
	
	protected Properties getProperties(String fileName) {
        ResourceFinder finder = new ResourceFinder("");
        try {
            return finder.findProperties(fileName);
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Unable to read property file [#]", fileName);
            }
            return new Properties();
        }
    }
	
	protected void replaceSystemPackages(Properties properties) {
        //Felix has a way to load the config file and substitution expressions
        //but the method does not have a way to specify the file (other than in an env variable)

        //${jre-${java.specification.version}}
        String systemPackages = (String) properties.get(Constants.FRAMEWORK_SYSTEMPACKAGES);
        String jreVersion = "jre-" + System.getProperty("java.version").substring(0, 3);
        systemPackages = systemPackages.replace("${jre-${java.specification.version}}", (String) properties.get(jreVersion));
        properties.put(Constants.FRAMEWORK_SYSTEMPACKAGES, systemPackages);
    }
	
	 /*
    Find subpackages of the packages defined in the property file and export them
	  */
	protected void addExportedPackages(Properties strutsConfigProps, Properties configProps) {
	    String[] rootPackages = StringUtils.split((String) strutsConfigProps.get("scanning.package.includes"), ",");
	    ResourceFinder finder = new ResourceFinder(StringUtils.EMPTY);
	    List<String> exportedPackages = new ArrayList<String>();
	    //build a list of subpackages
	    for (String rootPackage : rootPackages) {
	        try {
	            String version = null;
	            if (rootPackage.indexOf(";") > 0) {
	                String[] splitted = rootPackage.split(";");
	                rootPackage = splitted[0];
	                version = splitted[1];
	            }
	            Map<URL, Set<String>> subpackagesMap = finder.findPackagesMap(StringUtils.replace(rootPackage.trim(), ".", "/"));
	            for (Map.Entry<URL, Set<String>> entry : subpackagesMap.entrySet()) {
	                URL url = entry.getKey();
	                Set<String> packages = entry.getValue();
	
	                //get version if not set
	                if (StringUtils.isBlank(version)) {
	                    version = getVersion(url);
	                }
	
	                if (packages != null) {
	                    for (String subpackage : packages) {
	                        exportedPackages.add(subpackage + "; version=" + version);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            if (LOG.isErrorEnabled()) {
	                LOG.error("Unable to find subpackages of [#0]", e, rootPackage);
	            }
	        }
	    }
	
	    //make a string with the exported packages and add it to the system properties
	    if (!exportedPackages.isEmpty()) {
	        String systemPackages = (String) configProps.get(Constants.FRAMEWORK_SYSTEMPACKAGES);
	        systemPackages = StringUtils.chomp(systemPackages, ",") + "," + ttsoft.osgi.util.StringUtils.join(exportedPackages, ",");
	        /* phywxl 20130703, ���Ӷ���ϵͳ��BEGIN */
	        String vssyspackinclus = TtOsgiConfiguration.getConfig().getString(TtOsgiConfiguration.SYSTEM_PACKAGTE_INCLUDES);
	        if (vssyspackinclus != null) {
	        	systemPackages += vssyspackinclus.trim();
	        }
	        //System.out.println("====BaseOsgiHost.addExportedPackages(Properties,Properties) systemPackages=" + systemPackages);
	        /* phywxl 20130703, ���Ӷ���ϵͳ��END */
	        configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES, systemPackages);
	    }
	}
	
	/**
     * Gets the version used to export the packages. it tries to get it from MANIFEST.MF, or the file name
     */
    protected String getVersion(URL url) {
        if ("jar".equals(url.getProtocol())) {
            try {
                FileManager fileManager = ServletActionContext.getContext().getInstance(FileManagerFactory.class).getFileManager();
                JarFile jarFile = new JarFile(new File(fileManager.normalizeToFileProtocol(url).toURI()));
                Manifest manifest = jarFile.getManifest();
                if (manifest != null) {
                    String version = manifest.getMainAttributes().getValue("Bundle-Version");
                    if (StringUtils.isNotBlank(version)) {
                        return getVersionFromString(version);
                    }
                } else {
                    //try to get the version from the file name
                    return getVersionFromString(jarFile.getName());
                }
            } catch (Exception e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Unable to extract version from [#0], defaulting to '1.0.0'", url.toExternalForm());
                }
            }
        }
        return "1.0.0";
    }
    
    /**
     * Extracts numbers followed by "." or "-" from the string and joins them with "."
     */
    protected String getVersionFromString(String str) {
        Matcher matcher = versionPattern.matcher(str);
        List<String> parts = new ArrayList<String>();
        while (matcher.find()) {
            parts.add(matcher.group(1));
        }
        //default
        if (parts.size() == 0) {
            return "1.0.0";
        }
        while (parts.size() < 3) {
            parts.add("0");
        }
        return StringUtils.join(parts, ".");
    }
    
    protected void addAutoStartBundles(Properties configProps) {
        //starts system bundles in level 1
        List<String> bundleJarsLevel1 = new ArrayList<String>();
        bundleJarsLevel1.add(getJarUrl(ShellService.class));
        bundleJarsLevel1.add(getJarUrl(ServiceTracker.class));

        configProps.put(AutoProcessor.AUTO_START_PROP + ".1", ttsoft.osgi.util.StringUtils.join(bundleJarsLevel1, " "));

        //get a list of directories under /bundles with numeric names (the runlevel)
        Map<String, String> runLevels = getRunLevelDirs("bundles");
        if (runLevels.isEmpty()) {
            //there are no run level dirs, search for bundles in that dir
            List<String> bundles = getBundlesInDir("bundles");
            if (!bundles.isEmpty()) {
                configProps.put(AutoProcessor.AUTO_START_PROP + ".2", StringUtils.join(bundles, " "));
            }
        } else {
        	/* phywxl modify 20130703, BEGIN */
        	/*List<String> rls = new ArrayList<String>();
        	rls.addAll(runLevels.keySet());
        	java.util.Collections.sort(rls, new java.util.Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					return Integer.parseInt(o1) - Integer.parseInt(o2);
				}
        	});
            for (String runLevel : rls) {*/
            /* phywxl modify 20130703, END */
            for (String runLevel : runLevels.keySet()) {
            
                if ("1".endsWith(runLevel)) {
                    throw new StrutsException("Run level dirs must be greater than 1. Run level 1 is reserved for the Felix bundles");
                }
                List<String> bundles = getBundlesInDir(runLevels.get(runLevel));
                configProps.put(AutoProcessor.AUTO_START_PROP + "." + runLevel, ttsoft.osgi.util.StringUtils.join(bundles, " "));
            }
        }
    }
    
    protected String getJarUrl(Class clazz) {
        ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URL loc = codeSource.getLocation();
        return loc.toString();
    }
    
    /**
     * Return a list of directories under a directory whose name is a number
     */
    protected Map<String, String> getRunLevelDirs(String dir) {
        Map<String, String> dirs = new HashMap<String, String>();
        try {
            ResourceFinder finder = new ResourceFinder();
            URL url = finder.find("bundles");
            if (url != null) {
                if ("file".equals(url.getProtocol())) {
                    File bundlesDir = new File(url.toURI());
                    String[] runLevelDirs = bundlesDir.list(new FilenameFilter() {
                        public boolean accept(File file, String name) {
                            try {
                                return file.isDirectory() && Integer.valueOf(name) > 0;
                            } catch (NumberFormatException ex) {
                                //the name is not a number
                                return false;
                            }
                        }
                    });

                    if (runLevelDirs != null && runLevelDirs.length > 0) {
                        //add all the dirs to the list
                        for (String runLevel : runLevelDirs) {
                            dirs.put(runLevel, StringUtils.chomp(dir,  "/") + "/" + runLevel);
                        }
                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug("No run level directories found under the [#0] directory", dir);
                    }
                } else if (LOG.isWarnEnabled()) {
                    LOG.warn("Unable to read [#0] directory", dir);
                }
            } else if (LOG.isWarnEnabled()) {
                LOG.warn("The [#0] directory was not found", dir);
            }
        } catch (Exception e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Unable load bundles from the [#0] directory", e, dir);
            }
        }
        return dirs;
    }
    
    protected List<String> getBundlesInDir(String dir) {
        List<String> bundleJars = new ArrayList<String>();
        try {
            ResourceFinder finder = new ResourceFinder();
            URL url = finder.find(dir);
            if (url != null) {
                if ("file".equals(url.getProtocol())) {
                    File bundlesDir = new File(url.toURI());
                    File[] bundles = bundlesDir.listFiles(new FilenameFilter() {
                        public boolean accept(File file, String name) {
                            return ttsoft.osgi.util.StringUtils.endsWith(name, ".jar");
                        }
                    });

                    if (bundles != null && bundles.length > 0) {
                        //add all the bundles to the list
                        for (File bundle : bundles) {
                            String externalForm = bundle.toURI().toURL().toExternalForm();
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Adding bundle [#0]", externalForm);
                            }
                            bundleJars.add(externalForm);
                        }

                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug("No bundles found under the [#0] directory", dir);
                    }
                } else if (LOG.isWarnEnabled()) {
                    LOG.warn("Unable to read [#0] directory", dir);
                }
            } else if (LOG.isWarnEnabled()) {
                LOG.warn("The [#0] directory was not found", dir);
            }
        } catch (Exception e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Unable load bundles from the [#0] directory", e, dir);
            }
        }
        return bundleJars;
    }
    
    /**
     * Gets a param from the ServletContext, returning the default value if the param is not set
     *
     * @param paramName    the name of the param to get from the ServletContext
     * @param defaultValue value to return if the param is not set
     * @return
     */
    protected String getServletContextParam(String paramName, String defaultValue) {
        return StringUtils.defaultString(TtOsgiConfiguration.getConfig().getString(paramName), defaultValue);
    }
    
    /* phywxl modify 20130703, ���Ӷ������� �� BEGIN */
    private void addOtherConfigProperteis(Properties props) {
    	TtOsgiConfiguration config = TtOsgiConfiguration.getConfig();
    	java.util.Enumeration<String> names = config.getNames();
    	if (names != null) {
    		String key = null;
    		String value = null;
    		while (names.hasMoreElements()) {
    			key = names.nextElement();
    			if (key != null) {
    				value = config.getString(key);
    				props.put(key, value);
    			}
    		}
    	}
	}
    /* phywxl modify 20130703, ���Ӷ������� �� END */
}
