package ttsoft.osgi.web.main.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MainAction extends ActionSupport {
	/**
	 * 主页
	 * @return
	 */
	public String main() {
		return Action.SUCCESS;
	}
}
