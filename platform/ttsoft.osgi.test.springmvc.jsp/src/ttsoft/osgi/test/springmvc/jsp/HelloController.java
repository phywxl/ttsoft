package ttsoft.osgi.test.springmvc.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("--------------hello osgi Spring MVC jsp! 世界你好！");
        return new ModelAndView("/hello");
	}

}
