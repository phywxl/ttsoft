package ttsoft.osgi.test.resteasy;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

@Controller
@Path("/hello")
public class HelloController {

	@GET
	@Produces("text/plain")
	public String getStatus() {
		return "Hello World!";
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("data")
	public Contacts getAll() {
		System.out.println("====HelloController.getAll()...");
		Contacts c = new Contacts();
		c.setName("ccccccccc");
		
		List<Contact> l = new ArrayList<Contact>();
		c.setContacts(l);
		
		Contact t = new Contact();
		l.add(t);
		t.setId(1L); t.setName("name");t.setTelephone("181566");t.setEmail("email");
		
		t = new Contact();
		l.add(t);
		t.setId(2L); t.setName("name2");t.setTelephone("1815662");t.setEmail("email2");
		
		return c;
	}
	
	public static void main(String[] args) {
		javax.ws.rs.client.Client client = javax.ws.rs.client.ClientBuilder.newBuilder().build();
		javax.ws.rs.client.WebTarget target = client.target("http://localhost:8080/ttwebplatform/sservlet/ttsoft.osgi.test.resteasy");
		javax.ws.rs.core.Response response = target.path("hello").path("data").request(MediaType.APPLICATION_JSON).get();
		String value = response.readEntity(String.class);
		System.out.println(value);
		response.close();
	}
}
