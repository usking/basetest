<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>pagination分页</title>
<script type="text/javascript">
$(function(){
	query();
})
function query(){
	$.jqPage({
		currentPage:"1",//默认显示第一页
		url:"exp/pagination/list",//查询地址
		grid:"div1",//数据存储的容器id
		form:"queryForm",//查询form的id
		pagination:"pagination1",//分页标签的id
		pageSize:10,//每页显示记录数
		totalCounter:"totalCounter1",//总记录数
		totalPager:"totalPager1",//总页数
		currentPager:"currentPager1",//当前页号
		pageCallback:function(){
			//alert("执行回调")
		}
	});
}
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
  		
  		
  		<h3>pagination测试分页</h3>
	  	<form id="queryForm" method="post" onsubmit="return false">
	  	标题关键字：<input type="text" name="title" />&nbsp;
	  	<input type="button" value="查询" onclick="query()" />
	  	</form><br />
	  	
	  	
	   	<div id="div1"></div>
	   	<ul class="pagination" id="pagination1"></ul>
	   	<div>
	   		共<span id="totalCounter1"></span>条记录，
	   		第<span id="currentPager1"></span>/<span id="totalPager1"></span>页
	   	</div>
  		
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>