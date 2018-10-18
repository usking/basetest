<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>jquery-treegrid</title>

<link rel="stylesheet" href="static/js/plugins/jquery-treegrid/css/jquery.treegrid.css">
<script src="static/js/plugins/jquery-treegrid/js/jquery.treegrid.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#treegrid").treegrid({
		initialState:"collapsed"
	});
})

</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>

	<h3>jquery-treegrid测试</h3>
	<table id="treegrid" style="width:500px;" class="stable">
		<tr><th>ID</th><th>备注</th></tr>
		<c:forEach var="entity" items="${treegrid}">
			<tr class="treegrid-${entity.id} treegrid-parent-${entity.pid}">
				<td>${entity.id}</td><td>${entity.memo}</td>
			</tr>
		</c:forEach>
	</table>

<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>

</body>

</html>