<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.left{
	width: 200px;
	height:100%;
	float: left;
	position:absolute;
}
.right{
	float: left;
	margin-left: 210px;
	margin-top: 20px;
	width:83%;
}
</style>
<script type="text/javascript">
$(function(){
	var ztreeSettings={
		view: {
			showLine: false,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		}
	}
	var zNodes = [
    	{id:1, pId:0, name: "常用",open:true},
    	{id:11, pId:1, name: "pagination分页",url:"exp/pagination/index",target:"_self"},
    	{id:12, pId:1, name: "zPage分页",url:"exp/zpage/index",target:"_self"},
    	{id:13, pId:1, name: "DataTables",url:"exp/datatables/index",target:"_self"},
    	{id:14, pId:1, name: "treegrid",url:"exp/treegrid/index",target:"_self"},
    	{id:15, pId:1, name: "treeTable",url:"exp/treetable/index",target:"_self"},
    	{id:15, pId:1, name: "fileupload",url:"exp/fileupload/index",target:"_self"},
    	{id:15, pId:1, name: "quartz-job",url:"exp/quartzIndex",target:"_self"},
    	{id:15, pId:1, name: "redis",url:"exp/redisIndex",target:"_self"},
    	{id:100, pId:1, name: "百度",url:"http://www.baidu.com/",target:"_blank"},
    	{id:2, pId:0, name: "测试"},
    	{id:21, pId:2, name: "测试1",url:"",target:"_self"},
    	{id:22, pId:2, name: "测试2",url:"",target:"_self"}
    ];
	zTreeObj = $.fn.zTree.init($("#menu"), ztreeSettings, zNodes);
});
</script>
<div class="left">
   <ul id="menu" class="ztree"></ul>
</div>