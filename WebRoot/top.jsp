<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- bootstrap --%>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<link href="<%=basePath%>js/bootstrap/bootstrap.css" rel="stylesheet"/>
<script src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>菜单</title>
<link rel="stylesheet" href="<%=basePath%>css/new-style.css"> 
<script type="text/javascript">
   	var logoutFun = function(){
   		$.get("<%=basePath%>user/logout",function(data){
   			data = $.parseJSON(data);
   			window.top.location.href = "<%=basePath%>login";
   		});
   		//顶部导航切换
   	}
    $(function(){	
		//顶部导航切换
		$(".inav li a").click(function(){
			$(".inav li a.selected").removeClass("selected")
			$(this).addClass("selected");
		});
   	}); 
   	
	
</script>
</head>
<body style="background:url(<%=basePath%>images/new/top_bg.png) repeat-x;">
			
	<div class="topleft">
		<a href="index.html" target="_parent"> <img src="<%=basePath%>images/new/U2_12.png"
			title="系统首页" />
		</a>
	</div>
			
	<ul class="inav nav">
	<c:if test="${sessionInfo.user.userType=='super'}">
		
		<li class="dropdown">
			<a id="layout" data-toggle="dropdown" class="dropdown-toggle" data-target="#" role="button" aria-haspopup="true" aria-expanded="true">
				<img src="<%=basePath%>images/new/U2_03.png" title="布局管理" />
				<h2>布局 <span class="caret"></span></h2>
				
			</a>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			  <li><a href="#">地图管理</a></li>
			  <li><a href="#">路劲管理</a></li>
			  <li><a href="#">停车点管理</a></li>
			  <li><a href="#">工作点管理</a></li>
			  <li><a href="#">RFID点管理</a></li>
			  <li role="separator" class="divider"></li>
			  <li><a href="#">充电设备</a></li>
			  <li><a href="#">隔离设备</a></li>
			</ul>
			
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>agv'" id="car">
				<img src="<%=basePath%>images/new/U2_05.png" title="车辆管理" />
				<h2>车辆管理</h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>monitor'" id="map"> <img
				src="<%=basePath%>images/new/U2_07.png" title="系统监控" />
				<h2>系统监控</h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>manual/index'" id="manual">
				<img src="<%=basePath%>images/new/U2_11.png" title="人工控制" />
				<h2>人工控制</h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>user/changepwdPage'">
				<img src="<%=basePath%>images/new/U2_09.png" title="修改密码" />
				<h2>修改密码</h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>user/super/manager'">
				<img src="<%=basePath%>images/new/U2_10.png" title="用户管理" />
				<h2>用户管理</h2>
			</a>
		</li>
	</c:if>
	<c:if test="${sessionInfo.user.userType=='person'}">
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>task'" id="task">
				<img src="<%=basePath%>images/new/U2_03.png" title="任务管理" />
				<h2>任务管理 </h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>monitor'" id="map"> <img
				src="<%=basePath%>images/new/U2_07.png" title="地图监控" />
				<h2>地图监控</h2>
			</a>
		</li>
		<li>
			<a onClick="parent.mainFrame.location = '<%=basePath%>user/changepwdPage'">
				<img src="<%=basePath%>images/new/U2_09.png" title="修改密码" />
				<h2>修改密码</h2>
			</a>
		</li>
	</c:if>
	</ul>
	
	<div class="topright" >
		<div class="logout">
			<span>${sessionInfo.user.userName }</span> <br>
			<image class="userexitimg"></image>
			<a href="#" target="_parent" onClick="logoutFun()"> 退出 </a>
		</div>
	</div>
	
		
	
</body>

</html>