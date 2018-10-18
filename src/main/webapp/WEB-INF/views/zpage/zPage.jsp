<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<script type="text/javascript" src="static/js/common/zpage.js"></script>
<title>pagination分页</title>
<script type="text/javascript">
$(function(){
	query();
})
function query(){
	$("#div1").zPage({
		url:"exp/zpage/list",
		form:"queryForm",
		totalCounter:"totalCounter",//记录总数容器id
		totalPager:"totalPager",//总页号容器id
		currentPager:"currentPager",//当前页号容器id
		firstQuery:true
	});
}
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>

	<h3>zpage测试分页</h3>
  	<form id="queryForm" method="post" onsubmit="return false">
  	标题关键字：<input type="text" name="title" />&nbsp;
  	<input type="button" value="查询" onclick="query()" />
  	</form><br />
  	
  	
   	<div id="div1"></div>

<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>

</body>
</html>