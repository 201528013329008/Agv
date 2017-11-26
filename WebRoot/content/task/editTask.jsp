<%@ page language="java" import="agv.pageModel.Task" pageEncoding="utf-8"%>
<%
Integer typeId = (Integer)request.getAttribute("type");
if (typeId==null){
	typeId = 0;
}
Task task = (Task)request.getAttribute("task");
if (task==null){
	task = new Task();
	task.setTypeId(typeId);
}

%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<%@ include file="/header.jsp"%>
<%-- myTools --%>
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script type="text/javascript">
	var taskId = <%=task.getId() %>;
	var operation = '';
	if (taskId!=null){
		operation = "edit";
	}else{
		operation = "add";
	}
	
	$(function(){
		//初始化地图
		initMap(function(){
			addRoad();
			addIntersection();
			addStop();
			addWorkStation();
			addShelf();
			stage.update();
			
			if (operation=="edit"){
				if ($("#startPos").val()!=null){
					$("#startName").val(stationMap.get(parseInt($("#startPos").val())).name);
				}
				if ($("#endPos").val()!=null){
					$("#endName").val(stationMap.get(parseInt($("#endPos").val())).name);
				}
			}
		});
		
		//加载小车类型数据
		$.get("<%=basePath%>agv/getAgvTypeList",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			var selector = $("#agvType_selector");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			selector.append('<option id=agvOption'+datas[i].id+' value='+datas[i].id+'>'+datas[i].name+'</option>');
	      		}
	      		if (operation=="edit"){
	      			$("#agvOption"+$("#agvTypeId").val()).attr("selected","true");
				}
	      	}else{
	      		alert("获取小车类型数据失败！");
	      	}
			
	    });
		
		//修改和添加，差异化初始化
		if (operation=="edit"){
			$("#typeOption"+$("#typeId").val()).attr("selected","true");
			$("#panelTitle").text("修改任务");
			$("input.form-control").attr("readonly","readonly");
			$("#agvType_selector").attr("disabled","true");
			$("#taskType_selector").attr("disabled","true");
			var timeRemain = parseInt($("#scheduleTimeInt").val())-new Date().getTime();
			var minute = parseInt(timeRemain/60000);
			var hour = parseInt(minute/60);
			minute = minute % 60;
			$("#hour").val(hour);
			$("#minute").val(minute);
		}else if(operation=="add"){
			$("#typeOption"+$("#typeId").val()).attr("selected","true");
			$("input.form-control").val(""); //所有置空
			$("#panelTitle").text("添加任务");
		}
		//改变任务类型
		changeTaskType();
		
		//添加任务按钮的操作
		$("#submit").click(function(){
			switch($("#taskType_selector").val()){
			case '0':
				addMoveTask();
				break;
			case '1':
				addTransTask();
				break;
			case '2':
				addShelfTask();
				break;
			}
        });
		
	});
	
	//添加移动任务
	function addMoveTask(){
		var name = $("#name").val();
		var endPos = $("#endPos").val();
		var agvId = $("#agvId").val();
		
		if (endPos = ""){
			layer.msg('请选择终点', {icon: 0,time: 2000});
			return false;
		}
		var hour = $("#hour").val();
		    hour = hour ? parseInt(hour) : 0;
		var minute = $("#minute").val();
			minute = minute ? parseInt(minute) : 0;
		var scheduleTime = hour*60+minute>0 ? new Date().getTime()+(hour*60+minute)*60*1000:new Date().getTime()+3600000;
		
		
		$("#scheduleTimeInt").val(scheduleTime);
		$("#typeId").val($("#taskType_selector").val());
		
		if(agvId == ""){
			layer.msg('请输入车辆编号', {icon: 0,time: 2000});
			$("#agvId").focus();
			return false;
		}
		if(name == ""){
			layer.msg('请输入任务名称', {icon: 0,time: 2000});
			$("#name").focus();
			return false;
		}
		        
		if (operation=="add"){
            $.post("<%=basePath%>task/add", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                        history.back(-1);
                    	//window.parent.location.href="<%=basePath%>task/rt";
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}else if (operation=="edit"){
			$.post("<%=basePath%>task/edit", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("修改成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}
	}
	
	//添加运输任务	
	function addTransTask(){
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
        
		if (operation=="add"){
            $.post("<%=basePath%>task/add", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}else if (operation=="edit"){
			$.post("<%=basePath%>task/edit", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("修改成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}
	}
	
	//添加叉车任务
	function addShelfTask(){
		var name = $("#name").val();
		var shelf1Id = $("#shelf1Id").val();
		var shelf1Row = $("#shelf1Row").val();
		var shelf1Column = $("#shelf1Column").val();
		
		var shelf2Id = $("#shelf2Id").val();
		var shelf2Row = $("#shelf2Row").val();
		var shelf2Column = $("#shelf2Column").val();
		
		var hour = $("#hour").val();
	    	hour = hour ? parseInt(hour) : 0;
		var minute = $("#minute").val();
			minute = minute ? parseInt(minute) : 0;
		var scheduleTime = hour*60+minute>0 ? new Date().getTime()+(hour*60+minute)*60*1000:new Date().getTime()+3600000;
		
		$("#scheduleTimeInt").val(scheduleTime);
		$("#typeId").val($("#taskType_selector").val());
		
		if(name == ""){
			layer.msg('请输入任务名称', {icon: 0,time: 2000});
			$("#name").focus();
			return false;
		}
		if(shelf1Id == "" || shelf2Id == ""){
			layer.msg('请选择货架', {icon: 0,time: 2000});
			return false;
		}
		if(shelf1Row == "" || shelf2Row == ""){
			layer.msg('请选择储物格', {icon: 0,time: 2000});
			return false;
		}
		if(shelf1Column == "" || shelf2Column == ""){
			layer.msg('请选择储物格', {icon: 0,time: 2000});
			return false;
		}
		
		if (shelf1Id==shelf2Id && shelf1Row==shelf2Row && shelf1Column==shelf2Column){
			layer.msg('起始位置和终止位置不能一样', {icon: 0,time: 2000});
			return false;
		}
		
		if (operation=="add"){
            $.post("<%=basePath%>task/add", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}else{
			$.post("<%=basePath%>task/edit", $("#addTaskForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("修改成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}
	}
	
	//切换任务类型
	function changeTaskType(){
		$(".allTask").hide();
		$(".input_value").val("");
		switch($("#taskType_selector").val()){
		case '0':
			$(".moveTask").show();
			break;
		case '1':
			$(".transTask").show();
			break;
		case '2':
			$(".shelfTask").show();
			break;
		}
	}
	
	//点击地图上的元素
	function onMapClick(target){
		switch(target.type){
		case "workstation":
			selectStation(target);
			break;
		case "stop":
			selectStation(target);
		case "shelf":
			selectStation(target);
			break;
		}
	}
		
	var step = 0;
	
	//选中地图上的站点
	function selectStation(station){
		if (operation=="add"){
			switch($("#taskType_selector").val()){
			case '0':
				popSelectDes(station);
				break;
			case '1':
				if (station.type=="workstation"){
					popSelectStaDes(station);
				}
				break;
			case '2':
				if (station.type=="shelf"){
					popSelectShelf(station);
				}
				break;
			}
			
		}else if (operation=="edit"){
			switch($("#taskType_selector").val()){
			case '0':
				popSelectDes(station);
				break;
			case '1':
				popSelectDes(station);
				break;
			}
		}
	}
	
	//选择终点
	function popSelectDes(station){
		layer.confirm('确定设为终点？', {
		    btn: ['确定','取消'] //按钮
		}, function(){
			$("#endPos").val(station.id);//station id
			$("#endName").val(station.id+'-'+station.name);
		    layer.msg('设置终点成功！', {icon: 1});
		}, function(){
		});
	}
	
	//选择起点和终点
	function popSelectStaDes(station){
		layer.confirm('请设置起点或终点？', {
		    btn: ['设置为起点','设置为终点'] //按钮
		}, function(){
			$("#startPos").val(station.id);//station id
			$("#startName").val(station.id+'-'+station.name);
		    layer.msg('设置起点成功！', {icon: 1});
		}, function(){ 
			$("#endPos").val(station.id);//station id
			$("#endName").val(station.id+'-'+station.name);
		    layer.msg('设置终点成功！', {icon: 1});
		});
	}
	
	//选择货架
	function popSelectShelf(station){
		if (station.type == "shelf"){
			
			layer.confirm('请设置起点或终点？', {
			    btn: ['设置为起点','设置为终点'] //按钮
			}, function(){
				layer.closeAll();
				popSelectShelfContent(station,1);
			}, function(){ 
				layer.closeAll();
				popSelectShelfContent(station,2);
			});
		}
	}
	
	//弹出选择货架窗格窗口
	function popSelectShelfContent(station,whichShelf){
		$.get("<%=basePath%>shelf/get?sn="+station.sn, function(shelf, status){
			var shelf = eval("("+shelf+")");
							
	      	if (shelf.id != null){
	      		layer.open({
	      			  title: '请选择货架储物格',
					  type: 1,
					  skin: 'layui-layer-rim', //加上边框
					  area: ['420px', '240px'], //宽高
					  content: shelfContent(station,shelf,whichShelf)
				});
	      	}else{
	      		alert("获取货架信息失败");
	      	}
			
	    });
	}
	
	//货架储物格窗口内容
	function shelfContent(station, shelf, whichShelf){
		var row = shelf.rowNum;
		var column = shelf.columnNum;
		var  content = '<table class="mshelf">';
		for (i=1;i<=row;i++){
			content += '<tr>';
			for (j=1;j<=column;j++){
				content += '<td><div onClick="selectShelfBox('+i+','+j+','+whichShelf+')"></div></td>';
			}
			content += '</tr>';
		}
		content += '</table>';
		if (whichShelf==1){
			$("#startPos").val(station.id);//station id
			$("#shelf1SN").val(shelf.sn);
			$("#shelf1Id").val(shelf.id);
		}else{
			$("#endPos").val(station.id);//station id
			$("#shelf2SN").val(shelf.sn);
			$("#shelf2Id").val(shelf.id);
		}
		return content;
	}
	
	//选中某个储物格
	function selectShelfBox(row,column,whichShelf){
		if (whichShelf==1){
			$("#shelf1Row").val(row);
			$("#shelf1Column").val(column);
			layer.closeAll();
		}else{
			$("#shelf2Row").val(row);
			$("#shelf2Column").val(column);
			layer.closeAll();
		}
	}
	
</script>
<title>添加任务</title>
</head>
<body>
	<div class="mycontainer">
		<table>
		<tr>
			<td>
				<div style="text-align: center;">
					<canvas id="mapCanvas" width="1000px" height="1000px">
				    </canvas>
				</div>
			</td>
			<%-- 右边--------------------------------------------------------------------------------- --%>
			<td style="width:400px;min-width:400px">
				<form method="POST" id="addTaskForm">
					<input class="form-control input_value" type="hidden" name="id" id="id" value="<%=task.getId() %>">
					<input class="form-control input_value" type="hidden" name="startPos" id="startPos" value="<%=task.getStartPos() %>">
					<input class="form-control input_value" type="hidden" name="endPos" id="endPos" value="<%=task.getEndPos() %>">
					<input class="form-control input_value" type="hidden" name="scheduleTimeInt" id="scheduleTimeInt" value="<%=task.getScheduleTime()==null?null:task.getScheduleTime().getTime() %>">
					<input class="form-control input_value" type="hidden" name="typeId" id="typeId" value="<%=task.getTypeId() %>">
					<input class="form-control input_value" type="hidden" name="agvTypeId" id="agvTypeId" value="<%=task.getAgvTypeId() %>">
					<input class="form-control input_value" type="hidden" name="shelf1Id" id="shelf1Id" value="<%=task.getShelf1Id() %>">
					<input class="form-control input_value" type="hidden" name="shelf2Id" id="shelf2Id" value="<%=task.getShelf2Id() %>">
					
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 id="panelTitle" class="panel-title">任务类型</h3>
						</div>
						<div class="panel-body">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon5">任务名称</span> 
								<input
									id="name" name="name" type="text" class="form-control input_value" placeholder="请输入任务名称"
									aria-describedby="basic-addon5" value="<%=task.getName() %>">
							</div>
							
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">任务类型</span> 
								<select
									id="taskType_selector" class="form-control" onchange="changeTaskType()">
									<option id="typeOption0" value="0">移动</option>
									<option id="typeOption1" value="1">运输</option>
									<option id="typeOption2" value="2">货架</option>
								</select>
							</div>
							
							<div class="input-group allTask moveTask">
								<span class="input-group-addon" id="basic-addon4">车辆编号</span> 
								<input
									id="agvId" name="agvId" type="text" class="form-control input_value" placeholder="请输入车辆编号"
									aria-describedby="basic-addon2">
							</div>
							
							<div class="input-group allTask transTask">
								<span class="input-group-addon" id="basic-addon2">起点名称</span> 
								<input
									id="startName" type="text" class="form-control input_value" placeholder="请在地图上选择起点"
									aria-describedby="basic-addon2" readonly="">
							</div>
							
							<div class="input-group allTask transTask moveTask">
								<span class="input-group-addon" id="basic-addon3">终点名称</span> 
								<input
									id="endName" type="text" class="form-control input_value" placeholder="请在地图上选择终点"
									aria-describedby="basic-addon3" readonly="">
							</div>
							
							<div class="input-group allTask transTask">
								<span class="input-group-addon" id="basic-addon4">车辆类型</span> 
								<select
									id="agvType_selector" class="form-control">
								</select>
							</div>
							
							<div class="input-group allTask shelfTask">
								<span class="input-group-addon" id="basic-addon3">起始货架</span> 
								<input
									id="shelf1SN" type="text" class="form-control input_value" placeholder="请在地图上选择货架"
									aria-describedby="basic-addon3" readonly="">
							</div>
							
							<div class="input-group allTask shelfTask">
								<span class="input-group-addon" id="basic-addon6">货物位置</span> 
								<input
									id="shelf1Row" name="shelf1Row" type="number" class="form-control input_value" placeholder="行号"
									aria-describedby="basic-addon6" readonly="">
								<span class="input-group-addon" id="basic-addon6">行</span> 
								<input
									id="shelf1Column" name="shelf1Column" type="number" class="form-control input_value" placeholder="列号"
									aria-describedby="basic-addon6" readonly="">
								<span class="input-group-addon" id="basic-addon6">列</span>
							</div>
							
							<div class="input-group allTask shelfTask">
								<span class="input-group-addon" id="basic-addon3">目标货架</span> 
								<input
									id="shelf2SN" type="text" class="form-control input_value" placeholder="请在地图上选择货架"
									aria-describedby="basic-addon3" readonly="">
							</div>
							
							<div class="input-group allTask shelfTask">
								<span class="input-group-addon" id="basic-addon6">货物位置</span> 
								<input
									id="shelf2Row" name="shelf2Row" type="number" class="form-control input_value" placeholder="行号"
									aria-describedby="basic-addon6" readonly="">
								<span class="input-group-addon" id="basic-addon6">行</span> 
								<input
									id="shelf2Column" name="shelf2Column" type="number" class="form-control input_value" placeholder="列号"
									aria-describedby="basic-addon6" readonly="">
								<span class="input-group-addon" id="basic-addon6">列</span>
							</div>
							
							<div class="input-group allTask transTask moveTask shelfTask">
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
							
							
							
							<div class="btn-group btn-group-justified" role="group" aria-label="ok">
								<div class="btn-group" role="group">
									<input type="button" class="btn btn-success" id="submit" value="提交">
								</div>
								<div class="btn-group" role="group">
									<input type="button" class="btn btn-default" id="cancel" value="取消" onClick="history.back();">
								</div>
							</div>
						</div>
					</div>
				</form>
			</td>
			<%-- 右边完--------------------------------------------------------------------------------- --%>
		</tr>
		</table>

	</div>
</body>
</html>
