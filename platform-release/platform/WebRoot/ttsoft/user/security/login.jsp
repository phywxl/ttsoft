<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/j_spring_security_check">
	账户：<input id="j_username" name="j_username" type="text" value="admin">
	<br>
	密码：<input id="j_password" name="j_password" type="password" value="123456">
	<br>
	<input id="submit" name="submit" type="submit" value="提交">
</form>
</body>
</html>