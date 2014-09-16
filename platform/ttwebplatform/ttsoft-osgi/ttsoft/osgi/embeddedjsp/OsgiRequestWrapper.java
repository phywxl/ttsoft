package ttsoft.osgi.embeddedjsp;

import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class OsgiRequestWrapper extends StrutsRequestWrapper {
	private Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	public OsgiRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String[] values = parameterMap.get(name);
		if(values!=null && values.length>0){
			return values[0];
		}
		return super.getParameter(name);
	}
	
	public void setParameter(String name, String value) {
		String[] values = parameterMap.get(name);
		if(values!=null && values.length>0){
			String[] tmp=new String[values.length+1];
			for(int i=0;i<values.length;i++){
				tmp[i]=values[i];
			}
			tmp[tmp.length-1]=value;
			this.parameterMap.put(name, tmp);
		} else{
			this.parameterMap.put(name, new String[]{value});			
		}
	}

	@Override
	public Map getParameterMap() {
		Map map = new HashMap();
		map.putAll(super.getParameterMap());
		map.putAll(parameterMap);
		return map;
	}

	@Override
	public Enumeration getParameterNames() {
		Map map = new HashMap();
		map.putAll(super.getParameterMap());
		map.putAll(parameterMap);
		Vector vector = new Vector();
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			vector.add(it.next());
		}
		return vector.elements();
//		return super.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values1 = super.getParameterValues(name);
		String[] values2 = parameterMap.get(name);
		Set set=new HashSet();
		if(values1!=null&&values1.length>0){
			for(int i=0;i<values1.length;i++){
				set.add(values1[i]);
			}
		}
		if(values2!=null&&values2.length>0){
			for(int i=0;i<values2.length;i++){
				set.add(values2[i]);
			}
		}
		if(set.size()==0){
			return null;
		}
		String[] values=new String[set.size()];
		Iterator it=set.iterator();
		int i=0;
		while(it.hasNext()){
			values[i]=String.valueOf(it.next());
			i++;
		}
		return values;
//		return super.getParameterValues(name);
	}
}
