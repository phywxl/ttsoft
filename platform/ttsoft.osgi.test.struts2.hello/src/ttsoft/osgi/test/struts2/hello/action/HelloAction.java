package ttsoft.osgi.test.struts2.hello.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
	
	public String hello() {
		this.setMsg("test!");
		return Action.SUCCESS;
	}
	
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
