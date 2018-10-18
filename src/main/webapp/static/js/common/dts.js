/**
 * @author sjz
 * datables
 */

(function($) {
	$.fn.dts = function(opts) {
		var dt_i18 = {
		    //"sProcessing" : "<img src='../images/loading29.gif' alt='' />",
		    "sLengthMenu" : "",
		    "zeroRecords": "没有找到记录",
		    "sInfo" : "共 _TOTAL_ 条",
		    "infoEmpty": "无记录",
		    "infoFiltered": "(从 _MAX_ 条记录过滤)",
		    "oPaginate" : {
		        "sFirst" : "<span class='first_page'>&nbsp;</span>",
		        "sPrevious" : "<span class='prev_page'>上一页</span>",
		        "sNext" : "<span class='next_page'>下一页</span>",
		        "sLast" : "<span class='last_page'>&nbsp;</span>"
		    }
		};
		
		var defaults = {
			url:"",//查询地址
			columns:[],//数据列表
			order:[],//默认排序
			pageCallback:function(){}//回调函数
		};
		var options = $.extend(defaults, opts);
		
		var datalist=$(this).DataTable( {
	        dom: '<"top">rt<"bottom clearfix"ip>',
	        language: dt_i18,
	        ordering: true,
	        //"scrollY":  "400px",
	        //"scrollX":  true,
	        paging:   true,
	        /**
	        "fixedColumns":   {
	            leftColumns: 3,
	            rightColumns: 1
	        },
	        **/
	        pagingType:"full_numbers",
	        searching: false,
	        serverSide: true,
	        order:options.order,
	        ajax: {
	            url : options.url,
	            type: "POST",
	            dataType:"json",
	            dataSrc: "data",
	            data: {
	            }
	        },
	        columns: options.columns, 
        	drawCallback:function(){
	        	//加序号
	            var startIndex = datalist.page.info().start;//新版获取本页开始的行索引
	            datalist.column(0).nodes().each(function(cell, i) {
	                cell.innerHTML = startIndex + i + 1;
	            });
	            
	            options.pageCallback();
			}
	    });
		
		return datalist;
	
	};
	
})(jQuery);