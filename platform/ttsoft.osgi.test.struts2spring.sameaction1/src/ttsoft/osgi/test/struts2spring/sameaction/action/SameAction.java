package ttsoft.osgi.test.struts2spring.sameaction.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SameAction extends ActionSupport {
	public String same() {
		this.setMsg("I'm ttsoft.osgi.test.struts2spring.sameaction1_1.0 plugin action.");
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
