package ttsoft.osgi.test.jersey;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class JerseyApplication extends Application {
	
	/*@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> result = new HashSet<Class<?>>();
		result.add(StatusResource.class);
		return result;
	}*/
	
	@Override
	public Set<Object> getSingletons() {
		Set<Object> result = new HashSet<Object>();
		result.add(new StatusResource());
		return result;
	}
}
