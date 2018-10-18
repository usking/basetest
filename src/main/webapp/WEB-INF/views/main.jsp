<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>首页</title>
</head>
<body>

<div class="container-fluid">
  <div class="row">
  	<div class="col-md-2">
    	<jsp:include page="/WEB-INF/views/menu.jsp"></jsp:include>
    </div>
  	<div class="col-md-10">
  		【${sessionInfo.username}】登录成功&nbsp;<a href="logout">注销</a>
  	</div>
  </div>
</div>

</body>
</html>