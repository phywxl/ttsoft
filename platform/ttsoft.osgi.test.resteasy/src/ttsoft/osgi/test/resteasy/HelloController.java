package ttsoft.osgi.test.resteasy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;

@Controller
@Path("/hello")
public class HelloController {

	@GET
	@Produces("text/plain")
	public String getStatus() {
		return "Hello World!";
	}

}
