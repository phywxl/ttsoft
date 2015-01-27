package ttsoft.osgi.servlet;

public class TtSoftOsgiSpringMVCContext {
	static ThreadLocal<TtSoftOsgiSpringMVCContext> holderContext = new ThreadLocal<TtSoftOsgiSpringMVCContext>();
	
	private TtSoftOsgiSpringMVCBundleHolder bundleHolder;
	
	public TtSoftOsgiSpringMVCContext(TtSoftOsgiSpringMVCBundleHolder bundleHolder) {
		this.bundleHolder = bundleHolder;
	}
	
	public static TtSoftOsgiSpringMVCContext getContext() {
		return holderContext.get();
	}
	
	public static void setContext(TtSoftOsgiSpringMVCContext context) {
		holderContext.set(context);
	}
	
	public TtSoftOsgiSpringMVCBundleHolder getBundleHolder() {
		return this.bundleHolder;
	}
}
