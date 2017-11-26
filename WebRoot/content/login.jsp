<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>后台登陆</title>
<link rel="stylesheet" href="<%=basePath%>css/login.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/artDialog-master/css/ui-dialog.css">
<link rel="shortcut icon" href="<%=basePath%>images/favicon.ico" />
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，本系统暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->
<script src="<%=basePath%>js/artDialog-master/dist/dialog-min.js"></script>
<script type="text/javascript">
	$(function() {
		var alertMsg = function(title, msg) {
			var d = dialog({
				title : title,
				content : msg
			});
			d.show();
		}
		$("#subbtn").click(function() {
			var loginName = $("#loginName");
			var userPassword = $("#userPassword");
            if(loginName.val()===""){
                alertMsg("提示","登录帐号不能为空！");
                loginName.focus();
                return false;
            }
            if(userPassword.val()===""){
                alertMsg("提示","登录密码不能为空！");
                userPassword.focus();
                return false;
            }
            $.post("<%=basePath%>user/login",$("#loginform").serialize(),function(data){
                console.info(data);
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("登录成功！");
                    setTimeout(function(){
                        window.top.location.href="<%=basePath%>index";
                    },1000);
                }else{
                    alert(data.msg);
                    return false;
                }
            });
		});
	});
</script>
</head>
<body>
    
    <div id="login_center">
        <div id="login_area">
            <div id="login_form">
                <form method="POST" id="loginform">
                    <div id="login_tip">用户登录&nbsp;&nbsp;UserLogin</div>
                    <div>
                        <input type="text" class="username" name="loginName" id="loginName" value="admin"/>
                    </div>
                    <div>
                        <input type="password" class="pwd" name="userPassword" id="userPassword" value="123456"/>
                    </div>
                    <div>
                       <input type="button" class="btn" name="sbubtn" id="subbtn" value="登&nbsp;&nbsp;录">
                        &nbsp;&nbsp;
                        </div>
                    <div id="btn_area">
                        
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div id="login_bottom">版权所有：XXXXXXXXXX</div>
</body>
</html>