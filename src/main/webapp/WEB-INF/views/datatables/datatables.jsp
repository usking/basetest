<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>DataTables</title>
<script src="static/js/common/dts.js"></script>
<script type="text/javascript">
$(function(){
	query();
})
function query(){
	dd=$("#datalist").dts({
		url:"exp/datatables/list",
		columns: [//列定义
              { title: "序号",sortable:false, data:"id",width:"5%"},
              { title: "ID", data:"id", name:"id",width:"5%",visible:false},
              { title: "标题",data:"title", name:"title",className:"middle",width:"8%"},
              { title: "备注",data:"memo", name:"memo",width:"8%"},
              { title: "金额",data:"money", name:"money",width:"8%"},
              { title: "操作", data:"id", name:"id", sortable: false, width:"8%",
                  render: function(data, type, row, meta) {
                  	var str="";
                  	str += "<a href=\"javascript:;\" onclick=\"view('"+data+"')\">查看</a>";
                  	str += "&nbsp;<a href=\"javascript:;\" onclick=\"openEdit('"+data+"')\">修改</a>";
                  	str += "&nbsp;<a href=\"javascript:;\" onclick=\"del('"+data+"')\">删除</a>";
                    return str;
                  }
              }
          ],
          order:[[ 4, 'asc' ]]
	});
}

function search(){
	var param=$("#queryForm").serializeJson();
	dd.settings()[0].ajax.data = param;
   	dd.ajax.url("${ctx}/exp/datatables/list").load();
}

//查看详情
function view(id){
	$.post("exp/item/info",{id:id},function(data){
		$("#detailId").text(data.id);
		$("#detailTitle").text(data.title);
		$("#detailMemo").text(data.memo);
		$("#detailMoney").text(data.money);
		layer.open({
		  type: 1,
		  title:"详情",
		  area: ['400px', '350px'],
		  resize:false,
		  content: $('#detail')
		});
	},"json");
}

//打开修改窗口
function openEdit(id){
	$.ajax({
		type:"POST",
		url:"exp/item/info",
		data: {id:id},
		dataType:"json",
		success: function(data){
			$("#editForm1").get(0).reset();
			$("#editForm1 input[name='id']").val(data.id);
			$("#editForm1 input[name='title']").val(data.title);
			$("#editForm1 input[name='memo']").val(data.memo);
			$("#editForm1 input[name='money']").val(data.money);
			layer.open({
			  type: 1,
			  title:"修改",
			  area: ['400px', '400px'],
			  resize:false,
			  content: $('#editDiv')
			});
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



//新增或修改
function merge(){
	$.ajax({
		type:"POST",
		url:"exp/item/merge",
		data: $("#editForm1").serialize(),
		dataType:"text",
		success: function(data){
			if(data=="200"){
				layer.alert("保存成功",{closeBtn:0},function(index){
					search();
					layer.closeAll();
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

function del(id){
	layer.confirm("确定删除此记录吗？<br><br>"+id,function(){
		$.post("exp/item/del",{id:id},function(data){
			if(data=="200"){
				layer.alert("删除成功",{closeBtn:0},function(index){
					search();
					layer.close(index);
				});
			}else{
				layer.alert("删除失败");
			}
		},"text");
	});
}
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/layout-top.jsp"></jsp:include>

	<h3>DataTables测试</h3>
	<form id="queryForm" class="form-inline">
		标题关键字：<input type="text" name="title" class="form-control" style="width:200px;" />&nbsp;
		<input type="button" value="查询" class="btn btn-primary" onclick="search()" />
	</form><br />
	<a href="javascript:;" class="btn btn-primary" onclick="openInsert()">新增</a>
	<table id="datalist" class="stable"></table>
	
	
<jsp:include page="/WEB-INF/views/layout-bottom.jsp"></jsp:include>
</body>

<div id="detail" style="display:none">
	<div class="">
		<div class="form-group col-sm-10">
			<label class="">ID:</label>
			<div><p class="form-control-static" id="detailId"></p></div>
		</div>
		<div class="form-group col-sm-10">
			<label class="">标题:</label>
			<div><p class="form-control-static" id="detailTitle"></p></div>
		</div>
		<div class="form-group col-sm-10">
			<label class="">备注:</label>
			<div><p class="form-control-static" id="detailMemo"></p></div>
		</div>
		<div class="form-group col-sm-10">
			<label class="">金额:</label>
			<div><p class="form-control-static" id="detailMoney"></p></div>
		</div>
	</div>
</div>

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
			<a href="javascript:;" class="btn btn-primary" onclick="merge()">提交</a>
		</div>
		<input type="hidden" name="csrftoken" value="${sessionScope.csrftoken}" />
	</form>
</div>

</html>