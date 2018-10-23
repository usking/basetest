<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/WEB-INF/inc.jsp" %>
<title>登录</title>
<script type="text/javascript">
$(function(){
	changeCaptcha();
})

function changeCaptcha(){
	document.getElementById("captchaImg").src="captcha.jpg?time="+new Date().getTime();
}

function login(){
	$("#form1").submit();
}

function keyLogin(e){
	if(e.keyCode=="13"){
		login();
	}
}
</script>
</head>
<body>
<div class="container">

	<div class="row" style="height:80px;">
		<div class="col-md-12"></div>
	</div>
	<!-- 开始 -->
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6" style="background-color:#F5F9FB">
		
			<!-- form开始 -->
			<div class="row" style="height:50px">
				<div class="col-md-4"></div>
				<div class="col-md-4" style="text-align:center;font-size:20px;"><u>登录</u></div>
				<div class="col-md-4"></div>
			</div>
			
			
			
			<div class="row">
			  <div class="col-md-4"></div>
			  <div class="col-md-4" style="">
			  
				<form class="form-horizontal" id="form1" method="post" action="login" onkeyup="keyLogin(event)">
					<div class="form-group">
						<label>用户名</label><span style="margin-left:35px;color:red">${userErrorMessage}</span>
						<div class="input-group">
							<div class="input-group-addon"><i class="fa fa-user fa-fw"></i></div>
							<input type="text" class="form-control" id="username" name="username" placeholder="输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label>密码</label>
						<div class="input-group">
							<div class="input-group-addon"><i class="fa fa-key fa-fw"></i></div>
							<input type="password" class="form-control" id="password" name="password" placeholder="输入密码">
						</div>
					</div>
					<div class="form-group">
						<label>验证码</label><span style="margin-left:10px;color:red">${captchaErrorMessage}</span>
						<div class="input-group">
							<div class="input-group-addon"><i class="fa fa-keyboard-o fa-fw"></i></div>
							<input type="text" class="form-control" id="captcha" name="captcha" placeholder="验证码" style="width:80px">
							<img id="captchaImg" border="0" src="captcha.jpg" style="width:66px;height:33px;margin-left:5px;display:" onclick="changeCaptcha()" />
						</div>
					</div>
					<div class="form-group checkbox">
						<label>
						  <input type="checkbox" name="rememberMe" value="true"> 自动登录
						</label>
					</div>
					<div class="form-group checkbox">
						<label>
						   <a type="button" class="btn btn-primary" style="width:150px;" onclick="login()">
						   <i class="fa fa-hand-o-right"></i>&nbsp;登录</a>
						</label>
					</div>
				</form>
			  </div>
			  <div class="col-md-4"></div>
			</div>
			
			
			<div class="row" style="height:50px">
			</div>
			<!-- form结束 -->

		</div>

		<div class="col-md-3"></div>
	</div>
	<!-- 结束 -->
	

</div>
</body>
</html>