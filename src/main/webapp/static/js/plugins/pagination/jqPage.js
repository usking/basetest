/**
 * @author sjz
 */

(function($) {
	$.jqPage = function(opts) {
		var defaults = {
			url:"",//查询地址
			grid:"",//数据存储的容器id
			form:"",//查询form的id
			pagination:"",//分页标签的id
			pageSize:10,//每页显示记录数
			currentPage:1,//默认当前第几页
			numDisplayEntries:5,//连续分页主体部分显示的分页条目数
			numEdgeEntries:2,//起始点与结束点的数目
			totalCounter:"",//记录总数容器id
			totalPager:"",//总页号容器id
			currentPager:"",//当前页号容器id
			pageCallback:function(){}//回调函数
		};
		var options = $.extend(defaults, opts);
		
		var pageUrl=options.url;
		var gridId=options.grid;
		var paginationId=options.pagination;
		var formId=options.form;
		var pageSize=options.pageSize;
		var currentPage=options.currentPage;
		var numDisplayEntries=options.numDisplayEntries;
		var numEdgeEntries=options.numEdgeEntries;
		var totalCounter=options.totalCounter;
		var totalPager=options.totalPager;
		var currentPager=options.currentPager;
		
		var $gridId = $("#"+gridId);
		var queryString="";
		if(formId!="" && formId!=null)
		{
			queryString=$("#"+formId).serialize();
			$gridId.attr("queryString",queryString);
		}
		$.post(pageUrl+"?pageNo="+currentPage+"&pageSize="+pageSize,queryString,function(obj){
			$gridId.html(obj).hide().fadeIn(300);
			//设置总记录数
			var maxPageSize=$gridId.find(".pageDiv666").attr("maxPageSize");
			if(totalCounter!=null && $.trim(totalCounter)!=""){
				$("#"+totalCounter).html(maxPageSize);
			}
			if(maxPageSize=="0"){maxPageSize="1";}
			//设置总页数
			var maxPageNo=$gridId.find(".pageDiv666").attr("maxPageNo");
			if(totalPager!=null && $.trim(totalPager)!=""){
				$("#"+totalPager).html(maxPageNo);
			}
			//设置当前页号
			if(currentPager!=null && $.trim(currentPager)!=""){
				$("#"+currentPager).html(currentPage);
			}
			//设置默认页号
			if(parseInt(currentPage)>parseInt(maxPageNo) && parseInt(maxPageNo)!=0){
				currentPage=maxPageNo;
			}
			$("#"+paginationId).pagination(parseInt(maxPageSize),{
				items_per_page:parseInt(pageSize),//每页显示条数
				num_display_entries:parseInt(numDisplayEntries),//连续分页主体部分显示的分页条目数
				num_edge_entries:parseInt(numEdgeEntries),//起始点与结束点的数目
				current_page:parseInt(currentPage)-1,//当前选中的页面 默认是0，表示第1页
				prev_text:"上一页",
				next_text:"下一页",
				prev_show_always:true,
				next_show_always:true,
				ellipse_text:"...",
				link_to:"javascript:;",
				callback: function (page_index, jq) {
					var num=parseInt(page_index)+1;
			    	var currentPageNo=$gridId.find(".pageDiv666").attr("pageNo");
			    	//设置当前页号
					if(currentPager!=null && $.trim(currentPager)!=""){
						$("#"+currentPager).html(num);
					}
			    	if(parseInt(num)!=parseInt(currentPageNo)){
				    	$.post(pageUrl+"?pageNo="+num+"&pageSize="+pageSize,queryString,function(obj){
				    		$gridId.html(obj).hide().fadeIn(300);
				    		options.pageCallback();//执行回调
				    	});
			    	}
			    }
			});
			//执行回调
			options.pageCallback();
		});	
	};
	
})(jQuery);

