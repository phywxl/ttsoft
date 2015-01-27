package ttsoft.osgi.servlet;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class TtSoftOsgiSpringMVCFreeMarkerView extends FreeMarkerView {
	/**
	 * Check that the FreeMarker template used for this view exists and is valid.
	 * <p>Can be overridden to customize the behavior, for example in case of
	 * multiple templates to be rendered into a single view.
	 */
	@Override
	public boolean checkResource(Locale locale) throws Exception {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		super.setApplicationContext(TtSoftOsgiSpringMVCContext.getContext().getBundleHolder().getWebApplicationContext());
	}
	
	
}
