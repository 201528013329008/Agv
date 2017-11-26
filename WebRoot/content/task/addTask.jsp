<%-- <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<%@ include file="/header.jsp"%>
myTools
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script type="text/javascript">
	$(function(){
				
		//加载小车类型数据
		$.get("<%=basePath%>agv/getAgvTypeList",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			var selector = $("#agvType_selector");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			selector.append('<option value='+datas[i].id+'>'+datas[i].name+'</option>');
	      		}
	      		
	      	}else{
	      		alert("获取小车类型数据失败！");
	      	}
			
	    });
		
		
		$("#submit").click(function(){
			var name = $("#name").val();
			var startPos = $("#startPos").val();
			var endPos = $("#endPos").val();
			if (startPos == endPos){
				layer.msg('起点和终点不能一样', {icon: 0,time: 2000});
				return false;
			}
			var hour = $("#hour").val();
			    hour = hour ? parseInt(hour) : 0;
			var minute = $("#minute").val();
				minute = minute ? parseInt(minute) : 0;
			var scheduleTime = hour*60+minute>0 ? new Date().getTime()+(hour*60+minute)*60*1000:new Date().getTime()+3600000;
			
			$("#scheduleTimeInt").val(scheduleTime);
			$("#typeId").val($("#taskType_selector").val());
			$("#agvTypeId").val($("#agvType_selector").val());
			
			if(name == ""){
				layer.msg('请输入任务名称', {icon: 0,time: 2000});
				$("#name").focus();
				return false;
			}
			if(startPos == ""){
				layer.msg('请选择起点坐标', {icon: 0,time: 2000});
				return false;
			}
			if(endPos == ""){
				layer.msg('请选择终点坐标', {icon: 0,time: 2000});
				return false;
			}
            
            $.post("<%=basePath%>task/add", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                        window.parent.location.href="<%=basePath%>task";
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
        });
	});
	
	function init(){
		initMap(function(){
			addWorkStation();
			stage.update();
		});
	}
	
	var step = 0;
	function selectCar(car) {
	}
	
	function selectStation(station){
		layer.confirm('请设置起点或终点？', {
		    btn: ['设置为起点','设置为终点'] //按钮
		}, function(){
			$("#startPos").val(station.id);//station id
			$("#startName").val(station.name);
		    layer.msg('设置起点成功！', {icon: 1});
		}, function(){
			$("#endPos").val(station.id);//station id
			$("#endName").val(station.name);
		    layer.msg('设置终点成功！', {icon: 1});
		});
	}
	
</script>
<title>添加任务</title>
</head>
<body onload="init();">
	<%@ include file="/menu.jsp"%>
	<div class="mycontainer">
		<table width="100%">
			<td width="auto">
				<div style="text-align: center;">
					<canvas id="mapCanvas" width="1000px" height="1000px">
				    </canvas>
				</div>
			</td>
			右边---------------------------------------------------------------------------------
			<td width="400px">
				<form method="POST" id="addTaskForm">
					<input type="hidden" name="startPos" id="startPos">
					<input type="hidden" name="endPos" id="endPos">
					<input type="hidden" name="scheduleTimeInt" id="scheduleTimeInt">
					<input type="hidden" name="typeId" id="typeId">
					<input type="hidden" name="agvTypeId" id="agvTypeId">
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">添加任务</h3>
						</div>
						<div class="panel-body">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon5">任务名称</span> 
								<input
									id="name" name="name" type="text" class="form-control" placeholder="请输入任务名称"
									aria-describedby="basic-addon5">
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">任务类型</span> 
								<select
									id="taskType_selector" class="form-control">
									<option value="1">运输</option>
									<option value="0">移动</option>
								</select>
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon2">起点名称</span> 
								<input
									id="startName" type="text" class="form-control" placeholder="请在地图上选择起点"
									aria-describedby="basic-addon2" readonly="">
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon3">终点名称</span> 
								<input
									id="endName" type="text" class="form-control" placeholder="请在地图上选择终点"
									aria-describedby="basic-addon3" readonly="">
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon4">小车类型</span> 
								<select
									id="agvType_selector" class="form-control">
									
								</select>
							</div>
							<br>
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon6">完成时间</span> 
								<input
									id="hour" type="number" class="form-control" placeholder="小时"
									aria-describedby="basic-addon6">
								<span class="input-group-addon" id="basic-addon6">小时</span> 
								<input
									id="minute" type="number" class="form-control" placeholder="分钟"
									aria-describedby="basic-addon6">
								<span class="input-group-addon" id="basic-addon6">分钟</span>
							</div>
							<br>
							<div class="btn-group btn-group-justified" role="group" aria-label="ok">
								<div class="btn-group" role="group">
									<input type="button" class="btn btn-success" id="submit" value="提交">
								</div>
								<div class="btn-group" role="group">
									<input type="button" class="btn btn-default" id="submit" value="取消" onClick="history.back();">
								</div>
							</div>
						</div>
					</div>
				</form>
			</td>
			右边完---------------------------------------------------------------------------------
		</table>

	</div>
</body>
</html>
 --%>