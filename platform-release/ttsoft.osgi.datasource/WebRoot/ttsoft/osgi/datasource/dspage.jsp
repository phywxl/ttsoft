<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源分页</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/iwell.disposition.res_1.0/res/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/iwell.disposition.res_1.0/res/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/iwell.disposition.res_1.0/res/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/iwell.disposition.res_1.0/res/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/iwell.disposition.res_1.0/res/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$('#dsPageTable').datagrid({
		url: './dspagedata.action',
		toolbar: '#dsPageTableToolbar',
		fit: true,
		pagination: true,
		pageNumber: 1,
		pageSize: 10,
		rownumbers: true,
		columns:[[
			{field:'dsId',title:'&nbsp;',checkbox:true,width:100,align:'center'},
			{field:'dsBm',title:'编码',width:180,align:'center'},
			{field:'dsMc',title:'名称',width:80,align:'center'},
			{field:'dsUser',title:'用户',width:80,align:'center'},
			{field:'dsIp',title:'IP',width:120,align:'center'},
			{field:'dsPort',title:'端口',width:60,align:'center'},
			{field:'dsNm',title:'数据库名称',width:80,align:'center'},
			{field:'dsUrl',title:'URL',width:320,align:'center'},
			{field:'qybz',title:'状态',width:40,align:'center',formatter: function(value,row,index){
				if (value){
					if (value == "Y") {
						return "启用";
					} else if (value == "y") {
						return "启用";
					} else if (value == "1") {
						return "启用";
					} else if (value == "N") {
						return "禁用";
					} else if (value == "n") {
						return "禁用";
					} else if (value == "0") {
						return "禁用";
					}  else {
						return "未知";
					}
				} else {
					return "未知";
				}
			}}
		]]
	});
	searchDses();
});
</script>
<script type="text/javascript">
//搜索数据源
function searchDses() {
	$('#dsPageTable').datagrid('reload', {
		'dsvo.dsBm': $('#dsBm').val(),
		'dsvo.dsMc': $('#dsMc').val(),
		'dsvo.qybz': $('#qybz').combobox('getValue')
	});
}
</script>
<script type="text/javascript">
//启用数据源
function enabledDses() {
	var path = './status.action';
	ajaxDses(path, "&dsvo.qybz=Y");
}
//禁用数据源
function disabledDses() {
	$.messager.confirm('确认', '确定禁用数据源吗，可能影响系统数据处理操作?', function(r){
		if (r){
			var path = './status.action';
			ajaxDses(path, "&dsvo.qybz=N");
			return true;
		}
		return false;
	});
}
//删除数据源
function deleteDses() {
	$.messager.confirm('确认', '确定删除吗?', function(r){
		if (r){
			var path = './delete.action';
			ajaxDses(path);
			return true;
		}
		return false;
	});
}
function ajaxDses(path, args) {
	var rows = $('#dsPageTable').datagrid("getSelections");
	if (rows) {
		var ids = "";
		for(var i = 0; i < rows.length; i++){
			if (i == 0) {
				ids += "dsIds=" + rows[i].dsId;
			} else {
				ids += "&dsIds=" + rows[i].dsId;
			}
		}
		if (args) {
			ids += args;	
		}
		
		$.ajax({
			type: 'post',
	    	async: false,
			url: path,
			data: ids,
			error: function(request, status, thrown){
				alert("操作失败！\n" + thrown);
			},
			success: function(data, status){
				if (data.ok) {
					searchDses();
				} else {
					//alert(data.msg);
				}
			}
		});
	}
}
</script>
<script type="text/javascript">
function addDSPage() {
	location.href = "./dsaddpage.action";
}
function modDsPage() {
	var rows = $('#dsPageTable').datagrid("getSelections");
	if (rows && rows.length > 1) {
		$.messager.alert('提示','请选择一条数据!','info');
		return;
	}
	location.href = "./dsmodpage.action?dsId=" + rows[0].dsId;
}
</script>
</head>
<body>
<table id="dsPageTable"></table>
<div id="dsPageTableToolbar" style="padding:5px;margin-bottom: 5px;height:auto">
	<div class="datagrid-btn-separator"></div>
	<div class="datagrid-btn-separator"></div>
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok"     plain="true" onclick="javascript:enabledDses()">启用</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:disabledDses()">禁用</a>
	&nbsp;&nbsp;
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"    plain="true" onclick="javascript:addDSPage()">增加</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="javascript:modDsPage()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-no"     plain="true" onclick="javascript:deleteDses()">删除</a>
	&nbsp;&nbsp;&nbsp;&nbsp;
	编码：<input id="dsBm" name="dsBm" style="width:130px;" class="datagrid-editable-input">
	名称：<input id="dsMc" name="dsMc" style="width:130px;" class="datagrid-editable-input">
         状态：<select id="qybz" name="qybz" class="easyui-combobox" style="width:130px">
	    <option value="">请选择</option>
	    <option value="Y">启用</option>
	    <option value="N">禁用</option>
    </select>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:searchDses()">查询</a>
</div>
</body>
</html>