<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>excel导入导出</title>
<link rel="stylesheet" type="text/css" href="static/js/plugins/jquery-file-upload-9.14.2/css/jquery.fileupload.css">
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="static/js/plugins/jquery-file-upload-9.14.2/js/jquery.fileupload.js"></script>
<script type="text/javascript">
$(function(){
	upload();
})

function upload(){
	$('#file1').bind('fileuploadadd', function (e, data){
		layerLoad=layer.load(2);
	})
	
	$('#file1').fileupload({
        url: "exp/readExcel",
        dataType: 'json',
        done: function (e, data) {
        	if(data.result.code=="200"){
        		var str=data.result.data;
                $("#content").html(str);
        	}else{
        		layer.alert(data.result.message);
        	}
        },
        always:function(){
        	layer.close(layerLoad);
        }
    })
}



</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>
  		
  		<h3>excel导入导出</h3>
  		<br />
  		
  		<a href="javascript:;" class="btn btn-info" onclick="$('#file1').click()">
  		<i class="fa fa-upload fa-lg"></i>&nbsp;导入excel</a>
  		&nbsp;
  		<a href="exp/writeExcel" class="btn btn-info">
  		<i class="fa fa-download fa-lg"></i>&nbsp;导出excel</a>
  		
  		<input type="file" id="file1" name="file" class="btn" style="display:none" />
		   
		<br /><br /> 
	  	<div id="content">
  		
  		</div>
  		
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>