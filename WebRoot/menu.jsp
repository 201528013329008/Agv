<%@ page language="java" pageEncoding="UTF-8"%>

<%
String pathM = request.getContextPath();
String basePathM = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathM+"/";
%>

<link rel="stylesheet" href="<%=basePathM%>css/new-style.css"> 
<script type="text/javascript">
   	var logoutFun = function(){
   		$.get("<%=basePathM%>user/logout",function(data){
   			data = $.parseJSON(data);
   			window.top.location.href = "<%=basePathM%>login";
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

<div style="background:url(<%=basePathM%>images/new/top_bg.png) repeat-x;height:70px;width:100%">
			
	<div class="topleft">
		<a href="index.html" target="_parent"> <img src="<%=basePathM%>images/new/U2_12.png"
			title="系统首页" />
		</a>
	</div>
			
	<ul class="inav nav">
	<c:if test="${sessionInfo.user.userType=='super'}">
		
		<li class="dropdown">
			<a id="layout" data-toggle="dropdown" class="dropdown-toggle" data-target="#" role="button" aria-haspopup="true" aria-expanded="true">
				<img src="<%=basePathM%>images/new/U2_07.png" title="布局管理" />
				<h2>布局 <span class="caret"></span></h2>
				
			</a>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			  <li><a href="<%=basePathM%>layout/super/drawMap">地图绘制</a></li>
			  <li><a href="<%=basePathM%>layout/super/road">路径管理</a></li>
			  <li><a href="<%=basePathM%>layout/super/stop">停车点管理</a></li>
			  <li><a href="<%=basePathM%>layout/super/workstation">工作点管理</a></li>
			  <li><a href="<%=basePathM%>layout/super/RFID">RFID点管理</a></li>
			  <li role="separator" class="divider"></li>
			  <li><a href="<%=basePathM%>layout/super/charge">充电设备</a></li>
			  <li><a href="<%=basePathM%>layout/super/isolation">隔离设备</a></li>
			</ul>
			
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>agv'" id="car">
				<img src="<%=basePathM%>images/new/U2_05.png" title="车辆管理" />
				<h2>车辆管理</h2>
			</a>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>task'" id="task">
				<img src="<%=basePathM%>images/new/U2_03.png" title="任务管理" />
				<h2>任务管理</h2>
			</a>
		</li>
		<li class="dropdown" >
			<a id="map" data-toggle="dropdown"> 
				<img src="<%=basePathM%>images/new/U2_06.png" title="运行控制" />
				<h2>运行<span class="caret"></span></h2>
			</a>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			  <li><a href="<%=basePathM%>monitor/super/system">系统监控</a></li>
			  <li><a href="<%=basePathM%>monitor/super/operation">系统控制</a></li>
			</ul>
		</li>
		<li class="dropdown" >
			<a id="manual" data-toggle="dropdown"> 
				<img src="<%=basePathM%>images/new/U2_11.png" title="工位控制" />
				<h2>工控<span class="caret"></span></h2>
			</a>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			  <li><a href="<%=basePathM%>manual/index?id=1">工位A</a></li>
			  <li><a href="<%=basePathM%>manual/index?id=2">工位B</a></li>
			</ul>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>user/super/managePage'">
				<img src="<%=basePathM%>images/new/U2_10.png" title="用户管理" />
				<h2>用户管理</h2>
			</a>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>user/changepwdPage'">
				<img src="<%=basePathM%>images/new/U2_09.png" title="修改密码" />
				<h2>修改密码</h2>
			</a>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>manual/virtual'">
				<img src="<%=basePathM%>images/new/U2_02.png" title="虚拟设备" />
				<h2>虚拟设备</h2>
			</a>
		</li>
	</c:if>
	<c:if test="${sessionInfo.user.userType=='person'}">
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>task/user'" id="task">
				<img src="<%=basePathM%>images/new/U2_03.png" title="任务管理" />
				<h2>任务管理 </h2>
			</a>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>monitor'" id="map"> <img
				src="<%=basePathM%>images/new/U2_07.png" title="地图监控" />
				<h2>地图监控</h2>
			</a>
		</li>
		<li>
			<a onClick="window.top.location.href = '<%=basePathM%>user/changepwdPage'">
				<img src="<%=basePathM%>images/new/U2_09.png" title="修改密码" />
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
	
		
	
</div>

