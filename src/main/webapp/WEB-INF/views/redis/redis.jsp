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
	
})



function addRedis(){
	$.ajax({
		type:"POST",
		url:"exp/addRedis",
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

//打开新增窗口
function openInsert(){
	$("#editForm1").get(0).reset();
	layer.open({
	  type: 1,
	  title:"新增",
	  area: ['400px', '400px'],
	  resize:false,
	  content: $('#editDiv')
	});
}

function addRedisItem(){
	$.ajax({
		type:"POST",
		url:"exp/addRedisItem",
		data: $("#editForm1").serialize(),
		dataType:"text",
		success: function(data){
			if(data=="200"){
				layer.alert("保存成功",{closeBtn:0},function(index){
					layer.closeAll();
					location.reload();
				});
			}else{
				layer.alert("保存失败");
			}
		},
		error:function(XMLHttpRequest,textStatus){
			layer.alert("服务器异常");
		}
	});
}

function deleteRedisItem(id){
	layer.confirm("确定删除此记录吗？<br><br>"+id,function(){
		$.ajax({
			type:"POST",
			url:"exp/delRedisItem",
			data: {id:id},
			dataType:"text",
			success: function(data){
				if(data=="200"){
					layer.alert("删除成功",{closeBtn:0},function(index){
						location.reload();
					});
				}else{
					layer.alert("保存失败");
				}
			},
			error:function(XMLHttpRequest,textStatus){
				layer.alert("服务器异常");
			}
		});
	});
}


</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
  		
  		
  		<h3>测试redis</h3>
  		<a href="javascript:;" class="btn btn-info" onclick="openInsert()">添加缓存Item</a>
  		
  		<br /><br />
  		
  		<table class="stable" style="width:100%;">
		<tr>
		<th>编号</th><th>标题</th><th>备注</th><th>钱</th><th>操作</th>
		</tr>
		<c:forEach var="entity" items="${itemList}">
		<tr>
		<td>${entity.id}</td><td>${entity.title}</td><td>${entity.memo}</td><td>${entity.money}</td>
		<td><a href="javascript:;" onclick="deleteRedisItem('${entity.id}')">删除</a></td>
		</tr>
		</c:forEach>
		</table>
  		
  		
<div id="editDiv" style="display:none">
	<form id="editForm1">
		<div class="form-group col-sm-10">
			<label class="">ID:</label>
			<input name="id" class="form-control" readonly />
		</div>
		<div class="form-group col-sm-10">
			<label class="">标题:</label>
			<input name="title" class="form-control" />
		</div>
		<div class="form-group col-sm-10">
			<label class="">备注:</label>
			<input name="memo" class="form-control" />
		</div>
		<div class="form-group col-sm-10">
			<label class="">金额:</label>
			<input name="money" class="form-control" />
		</div>
		<div class="form-group col-sm-10">
			<a href="javascript:;" class="btn btn-primary" onclick="addRedisItem()">提交</a>
		</div>
	</form>
</div>		    
	  	
  		
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>