<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tag.jsp"%>

<!--[if lte IE 8]><body class="lower-ie"><script>window.location.href="<%=basePath%>error/browser"</script></body><![endif]-->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="static/css/style.css">
<script type="text/javascript" src="static/js/common/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="static/js/common/form2json.js"></script>

<!-- bootstrap -->
<script type="text/javascript" src="static/js/plugins/bootstrap-3.3.7/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="static/js/plugins/bootstrap-3.3.7/css/bootstrap.css">

<!-- Font-Awesome -->
<link rel="stylesheet" type="text/css" href="static/js/plugins/font-awesome/css/font-awesome.min.css">

<!-- pagination -->
<link rel="stylesheet" type="text/css" href="static/js/plugins/pagination/pagination.css">
<script type="text/javascript" src="static/js/plugins/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="static/js/plugins/pagination/jqPage.js"></script>

<!-- layer -->
<link rel="stylesheet" type="text/css" href="static/js/plugins/layer/skin/default/layer.css">
<script type="text/javascript" src="static/js/plugins/layer/layer.js"></script>

<!-- ztree -->
<link rel="stylesheet" type="text/css" href="static/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="static/js/plugins/ztree/js/jquery.ztree.core.js"></script>
<!-- DataTables -->
<link rel="stylesheet" href="static/js/plugins/DataTables/media/css/jquery.dataTables.min.css">
<script src="static/js/plugins/DataTables/media/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
var objGlobal = {
	ctx:"<%=basePath%>"
};

$.ajaxSetup({
	beforeSend:function(){
		layerLoad=layer.load(2);
	},
    complete:function(){
    	layer.close(layerLoad);
    }
});
</script>