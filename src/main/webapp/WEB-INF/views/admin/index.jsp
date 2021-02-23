<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/WEB-INF/tag.jsp" %>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<title>通用后台管理</title>
	  
	<link rel="stylesheet" href="${ctx}/static/js/plugins/layui-v2.5.7/layui/css/layui.css"  media="all">
</head>
<body>
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">后台布局</div>
    <!-- 头部区域 -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item"><a href="">菜单一</a></li>
      <li class="layui-nav-item"><a href="">菜单二</a></li>
	  <li class="layui-nav-item"><a href="${ctx}/main">回主页</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="">邮件管理</a></dd>
          <dd><a href="">消息管理</a></dd>
          <dd><a href="">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          用户
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="">注销</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域 -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">所有商品</a>
          <dl class="layui-nav-child">
            <dd><a href="${ctx}/test/admin/console" target="iframe1">列表一</a></dd>
            <dd><a href="javascript:;">列表二</a></dd>
            <dd><a href="http://www.baidu.com/" target="iframe1">打开百度</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item">
          <a href="javascript:;">解决方案</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;">列表一</a></dd>
            <dd>
				<a href="javascript:;">列表二</a>
				<dl class="layui-nav-child">
					<dd><a href="http://www.csdn.net/" target="iframe1">列表2的下级</a></dd>
				</dl>
			</dd>
            
          </dl>
        </li>
        <li class="layui-nav-item"><a href="javascript:;" target="iframe1">菜市场</a></li>
        <li class="layui-nav-item"><a href="http://www.baidu.com/" target="_blank">发布商品</a></li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;">
      <iframe name="iframe1" src="${ctx}/test/admin/console" frameborder="0" style="position:absolute;width:100%;height:100%;left:0;top:0;right:0;bottom:0"></iframe>
      
      
    </div>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    底部固定区域
  </div>
</div>
<script src="${ctx}/static/js/plugins/layui-v2.5.7/layui/layui.js" charset="utf-8"></script>
<script>
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;
  
});
</script>
</body>
</html>