<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>jquery-file-upload文件上传</title>
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
        url: "exp/file/upload",
        dataType: 'json',
        done: function (e, data) {
        	if(data.result.code=="200"){
        		var fileName=data.result.fileName;
        		var fileUrl=data.result.fileUrl;
                var html="<a href='exp/file/download?fileName="+fileName+"' target='_blank'><img class='img-thumbnail' style='width:100px;height:100px;' src='"+fileUrl+"' /></a>";
                $("#imgs").append(html);
        	}else{
        		layer.alert(data.result.message);
        	}
        	
        },
        progressall: function (e, data) {
            /* var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            ); */
        	var progress = parseInt(data.loaded / data.total * 100, 10);
            $("#progress").text(progress);
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
  		
  		
  		<h3>jquery-file-upload文件上传</h3>
  		<div id="imgs">
  		
  		</div><br />
  		
  		<a href="javascript:;" class="btn btn-info" onclick="$('#file1').click()">
  		<i class="fa fa-upload fa-lg"></i>&nbsp;上传文件</a>&nbsp;<span id="progress"></span>
  		
  		<input type="file" id="file1" name="file" multiple="multiple" class="btn" style="display:none" />
		    
	  	
  		
  		
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>
</html>