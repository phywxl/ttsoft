<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源分页</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ttsoft.osgi.datasource/1.0.0/res/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ttsoft.osgi.datasource/1.0.0/res/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/ttsoft.osgi.datasource/1.0.0/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ttsoft.osgi.datasource/1.0.0/res/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ttsoft.osgi.datasource/1.0.0/res/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
function dsLxOnSelect(record) {
	if (record.value && record.value != "") {
		if (record.value === 'oracle')	{
			$('#dsDc').val('oracle.jdbc.driver.OracleDriver');
		} else if (record.value === 'mysql')	{
			$('#dsDc').val('com.mysql.jdbc.Driver');
		} else if (record.value === 'sqlserver')	{
			$('#dsDc').val('com.microsoft.jdbc.sqlserver.SQLServerDriver');
		} else {
			$('#dsDc').val('oracle.jdbc.driver.OracleDriver');
		}
	}
}
</script>
</head>
<body>
<form id="dataSourceVoForm" action="./insert.action" method="post">
	<br>名称：<input id="dsMc" name="dsvo.dsMc" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,64]}" type="text">
	<br>编码：<input id="dsBm" name="dsvo.dsBm" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,64]}" type="text"/>
	<br>类型：    <select id="dsLx" name="dsvo.dsLx" class="easyui-combobox" data-options="onSelect:dsLxOnSelect" style="width:200px;">
		    <option value="">请选择</option>
		    <option value="oracle">oracle</option>
		    <option value="mysql">mysql</option>
		    <option value="sqlserver">sqlserver</option>
    	</select>
    <br>驱动类：<input id="dsDc" name="dsvo.dsDc" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,64]}" type="text">
	<br>状态：<select id="qybz" name="dsvo.qybz" class="easyui-combobox" style="width:130px">
		    <option value="N">禁用</option>
		    <option value="Y">启用</option>
    	</select>
    <br>IP：<input id="dsIp" name="dsvo.dsIp" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,32]}" type="text">
    <br>端口：<input id="dsPort" name="dsvo.dsPort" class="easyui-numberbox" value="1521" data-options="min:1,max:65536" type="text">
    <br>数据库名称：<input id="dsNm" name="dsvo.dsNm" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,64]}" type="text">
    <br>连接Url：<input id="dsUrl" name="dsvo.dsUrl" value="" class="easyui-validatebox" data-options="validType:{length:[0,512]}" type="text">
    <br>用户：<input id="dsUser" name="dsvo.dsUser" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,16]}" type="text">
	<br>密码：<input id="dsPass" name="dsvo.dsPass" value="" class="easyui-validatebox" data-options="required:true,validType:{length:[0,16]}" type="password">
	<br>连接池最小数：<input id="dsMin" name="dsvo.dsMin" class="easyui-numberbox" value="1" data-options="min:1" type="text">
	<br>连接池最大数：<input id="dsMax" name="dsvo.dsMax" class="easyui-numberbox" value="15" data-options="min:1" type="text">
	<br>连接池初始数：<input id="dsInit" name="dsvo.dsInit" class="easyui-numberbox" value="2" data-options="min:1" type="text">
	<br>连接池增长数：<input id="dsInc" name="dsvo.dsInc" class="easyui-numberbox" value="1" data-options="min:1" type="text">
	<br>序号：<input id="xh" name="dsvo.xh" class="easyui-numberbox" value="1.0" data-options="min:0,max:99999,precision:3" type="text">
	<br>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="javascript:$('#dataSourceVoForm').submit();">提交</a>
</form>
</body>
</html>