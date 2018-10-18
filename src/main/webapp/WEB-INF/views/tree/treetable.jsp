<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>treetable</title>

<link rel="stylesheet" href="static/js/plugins/treeTable/themes/vsStyle/treeTable.min.css">
<script src="static/js/plugins/treeTable/jquery.treeTable.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#treegrid").treeTable({
		expandLevel:1
	});
})

</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
	
	<h3>treetable测试</h3>
	<table id="treegrid" style="width:500px;" class="stable">
		<tr><th>ID</th><th>备注</th></tr>
		<c:forEach var="entity" items="${treegrid}">
			<tr id="${entity.id}" pId="${entity.pid}">
				<td>${entity.id}</td><td>${entity.memo}</td>
			</tr>
		</c:forEach>
	</table>

<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>

</body>

</html>