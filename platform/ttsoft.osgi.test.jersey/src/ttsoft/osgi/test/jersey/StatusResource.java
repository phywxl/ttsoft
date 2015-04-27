package ttsoft.osgi.test.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("status")
public class StatusResource {
	public StatusResource() {
		System.out.println("==StatusResource constrator.");
	}
	
	@GET
	@Produces("text/plain")
	public String getStatus() {
		return "active";
	}
}
