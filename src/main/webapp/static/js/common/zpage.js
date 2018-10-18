/**
 * 分页js
 * @author sjz
 * 
 */

(function($) {
	$.fn.zPage = function(opts) {
		var defaults = {
			url:"",//查询地址
			form:"",//查询form的id
			pageSize:10,//每页显示记录数
			pageNo:1,//默认当前第几页
			firstQuery:true,//是否每次都查询第一页(或给定的默认页号)，是的话将清空记录的页号
			totalCounter:"",//记录总数容器id
			totalPager:"",//总页号容器id
			currentPager:"",//当前页号容器id
			pageCallback:function(){}//回调函数
		};
		var options = $.extend(defaults, opts);
		
		var $grid = $(this);
		var queryString="";
		
		if(options.firstQuery){
			if(options.form!="" && options.form!=null){
				queryString=$("#"+options.form).serialize();
				options.queryString=queryString;
			}
			
			$grid.find(".pageDiv666").attr("pageNo","");
		}
		
		//刷新后仍然为当前页号
		var pageNo=$grid.find(".pageDiv666").attr("pageNo");
		if(pageNo==null || pageNo=="" || pageNo=="undefined"){pageNo=options.pageNo;}
		//每页显示条数
		var pageSize=$grid.find(".pageDiv666").attr("pageSize");
		if(pageSize==null || pageSize=="" || pageSize=="undefined"){pageSize=options.pageSize;}
		
		var queryUrl=options.url+"?pageNo="+pageNo+"&pageSize="+pageSize;
		$.ajax({
			type:"POST",
			url:queryUrl,
			data: options.queryString,
			dataType:"html",
			success: function(data){
				$grid.html(data).hide();
				var maxPageNo=$grid.find(".pageDiv666").attr("maxPageNo");
				//判断如当前页号大于查询出来的总页号  则再次查询最后一页
				if(parseInt(pageNo)>parseInt(maxPageNo) && parseInt(maxPageNo)!=0){
					pageNo=maxPageNo;
					options.pageNo=pageNo;
					options.firstQuery=false;
					$grid.find(".pageDiv666").attr("pageNo","");//清空当前页号，按新页号重新查询
					$grid.zPage(options);
				}else{
					$grid.show();
					var maxPageSize=$grid.find(".pageDiv666").attr("maxPageSize");
					$("#"+options.totalCounter).text(maxPageSize);//总记录数
					$("#"+options.totalPager).text(maxPageNo);//总页数
					$("#"+options.currentPager).text(pageNo);//当前页号
					options.pageCallback();
				}
			},
			error:function(XMLHttpRequest,textStatus){
				alert("ERROR:"+textStatus);
			}
		});
		
		//首页
		$grid.off("click",".firstPage").on("click",".firstPage",function(){
			var pno=$grid.find(".pageDiv666").attr("pageNo");
			if(pno=="1"){return false;}
			$grid.find(".pageDiv666").attr("pageNo","");
			options.pageNo="1";
			options.firstQuery=false;
			$grid.zPage(options);
		});
		//尾页
		$grid.off("click",".lastPage").on("click",".lastPage",function(){
			var pno=$grid.find(".pageDiv666").attr("pageNo");
			var mpno=$grid.find(".pageDiv666").attr("maxPageNo");
			if(pno==mpno){return false;}
			$grid.find(".pageDiv666").attr("pageNo","");
			options.pageNo=mpno;
			options.firstQuery=false;
			$grid.zPage(options);
		});
		//上一页
		$grid.off("click",".prevPage").on("click",".prevPage",function(){
			var pno=$grid.find(".pageDiv666").attr("pageNo");
			if(pno=="1"){return false;}
			$grid.find(".pageDiv666").attr("pageNo","");
			options.pageNo=parseInt(pno)-1;
			options.firstQuery=false;
			$grid.zPage(options);
		});
		//下一页
		$grid.off("click",".nextPage").on("click",".nextPage",function(){
			var pno=$grid.find(".pageDiv666").attr("pageNo");
			var mpno=$grid.find(".pageDiv666").attr("maxPageNo");
			if(pno==mpno){return false;}
			$grid.find(".pageDiv666").attr("pageNo","");
			options.pageNo=parseInt(pno)+1;
			options.firstQuery=false;
			$grid.zPage(options);
		});
		//某一页
		$grid.off("click",".pno").on("click",".pno",function(){
			var goPno=$(this).attr("pno");//点击的页号
			var pno=$grid.find(".pageDiv666").attr("pageNo");//当前页号
			if(pno==goPno){return false;}
			$grid.find(".pageDiv666").attr("pageNo","");
			options.pageNo=goPno;
			options.firstQuery=false;
			$grid.zPage(options);
		});
		
		var re = /^[0-9]+?[0-9]*$/;
		
		//自己输入每页显示记录数
		$grid.off("keyup",".inputPageSize").on("keyup",".inputPageSize",function(event){
			if(event.keyCode=="13"){
				var inputPageSize=$(this).val();
				if(!re.test(inputPageSize)){return false;}
				if(pageSize==inputPageSize){return false;}
				$grid.find(".pageDiv666").attr("pageNo","");
				$grid.find(".pageDiv666").attr("pageSize","");
				options.pageSize=inputPageSize;
				options.pageNo=1;
				options.firstQuery=false;
				$grid.zPage(options);
			}
		});
		
		//自己输入页号
		$grid.off("keyup",".inputPageNo").on("keyup",".inputPageNo",function(event){
			if(event.keyCode=="13"){
				var inputPageNo=$(this).val();
				if(!re.test(inputPageNo)){return false;}
				var pno=$grid.find(".pageDiv666").attr("pageNo");
				if(pno==inputPageNo){return false;}
				$grid.find(".pageDiv666").attr("pageNo","");
				options.pageNo=inputPageNo;
				options.firstQuery=false;
				$grid.zPage(options);
			}
		});
	};
	
})(jQuery);

