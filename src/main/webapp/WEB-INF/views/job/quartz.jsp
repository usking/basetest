<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>测试定时任务quartz</title>
<link rel="stylesheet" type="text/css" href="static/js/plugins/jquery-file-upload-9.14.2/css/jquery.fileupload.css">
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.fileupload.js"></script>

<script type="text/javascript" src="static/js/common/websocket.js"></script>
<script type="text/javascript">
$(function(){
	$.initWebsocket({
		userid:"${sessionScope.sessionInfo.userid}",
		onopen:function(event){
			$("#content").append("已连接<br />");
		},
		onmessage:function(event){
			var json=$.parseJSON(event.data);
			$("#content").append(json.message+"<br />");
			scrollDiv();
		}
	});
})

function scrollDiv(){
	var div = document.getElementById('content');
	div.scrollTop = div.scrollHeight;
}

function addJob(){
	$.ajax({
		type:"POST",
		url:"exp/addJob",
		//data: {},
		dataType:"text",
		success: function(data){
			layer.alert(data);
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常");
		}
	});
}

function modifyJob(){
	$.ajax({
		type:"POST",
		url:"exp/modifyJob",
		//data: {},
		dataType:"text",
		success: function(data){
			layer.alert(data);
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常");
		}
	});
}

function removeJob(){
	$.ajax({
		type:"POST",
		url:"exp/removeJob",
		//data: {},
		dataType:"text",
		success: function(data){
			layer.alert(data);
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常");
		}
	});
}



</script>

<style type="text/css">
.d{
	float: left;
	overflow-y: auto;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
  		
  		
  		<h3>测试定时任务quartz</h3>
  		<a href="javascript:;" class="btn btn-info" onclick="addJob()">开始定时任务</a>
  		<a href="javascript:;" class="btn btn-info" onclick="modifyJob()">执行修改时间</a>
  		<a href="javascript:;" class="btn btn-info" onclick="removeJob()">删除定时任务</a>
  		
  		<br />
  		
  		<div class="d" id="content" style="border:1px solid red;width: 300px;height: 400px;">
	    	
	    </div>
  		
		    
	  	
  		
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>