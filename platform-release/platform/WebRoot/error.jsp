<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="0;url=./ttsoft.osgi.web.main_1.0/main.action">
<script type="text/javascript">
</script>
</head>
<body>
	<%
	Object obj = null;
	if (request.getAttributeNames() != null) {
		for (Enumeration enu = request.getAttributeNames(); enu.hasMoreElements();) {
			out.println((obj = enu.nextElement()));
			if (obj != null) {
				out.println("=");
				out.println(request.getAttribute((String)obj));
			}
			out.println("<br>");
		}
	}
	if (request.getSession() != null) {
		for (Enumeration enu = request.getSession().getAttributeNames(); enu.hasMoreElements();) {
			out.println((obj = enu.nextElement()));
			if (obj != null) {
				out.println("=");
				out.println(request.getAttribute(obj.toString()));
			}
			out.println("<br>");
		}
		
		if (request.getSession().getServletContext().getAttributeNames() != null) {
			for (Enumeration enu = request.getSession().getServletContext().getAttributeNames(); enu.hasMoreElements();) {
				out.println((obj = enu.nextElement()));
				if (obj != null) {
					out.println("=");
					out.println(request.getAttribute(obj.toString()));
				}
				out.println("<br>");
			}
		}
	}
	
	%>
</body>
</html>
