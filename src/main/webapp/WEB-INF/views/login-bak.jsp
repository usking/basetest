<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<title>登录</title>
<style>
body {
	font-family:"Microsoft Yahei";
	font-size:12px;
	margin:0;
	background: #F5F9FB 
}
ul {
	padding:0;
	margin:0;
}
ul li {
	list-style-type:none;
}
a {
	text-decoration:none;
}
a:hover {
	text-decoration:none;color:#f00;
}
.cl{ clear: both;}
input[type="text"]:focus, input[type="password"]:focus {
	outline:none;
}
input::-ms-clear {
	display:none;
}
.login {
	margin:0 auto;
	width:370px;
	border:2px solid #eee;
	border-bottom:none;
	position:relative;
}
.header {
	height:50px;
	border-bottom:1px solid #e2e2e2;
	position:relative;
	font-family:"Microsoft Yahei";
}
.header .switch {
	height:45px;
	position:absolute;
	left:60px;
	bottom:0;
	font-size:16px;
}
.header .switch #switch_qlogin {
	margin-right:85px;
}
.header .switch .switch_btn {
	color:#999;
	display:inline-block;
	height:45px;
	line-height:45px;
	outline:none;
	*hide-focus:expression(this.hideFocus=true);
}
.header .switch .switch_btn_focus {
	color:#333;
	display:inline-block;
	height:45px;
	line-height:45px;
	outline:none;
	*hide-focus:expression(this.hideFocus=true);
}
.header .switch .switch_btn:hover {
	color:#333;
	text-decoration:none;
}
.header .switch .switch_btn_focus:hover {
	text-decoration:none;
}
#switch_bottom {
	position:absolute;
	bottom:-1px;_bottom:-2px;
	border-bottom:2px solid #848484;
}

.web_login {
	width:370px;
	position:relative;
}
#web_login{_left:60px;*left:0;}
.web_login .login_form {
	width:272px;
	margin:0 auto;
}
.web_login .reg_form {
	width:300px;
	margin:0 auto;
}
.web_login .input-tips {
	float:left;
	margin-top:25px;
	width:66px;
	height:42px;
	font-size:16px;
	line-height:42px;
	font-family:"Hiragino Sans GB", "Microsoft Yahei";
}
.web_login .input-tips2 {
	float:left;
	text-align:right;
	padding-right:10px;
	width:75px;
	height:30px;
	font-size:16px;
	margin-top:10px;
	clear:both;
	line-height:30px;
	font-family:"Hiragino Sans GB", "Microsoft Yahei";
}
.web_login .inputOuter {
	width:200px;
	height:42px;
	margin-top:10px;
	float:left;
	
}
.web_login .inputOuter2 {
	width:200px;
	margin-top:6px;margin-top:5px\9;
	float:left;
	
}
.web_login .inputstyle {
	width:200px;
	height:38px;
	padding-left:5px;
	line-height:30px;line-height:38px;
	border:1px solid #D7D7D7;
	background:#fff;
	color:#333;border-radius:2px;
	font-family:Verdana, Tahoma, Arial;
	font-size:16px;
	ime-mode:disabled;
}
.web_login input.inputstyle2:focus,.web_login input.inputstyle:focus{border:1px solid #198BD4;box-shadow:0 0 2px #198BD4;}
.web_login .inputstyle2 {
	width:200px;
	height:34px;
	padding-left:5px;
	line-height:34px;
	border:1px solid #D7D7D7;
	background:#fff;
	color:#333;border-radius:2px;
	font-family:Verdana, Tahoma, Arial;
	font-size:16px;
	ime-mode:disabled;
}
.web_login .uinArea {
	height:55px;
	position:relative;
	z-index:10;
}
.web_login .pwdArea {
	height:67px;
	margin-top:13px;
	position:relative;
	z-index:3;
}
.web_qr_login {
	position:relative;
	
	overflow:hidden;
}

.cue {
	height:40px;
	line-height:40px;
	font-size:14px;
	border:1px #CCCCCC solid;
	margin-top:10px;margin-bottom:5px;
	text-align:center;
	font-family:"Hiragino Sans GB", "Microsoft Yahei";
}
.login {
	background-color:#ffffff;
}

h1{margin:80px auto 50px auto;text-align:center;color:#fff;margin-left:-25px;font-size:35px;font-weight: bold;text-shadow: 0px 1px 1px #555;}
h1 sup{
    font-size: 18px;
    font-style: normal;
    position: absolute;
    margin-left: 10px;}
.login {border:0;padding:5px 0;
background: #fff;
margin: 0 auto;
-webkit-box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, .3);
box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, .3);}

.web_login{padding-bottom:20px;}

.reg_form li {
height: 55px;
}
.cue {
margin-top: 15px;
margin-bottom: 10px;border:1px solid #eee;border-radius:3px;
}
.web_login input.inputstyle2:focus, .web_login input.inputstyle:focus {
border: 1px solid #5796f;
box-shadow: 0 0 0;
}
.web_login .reg_form {
width: 300px;
margin: 0 auto;
}
.web_login .inputstyle2 {border-radius:2px;width:210px;}
.web_login .input-tips2 {
padding-right: 5px;
width: 80px;_width: 75px;_font-size:12px;}
.button_blue
{
	display:inline-block;
	float:left;
	height:41px;border-radius:4px;
	background:#2795dc;border:none;cursor:pointer;
	border-bottom:3px solid #0078b3;*border-bottom:none;
	color:#fff;
	font-size:16px;padding:0 10px;*width:140px;
	text-align:center;outline:none;font-family: "Microsoft Yahei",Arial, Helvetica, sans-serif;
	margin-left:0px;
}
input.button_blue:hover
{
	background:#0081c1;
	border-bottom:3px solid #006698;*border-bottom:none;
	color:#fff;
	text-decoration:none;
}
a.zcxy {text-decoration: underline;line-height:58px;margin-left:15px;color: #959ca8;}
.web_login .login_form {margin-top:30px;}
.web_login .uinArea {
height: 67px;}
.header .switch{left:70px;}



.error-tip{
	/*text-align:right;*/
	width:200px;
	height:15px;
	display:inline-block;
	color:red;
}
.captchastyle {
	width:100px;
	height:38px;
	padding-left:5px;
	line-height:30px;line-height:38px;
	border:1px solid #D7D7D7;
	background:#fff;
	color:#333;border-radius:2px;
	font-family:Verdana, Tahoma, Arial;
	font-size:16px;
	ime-mode:disabled;
}

</style>
<script type="text/javascript">
function changeCaptcha(){
	document.getElementById("captchaImg").src="captcha.jpg?time="+new Date().getTime();
}
</script>
</head>
<body>
<div class="login" style="margin-top:50px;">
    
    <div class="header">
        <div class="switch" id="switch">
		<a class="switch_btn_focus" href="javascript:void(0);">快速登录</a>
			<div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>    
  
    
    <div class="web_qr_login" id="web_qr_login" style="height: 336px;">    

		<!--登录-->
		<div class="web_login" id="web_login">
		   
		   
		   <div class="login-box" style="width:360px;margin-top:-20px;">

		
			<div class="login_form">
			  <form id="login_form" class="loginForm" method="post" action="login">

				<div class="uinArea" >
					<label class="input-tips">帐号：</label>
					<div class="inputOuter">
						<span class="error-tip">${userErrorMessage}</span>
						<input type="text" id="u" name="username" class="inputstyle"/>
					</div>
				</div>

				<div class="uinArea">
				   <label class="input-tips">密码：</label> 
				   <div class="inputOuter">
						<span class="error-tip"></span>
						<input type="password" name="password" class="inputstyle"/>
				   </div>
				</div>

				<div class="uinArea">
				   <label class="input-tips">验证码：</label> 
				   <div class="inputOuter">
						<span class="error-tip">${captchaErrorMessage}</span>
						<input type="text" class="captchastyle" name="captcha" />
						<img border="0" id="captchaImg" src="captcha.jpg" style="width:85px;height:42px;float:right;margin-left:5px;" onclick="changeCaptcha()" />
				   </div>
				</div>

				<div class="uinArea" style="height:30px;">
				   <label class="input-tips"></label> 
				   <div class="inputOuter">
						
						<input type="checkbox" name="rememberMe" value="true" />&nbsp;<span>自动登录</span>
				   </div>
				</div>
			   
				<div style="padding-left:50px;margin-top:15px;">
					<input type="submit" value="登 录" style="width:150px;" class="button_blue"/>
				</div>
			  </form>
		   </div>
	   
			</div>
		   
		</div>
		<!--登录end-->
  </div>

</div>

</body>
</html>