<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>各种测试</title>
<link rel="stylesheet" type="text/css" href="static/js/plugins/jquery-file-upload-9.14.2/css/jquery.fileupload.css">
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.fileupload.js"></script>

<script type="text/javascript">
$(function(){
	
})



function testAjax(){
	$.ajax({
		type:"POST",
		url:"test/testAjax",
		//data: {},
		dataType:"json",
		success: function(data){
			layer.alert(data.code+":"+data.message);
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常:"+XMLHttpRequest.responseText);
		}
	});
}

function testRocketmq(){
	$.ajax({
		type:"POST",
		url:"test/testRocketmq",
		//data: {},
		dataType:"json",
		success: function(data){
			layer.alert(data.code+" "+data.message);
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常:"+XMLHttpRequest.responseText);
		}
	});
}


</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
  		
  		
  		<h3>各种测试</h3>
  		<a href="javascript:;" class="btn btn-info" onclick="testAjax()">测试ajax</a>
  		
  		<br /><br />
  		
  		<a href="javascript:;" class="btn btn-info" onclick="testRocketmq()">测试rocketmq</a>
  		<br /><br />
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>