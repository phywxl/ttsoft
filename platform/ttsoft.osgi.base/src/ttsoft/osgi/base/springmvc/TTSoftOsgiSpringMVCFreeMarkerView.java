package ttsoft.osgi.base.springmvc;

import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class TTSoftOsgiSpringMVCFreeMarkerView extends FreeMarkerView implements BundleContextAware {
	private BundleContext bundleContext;

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

}
