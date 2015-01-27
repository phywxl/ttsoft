<%@page import="java.io.PrintStream"%>
<%@page import="java.util.Enumeration"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head><title>Simple jsp page</title></head>
<body>
    <h3>Exception:</h3>
    <s:property value="exception"/>

    <h3>Stack trace:</h3>
    <pre>
        <s:property value="exceptionStack"/>
    </pre>
    
    <div>
    <%
    Enumeration e = (session != null ? session.getAttributeNames() : null);
    if (e != null) {
    	Object key = null;
    	Object value = null;
    	out.println("<h3>Session Attribute:</h3>");
    	for (; e.hasMoreElements(); ) {
    		key = e.nextElement();
    		value =  session.getAttribute((String)key);
    		out.println("<pre>");
    		out.println("key=" + key);
    		//out.println("<br>");
    		if (value != null && value instanceof Throwable) {
    			out.println("value=");
    			((Throwable)value).printStackTrace(new java.io.PrintWriter(out));
    		} else {
    			out.println("value=" + value);
    		}
    		out.println("</pre>");
    	}
    }
    %>
    </div>
</body>
</html>