<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container-fluid">
  <div class="row">
  	<div class="col-md-12 text-right" style="background-color: #1883d7">
  	<a href="main" style="color: #000">首页</a>&nbsp;
  	【${sessionInfo.username}】&nbsp;<a href="logout" style="color: #000">注销</a>
  	</div>
  </div>
  <div class="row">
  	<div class="col-md-2">
    	<jsp:include page="/WEB-INF/views/menu.jsp"></jsp:include>
    </div>
    
  	<div class="col-md-10">