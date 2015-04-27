package ttsoft.osgi.web.pluginres.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.springframework.osgi.context.BundleContextAware;

public class PluginResourceFilter implements Filter, BundleContextAware {
	private static BundleContext bundleContext; //osgi容器context
	private FilterConfig filterConfig;          //filter config
	private String[] pluginResourceExcludePaths;//过滤资源排除哪些路径模式，例如：.*\.action$
	private String[] pluginResourcePathPrefix;  //插件内资源所在目录，例如：/WebRoot
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		//获得过滤资源排除路径模式
		String paths = filterConfig.getInitParameter("pluginResourceExcludePaths");
		if (paths != null && !paths.trim().equals("")) {
			pluginResourceExcludePaths = paths.split(",");
			if (pluginResourceExcludePaths != null) {
				for (int i = 0; i < pluginResourceExcludePaths.length; i++) {
					pluginResourceExcludePaths[i] = (pluginResourceExcludePaths[i] == null || pluginResourceExcludePaths[i]
							.trim().equals("")) ? null
							: pluginResourceExcludePaths[i].trim();
				}
			}
		}
		//System.out.println("PluginResourceFilter pluginResourceExcludePaths=" + pluginResourceExcludePaths);
		//获得插件内资源目录，默认/WebRoot
		paths = filterConfig.getInitParameter("pluginResourcePathPrefix");
		if (paths != null && !paths.trim().equals("")) {
			pluginResourcePathPrefix = paths.split(",");
			if (pluginResourcePathPrefix != null) {
				for (int i = 0; i < pluginResourcePathPrefix.length; i++) {
					pluginResourcePathPrefix[i] = (pluginResourcePathPrefix[i] == null || pluginResourcePathPrefix[i]
							.trim().equals("")) ? null
							: pluginResourcePathPrefix[i].trim();
				}
			}
		}
		if (pluginResourcePathPrefix == null)
			pluginResourcePathPrefix = new String[]{"/WebRoot"};
		//System.out.println("PluginResourceFilter pluginResourcePathPrefix=" + pluginResourcePathPrefix);
	}
	
	@Override
	public void destroy() {
		bundleContext = null;
		filterConfig = null;
		pluginResourceExcludePaths = null;
	}
	
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		if (sresponse.isCommitted())
			return;
		
		HttpServletRequest request = (HttpServletRequest)srequest;
		HttpServletResponse response = (HttpServletResponse)sresponse;
		
		//请求url，例如：/static/html/a.html;X=X?Y+Y&Z=Z
		String requestUrl = buildRequestUrl(request.getServletPath(),
				request.getRequestURI(), request.getContextPath(),
				request.getPathInfo(), request.getQueryString());
		//System.out.println("PluginResourceFilter requestUrl=" + requestUrl);
		
		//去掉query string和;内容，例如：/static/html/a.html
		int index = -1;
		String neatUrl = requestUrl;
		index = neatUrl.indexOf("?");
		if (index >= 0)
			neatUrl = neatUrl.substring(0, index);
		index = neatUrl.indexOf(";");
		if (index >= 0)
			neatUrl = neatUrl.substring(0, index);
		if (neatUrl == null || neatUrl.trim().equals("")) {
			chain.doFilter(srequest, sresponse);
			return;
		}
		//System.out.println("PluginResourceFilter neatUrl=" + requestUrl);
		
		//是否在排除路径，pluginResourceExcludePaths
		if (pluginResourceExcludePaths != null) {
			for (String pat : pluginResourceExcludePaths) {
				if (pat != null && neatUrl.matches(pat)) {
					//System.out.println("PluginResourceFilter exclude pattern=" + pat + " path=" + requestUrl);
					chain.doFilter(srequest, sresponse);
					return;
				}
			}
		}
		
		//资源后缀
		String resSuffix = null;
		index = neatUrl.lastIndexOf(".");
		if (index > 0) {
			resSuffix = neatUrl.substring(index);
		}
		
		//形成插件内资源路径集合，例如 /WebRoot/static/css/main.css、/static/css/main.css、/插件ID/static/css/main.css
		List<String> pathReses = new ArrayList<String>();
		//根据插件名构建路径
		String pluginTmpId = this.getPlunginIdFromPath(neatUrl); //从路径中解析第一个目录作为插件id
		boolean isPluginId = false;
		Bundle bundle = null;
		if (pluginTmpId != null) {
			List<Bundle> activeBundles = getActiveBundles();  //所有插件
			//是否插件ID
			for (Bundle b : activeBundles) {
				if (b == null || b.getBundleId() == 0L || b.getSymbolicName() == null) {
					continue;
				}
				if (b.getSymbolicName().trim().equals(pluginTmpId)) {
					isPluginId = true;
					
					if (bundle  == null) {
						bundle = b;
						continue;
					} else {
						if (b.getVersion().compareTo(bundle.getVersion()) > 0) {
							bundle = b;
						}
					}
				}
			}
			//是插件，增加资源前缀
			if (isPluginId) {
				String t = neatUrl.substring(neatUrl.indexOf(pluginTmpId) + pluginTmpId.length());
				
				String v = null;
				Version version = null;
				v = getVersion(t);
				if (v != null) {
					try {
						version = Version.parseVersion(v);
					} catch (Throwable e) {
						version = null;
					}
				}
				
				if (version != null) {
					t = t.substring(v.length() + 1);
					
					for (Bundle b : activeBundles) {
						if (b.getSymbolicName().trim().equals(pluginTmpId) && version.equals(b.getVersion())) {
							bundle = b;
							break;
						}
					}
				}
				
				for (String prefix : pluginResourcePathPrefix) {
					if (prefix != null)
						pathReses.add(prefix + t);
				}
				pathReses.add(t);
			}
		}
		pathReses.add(neatUrl);
		
		List<String> paths = new ArrayList<String>();
		paths.addAll(pathReses);
		
		URL url = null;
		//如果请求路径有插件ID，根据此插件获取资源
		if (isPluginId && bundle != null) {
			for (String path : paths) {
				if (path == null || path.trim().equals(""))
					continue;
				//去掉“/”字符
				while (path.startsWith("/"))
					path = path.substring(1);
				
				url = bundle.getResource(path);
				
				if (url != null)
					break;
			}
		}
		if (url != null) {
			try {
				//mime-type
				this.mimeType(resSuffix, response);
				//此处需要增加http cache设置，包括Expires和Cache-control头
				this.setCacheHeader(365 * 24 * 60 * 60, request, response);
				//下述方法参数1的值可以从缓存中获取，以加快访问速度
				this.output(url.openStream(), response.getOutputStream());
				//System.out.println("PluginResourceFilter out resource from ispluginid bundle=" + bundle + ", url=" + url);
				return;
			} catch (Throwable e) {
			}
		}
		
		
		//继续下一过滤器
		chain.doFilter(srequest, sresponse);
	}
	
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		PluginResourceFilter.bundleContext = bundleContext;
	}
	
	public String getVersion(String path) {
		String p = null;
		if (path == null || (path = path.trim()).equals("")) {
			return null;
		}
		while (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (path.indexOf("/") > 0) {
			return path.substring(0, path.indexOf("/"));
		}
		return null;
	}
	
	public void setCacheHeader(long adddays, HttpServletRequest request, HttpServletResponse response) {
		long adddaysM = adddays * 1000;
		
		response.addHeader("ETag", Long.toString(System.currentTimeMillis() + adddaysM));
		response.addHeader("Cache-Control", "max-age=" + adddays);
		response.addDateHeader("Last-Modified", System.currentTimeMillis() + adddaysM);
		response.addDateHeader("Expires", System.currentTimeMillis() + adddaysM);
	}
	
	private String buildRequestUrl(String servletPath,
			String requestURI, String contextPath, String pathInfo,
			String queryString) {
		String uri = servletPath;

		if (uri == null) {
			uri = requestURI;
			uri = uri.substring(contextPath.length());
		}

		return uri
				+ ((pathInfo == null) ? "" : pathInfo)
				+ ((queryString == null) ? "" : new StringBuffer()
						.append("?").append(queryString).toString());
	}
	
	private List<Bundle> getActiveBundles() {
		List<Bundle> bundles = new ArrayList<Bundle>();
        for (Bundle bundle : bundleContext.getBundles()) {
            if (bundle.getState() == Bundle.ACTIVE)
                bundles.add(bundle);
        }

        return Collections.unmodifiableList(bundles);
    }
	
	private String getPlunginIdFromPath(String path) {
		if (path == null || path.trim().equals(""))
			return null;
		
		path = path.trim();
		
		while (path.startsWith("/"))
			path = path.substring(1);
		
		int index = path.indexOf("/");
		
		if (index > 0)
			return path.substring(0,index);
		
		return null;
	}
	
	private void mimeType(String resSuffix, HttpServletResponse response) {
		if (resSuffix == null || resSuffix.trim().equals(""))
			return;
		if (response == null)
			return;
		
		resSuffix = resSuffix.trim();
		
		String mime = null;
		if (resSuffix.compareToIgnoreCase(".323") == 0)
			mime = "text/h323";
		else if (resSuffix.compareToIgnoreCase(".acx") == 0)
			mime = "application/internet-property-stream";
		else if (resSuffix.compareToIgnoreCase(".ai") == 0)
			mime = "application/postscript";
		else if (resSuffix.compareToIgnoreCase(".aif") == 0 || resSuffix.compareToIgnoreCase(".aifc") == 0 || resSuffix.compareToIgnoreCase(".aiff") == 0)
			mime = "audio/x-aiff";
		else if (resSuffix.compareToIgnoreCase(".asf") == 0 || resSuffix.compareToIgnoreCase(".asr") == 0 || resSuffix.compareToIgnoreCase(".asx") == 0)
			mime = "video/x-ms-asf";
		else if (resSuffix.compareToIgnoreCase(".au") == 0)
			mime = "audio/basic";
		else if (resSuffix.compareToIgnoreCase(".avi") == 0)
			mime = "video/x-msvideo";
		else if (resSuffix.compareToIgnoreCase(".css") == 0)
			mime = "text/css";
		else if (resSuffix.compareToIgnoreCase(".axs") == 0)
			mime = "application/olescript";
		else if (resSuffix.compareToIgnoreCase(".bas") == 0)
			mime = "text/plain";
		else if (resSuffix.compareToIgnoreCase(".bcpio") == 0)
			mime = "application/x-bcpio";
		else if (resSuffix.compareToIgnoreCase(".bin") == 0)
			mime = "application/octet-stream";
		else if (resSuffix.compareToIgnoreCase(".bmp") == 0)
			mime = "image/bmp";
		else if (resSuffix.compareToIgnoreCase(".c") == 0)
			mime = "text/plain";
		else if (resSuffix.compareToIgnoreCase(".cat") == 0)
			mime = "application/vnd.ms-pkiseccat";
		else if (resSuffix.compareToIgnoreCase(".cdf") == 0)
			mime = "application/x-cdf";	
		else if (resSuffix.compareToIgnoreCase(".cer") == 0)
			mime = "application/x-x509-ca-cert";	
		else if (resSuffix.compareToIgnoreCase(".class") == 0)
			mime = "application/octet-streamrt";
		else if (resSuffix.compareToIgnoreCase(".clp") == 0)
			mime = "application/x-msclip";
		else if (resSuffix.compareToIgnoreCase(".cmx") == 0)
			mime = "image/x-cmx";
		else if (resSuffix.compareToIgnoreCase(".cod") == 0)
			mime = "image/cis-cod";
		else if (resSuffix.compareToIgnoreCase(".cpio") == 0)
			mime = "application/x-cpio";
		else if (resSuffix.compareToIgnoreCase(".crd") == 0)
			mime = "application/x-mscardfile";
		else if (resSuffix.compareToIgnoreCase(".crl") == 0)
			mime = "application/pkix-crl";
		else if (resSuffix.compareToIgnoreCase(".crt") == 0)
			mime = "application/x-x509-ca-cert";
		else if (resSuffix.compareToIgnoreCase(".csh") == 0)
			mime = "application/x-csh";
		else if (resSuffix.compareToIgnoreCase(".dcr") == 0)
			mime = "application/x-director";
		else if (resSuffix.compareToIgnoreCase(".der") == 0)
			mime = "application/x-x509-ca-cert";
		else if (resSuffix.compareToIgnoreCase(".dir") == 0)
			mime = "application/x-director";	
		else if (resSuffix.compareToIgnoreCase(".dll") == 0)
			mime = "application/x-msdownload";
		else if (resSuffix.compareToIgnoreCase(".dms") == 0)
			mime = "application/octet-stream";
		else if (resSuffix.compareToIgnoreCase(".doc") == 0 || resSuffix.compareToIgnoreCase(".dot") == 0)
			mime = "application/msword";
		else if (resSuffix.compareToIgnoreCase(".dvi") == 0)
			mime = "application/x-dvi";
		else if (resSuffix.compareToIgnoreCase(".dxr") == 0)
			mime = "application/x-director";
		else if (resSuffix.compareToIgnoreCase(".eps") == 0)
			mime = "application/postscript";
		else if (resSuffix.compareToIgnoreCase(".etx") == 0)
			mime = "text/x-setext";
		else if (resSuffix.compareToIgnoreCase(".evy") == 0)
			mime = "application/envoy";
		else if (resSuffix.compareToIgnoreCase(".exe") == 0)
			mime = "application/octet-stream";
		else if (resSuffix.compareToIgnoreCase(".fif") == 0)
			mime = "application/fractals";
		else if (resSuffix.compareToIgnoreCase(".flr") == 0)
			mime = "x-world/x-vrml";
		else if (resSuffix.compareToIgnoreCase(".gif") == 0)
			mime = "image/gif";
		else if (resSuffix.compareToIgnoreCase(".gtar") == 0)
			mime = "application/x-gtar";	
		else if (resSuffix.compareToIgnoreCase(".gz") == 0)
			mime = "application/x-gzip";	
		else if (resSuffix.compareToIgnoreCase(".h") == 0)
			mime = "text/plain";
		else if (resSuffix.compareToIgnoreCase(".hdf") == 0)
			mime = "application/x-hdf";
		else if (resSuffix.compareToIgnoreCase(".hlp") == 0)
			mime = "application/winhlp";
		else if (resSuffix.compareToIgnoreCase(".hqx") == 0)
			mime = "application/mac-binhex40";
		else if (resSuffix.compareToIgnoreCase(".hta") == 0)
			mime = "application/hta";
		else if (resSuffix.compareToIgnoreCase(".htc") == 0)
			mime = "text/x-component";
		else if (resSuffix.compareToIgnoreCase(".htm") == 0 || resSuffix.compareToIgnoreCase(".html") == 0)
			mime = "text/html";
		else if (resSuffix.compareToIgnoreCase(".htt") == 0)
			mime = "text/webviewhtml";
		else if (resSuffix.compareToIgnoreCase(".ico") == 0)
			mime = "image/x-icon";
		else if (resSuffix.compareToIgnoreCase(".ief") == 0)
			mime = "image/ief";
		else if (resSuffix.compareToIgnoreCase(".iii") == 0)
			mime = "application/x-iphone";
		else if (resSuffix.compareToIgnoreCase(".ins") == 0)
			mime = "application/x-internet-signup";
		else if (resSuffix.compareToIgnoreCase(".isp") == 0)
			mime = "application/x-internet-signup";
		else if (resSuffix.compareToIgnoreCase(".jfif") == 0)
			mime = "image/pipeg";
		else if (resSuffix.compareToIgnoreCase(".jpe") == 0 || resSuffix.compareToIgnoreCase(".jpeg") == 0 || resSuffix.compareToIgnoreCase(".jpg") == 0)
			mime = "image/jpeg";
		else if (resSuffix.compareToIgnoreCase(".js") == 0)
			mime = "application/x-javascript";
		else if (resSuffix.compareToIgnoreCase(".latex") == 0)
			mime = "application/x-latex";
		else if (resSuffix.compareToIgnoreCase(".lha") == 0)
			mime = "application/octet-stream";
		else if (resSuffix.compareToIgnoreCase(".lsf") == 0 || resSuffix.compareToIgnoreCase(".lsx") == 0)
			mime = "video/x-la-asf";
		else if (resSuffix.compareToIgnoreCase(".lzh") == 0)
			mime = "application/octet-stream";
		else if (resSuffix.compareToIgnoreCase(".m13") == 0 || resSuffix.compareToIgnoreCase(".m14") == 0)
			mime = "application/x-msmediaview";
		else if (resSuffix.compareToIgnoreCase(".m3u") == 0)
			mime = "audio/x-mpegurl";
		else if (resSuffix.compareToIgnoreCase(".man") == 0)
			mime = "application/x-troff-man";
		else if (resSuffix.compareToIgnoreCase(".mdb") == 0)
			mime = "application/x-msaccess";
		else if (resSuffix.compareToIgnoreCase(".me") == 0)
			mime = "application/x-troff-me";
		else if (resSuffix.compareToIgnoreCase(".mht") == 0 || resSuffix.compareToIgnoreCase(".mhtml") == 0)
			mime = "message/rfc822";
		else if (resSuffix.compareToIgnoreCase(".mid") == 0)
			mime = "audio/mid";
		else if (resSuffix.compareToIgnoreCase(".mny") == 0)
			mime = "application/x-msmoney";
		else if (resSuffix.compareToIgnoreCase(".mov") == 0)
			mime = "video/quicktime";
		else if (resSuffix.compareToIgnoreCase(".movie") == 0)
			mime = "video/x-sgi-movie";
		else if (resSuffix.compareToIgnoreCase(".mp2") == 0 || resSuffix.compareToIgnoreCase(".mp3") == 0 || resSuffix.compareToIgnoreCase(".mpa") == 0
			|| resSuffix.compareToIgnoreCase(".mpe") == 0 || resSuffix.compareToIgnoreCase(".mpeg") == 0 || resSuffix.compareToIgnoreCase(".mpg") == 0
			 || resSuffix.compareToIgnoreCase(".mpv2") == 0)
			mime = "video/mpeg";
		else if (resSuffix.compareToIgnoreCase(".mpp") == 0)
			mime = "application/vnd.ms-project";
		else if (resSuffix.compareToIgnoreCase(".ms") == 0)
			mime = "application/x-troff-ms";
		else if (resSuffix.compareToIgnoreCase(".mvb") == 0)
			mime = "application/x-msmediaview";
		else if (resSuffix.compareToIgnoreCase(".nws") == 0)
			mime = "message/rfc822";
		else if (resSuffix.compareToIgnoreCase(".oda") == 0)
			mime = "application/oda";
		else if (resSuffix.compareToIgnoreCase(".p10") == 0)
			mime = "application/pkcs10";
		else if (resSuffix.compareToIgnoreCase(".p12") == 0)
			mime = "application/x-pkcs12";
		else if (resSuffix.compareToIgnoreCase(".p7b") == 0)
			mime = "application/x-pkcs7-certificates";
		else if (resSuffix.compareToIgnoreCase(".p7c") == 0)
			mime = "application/x-pkcs7-mime";
		else if (resSuffix.compareToIgnoreCase(".p7m") == 0)
			mime = "application/x-pkcs7-mime";
		else if (resSuffix.compareToIgnoreCase(".p7r") == 0)
			mime = "application/x-pkcs7-certreqresp";
		else if (resSuffix.compareToIgnoreCase(".p7s") == 0)
			mime = "application/x-pkcs7-signature";
		else if (resSuffix.compareToIgnoreCase(".pbm") == 0)
			mime = "image/x-portable-bitmap";
		else if (resSuffix.compareToIgnoreCase(".pdf") == 0)
			mime = "application/pdf";
		else if (resSuffix.compareToIgnoreCase(".pfx") == 0)
			mime = "application/x-pkcs12";
		else if (resSuffix.compareToIgnoreCase(".pgm") == 0)
			mime = "image/x-portable-graymap";
		else if (resSuffix.compareToIgnoreCase(".pko") == 0)
			mime = "application/ynd.ms-pkipko";
		else if (resSuffix.compareToIgnoreCase(".pma") == 0 || resSuffix.compareToIgnoreCase(".pmc") == 0 || resSuffix.compareToIgnoreCase(".pml") == 0
			|| resSuffix.compareToIgnoreCase(".pmr") == 0 || resSuffix.compareToIgnoreCase(".pmw") == 0)
			mime = "application/x-perfmon";
		else if (resSuffix.compareToIgnoreCase(".pnm") == 0)
			mime = "image/x-portable-anymap";
		else if (resSuffix.compareToIgnoreCase(".pot") == 0)
			mime = "application/vnd.ms-powerpoint";
		else if (resSuffix.compareToIgnoreCase(".ppm") == 0)
			mime = "image/x-portable-pixmap";
		else if (resSuffix.compareToIgnoreCase(".ppt") == 0 || resSuffix.compareToIgnoreCase(".pps") == 0)
			mime = "application/vnd.ms-powerpoint";
		else if (resSuffix.compareToIgnoreCase(".prf") == 0)
			mime = "application/pics-rules";
		else if (resSuffix.compareToIgnoreCase(".ps") == 0)
			mime = "application/postscript";
		else if (resSuffix.compareToIgnoreCase(".pub") == 0)
			mime = "application/x-mspublisher";
		else if (resSuffix.compareToIgnoreCase(".qt") == 0)
			mime = "video/quicktime";
		else if (resSuffix.compareToIgnoreCase(".ra") == 0)
			mime = "audio/x-pn-realaudio";
		else if (resSuffix.compareToIgnoreCase(".ram") == 0)
			mime = "audio/x-pn-realaudio";
		else if (resSuffix.compareToIgnoreCase(".ras") == 0)
			mime = "image/x-cmu-raster";
		else if (resSuffix.compareToIgnoreCase(".rgb") == 0)
			mime = "image/x-rgb";
		else if (resSuffix.compareToIgnoreCase(".rmi") == 0)
			mime = "audio/mid";
		else if (resSuffix.compareToIgnoreCase(".roff") == 0)
			mime = "application/x-troff";
		else if (resSuffix.compareToIgnoreCase(".rtf") == 0)
			mime = "application/rtf";
		else if (resSuffix.compareToIgnoreCase(".rtx") == 0)
			mime = "text/richtext";
		else if (resSuffix.compareToIgnoreCase(".scd") == 0)
			mime = "application/x-msschedule";
		else if (resSuffix.compareToIgnoreCase(".sct") == 0)
			mime = "text/scriptlet";
		else if (resSuffix.compareToIgnoreCase(".setpay") == 0)
			mime = "application/set-payment-initiation";
		else if (resSuffix.compareToIgnoreCase(".setreg") == 0)
			mime = "application/set-registration-initiation";
		else if (resSuffix.compareToIgnoreCase(".sh") == 0)
			mime = "application/x-sh";
		else if (resSuffix.compareToIgnoreCase(".shar") == 0)
			mime = "application/x-shar";
		else if (resSuffix.compareToIgnoreCase(".sit") == 0)
			mime = "application/x-stuffit";
		else if (resSuffix.compareToIgnoreCase(".snd") == 0)
			mime = "audio/basic";
		else if (resSuffix.compareToIgnoreCase(".spc") == 0)
			mime = "application/x-pkcs7-certificates";
		else if (resSuffix.compareToIgnoreCase(".spl") == 0)
			mime = "application/futuresplash";
		else if (resSuffix.compareToIgnoreCase(".src") == 0)
			mime = "application/x-wais-source";
		else if (resSuffix.compareToIgnoreCase(".sst") == 0)
			mime = "application/vnd.ms-pkicertstore";
		else if (resSuffix.compareToIgnoreCase(".stl") == 0)
			mime = "application/vnd.ms-pkistl";
		else if (resSuffix.compareToIgnoreCase(".stm") == 0)
			mime = "text/html";
		else if (resSuffix.compareToIgnoreCase(".svg") == 0)
			mime = "image/svg+xml";
		else if (resSuffix.compareToIgnoreCase(".sv4cpio") == 0)
			mime = "application/x-sv4cpio";
		else if (resSuffix.compareToIgnoreCase(".sv4crc") == 0)
			mime = "application/x-sv4crc";
		else if (resSuffix.compareToIgnoreCase(".swf") == 0)
			mime = "application/x-shockwave-flash";
		else if (resSuffix.compareToIgnoreCase(".t") == 0)
			mime = "application/x-troff";	
		else if (resSuffix.compareToIgnoreCase(".tar") == 0)
			mime = "application/x-tar";
		else if (resSuffix.compareToIgnoreCase(".tcl") == 0)
			mime = "application/x-tcl";
		else if (resSuffix.compareToIgnoreCase(".tex") == 0)
			mime = "application/x-tex";
		else if (resSuffix.compareToIgnoreCase(".texi") == 0)
			mime = "application/x-texinfo";
		else if (resSuffix.compareToIgnoreCase(".texinfo") == 0)
			mime = "application/x-texinfo";
		else if (resSuffix.compareToIgnoreCase(".tgz") == 0)
			mime = "application/x-compressed";
		else if (resSuffix.compareToIgnoreCase(".tif") == 0 || resSuffix.compareToIgnoreCase(".tiff") == 0 )
		mime = "image/tiff";
		else if (resSuffix.compareToIgnoreCase(".tr") == 0)
			mime = "application/x-troff";
		else if (resSuffix.compareToIgnoreCase(".trm") == 0)
			mime = "application/x-msterminal";
		else if (resSuffix.compareToIgnoreCase(".tsv") == 0)
			mime = "text/tab-separated-values";
		else if (resSuffix.compareToIgnoreCase(".txt") == 0)
			mime = "text/plain";
		else if (resSuffix.compareToIgnoreCase(".uls") == 0)
			mime = "text/iuls";
		else if (resSuffix.compareToIgnoreCase(".ustar") == 0)
			mime = "application/x-ustar";	
		else if (resSuffix.compareToIgnoreCase(".vcf") == 0)
			mime = "text/x-vcard";	
		else if (resSuffix.compareToIgnoreCase(".vrml") == 0)
			mime = "x-world/x-vrml";	
		else if (resSuffix.compareToIgnoreCase(".wav") == 0)
			mime = "audio/x-wav";
		else if (resSuffix.compareToIgnoreCase(".wmf") == 0)
			mime = "application/x-msmetafile";
		else if (resSuffix.compareToIgnoreCase(".wcm") == 0 || resSuffix.compareToIgnoreCase(".wdb") == 0 || resSuffix.compareToIgnoreCase(".wks") == 0
			|| resSuffix.compareToIgnoreCase(".wps") == 0)
			mime = "application/vnd.ms-works";
		else if (resSuffix.compareToIgnoreCase(".wri") == 0)
			mime = "application/x-mswrite";
		else if (resSuffix.compareToIgnoreCase(".wrl") == 0 || resSuffix.compareToIgnoreCase(".wrz") == 0 || resSuffix.compareToIgnoreCase(".xaf") == 0)
			mime = "x-world/x-vrml";
		else if (resSuffix.compareToIgnoreCase(".xbm") == 0)
			mime = "image/x-xbitmap";
		else if (resSuffix.compareToIgnoreCase(".xla") == 0 || resSuffix.compareToIgnoreCase(".xlc") == 0 || resSuffix.compareToIgnoreCase(".xlm") == 0
			|| resSuffix.compareToIgnoreCase(".xls") == 0 || resSuffix.compareToIgnoreCase(".xlt") == 0 || resSuffix.compareToIgnoreCase(".xlw") == 0)
			mime = "application/vnd.ms-excel";
		else if (resSuffix.compareToIgnoreCase(".xof") == 0)
			mime = "x-world/x-vrml";
		else if (resSuffix.compareToIgnoreCase(".xpm") == 0)
			mime = "image/x-xpixmap";
		else if (resSuffix.compareToIgnoreCase(".xwd") == 0)
			mime = "image/x-xwindowdump";
		else if (resSuffix.compareToIgnoreCase(".z") == 0)
			mime = "application/x-compress";
		else if (resSuffix.compareToIgnoreCase(".zip") == 0)
			mime = "application/zip";
		
		if (mime != null)
			response.setContentType(mime);
	}
	
	private void output(InputStream in, OutputStream out) throws IOException {
		try {
			int len = -1;
			byte[] b = new byte[4096];
			while ((len = in.read(b, 0, b.length)) > 0) {
				out.write(b, 0, len);
				out.flush();
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}
}
