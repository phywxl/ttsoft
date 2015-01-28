<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
<!--
var result = <s:property value="jsonValue" escape="false"/> || null;
if (result) {
	if (result.ok) {
		alert("提交成功！");
		history.back();
	} else {
		alert(result.msg);
		history.back();
	}
} else {
	history.back();
}
//-->
</script>