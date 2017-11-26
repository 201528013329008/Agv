<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<%@ include file="/header.jsp"%>
<%-- myTools --%>
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script type="text/javascript">

	var gridObj;
	$(function(){
	    gridObj = $.fn.bsgrid.init('logTable', {
	        url: '<%=basePath%>log/dataGrid',
			pageSizeSelect : true,
			pageSize : 10,
			autoLoad : true,
			pagingLittleToolbar : true,//精简工具条
			stripeRows : true,//隔行变色
			displayBlankRows : false,//不显示空白行
			displayPagingToolbarOnlyMultiPages : false
		});
	});

	function init(){
		initMap(function(){
			addRoad();
			addIntersection();
			addWorkStation();
			addStop();
			addShelf();
			addCar();
			stage.update();
		});
	}
	
	window.onfocus = function(evt){
		$.get(getRootPath()+"agv/dataGrid",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			var agv = datas[i];
	      			var car = carMap.get(agv.id);
	      			if (car!=null){
	      				car.x = SX(agv.x);
	      				car.y = SY(agv.y);
	      			}
	      		}
	      		stage.update();
	      	}else{
	      		alert("获取车辆数据失败！");
	      	}
			
	    });
	}
	
	//定期更新小车位置
	function onMapTick(){
		$.get(getRootPath()+"agv/dataGrid",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			var agv = datas[i];
	      			var car = carMap.get(agv.id);
	      			if (car!=null){
	      				createjs.Tween.get(car)
	      				.to({x:SX(agv.x), y:SY(agv.y)}, 1000)
	      			}
	      		}
	      		stage.update();
	      	}else{
	      		alert("获取车辆数据失败！");
	      	}
			
	    });
	}
	
	var step = 0;
	
	function addFun(){
		//判断是否已选择起点坐标和终点坐标
		var startPos = $("#startPos").val();
		var endPos = $("#endPos").val();
		var isAuto = $("#isAuto").val();
		var carId = $("#carId").val();
		if(isAuto == 1 && carId == ""){
			layer.msg('您还没有选择车辆！请点击图片中的汽车进行选择。', {icon: 0,time: 2000});
			return false;
		}
		if(startPos == ""){
			layer.msg('请选择起点坐标。', {icon: 0,time: 2000});
			return false;
		}
		if(endPos == ""){
			layer.msg('请选择终点坐标。', {icon: 0,time: 2000});
			return false;
		}
		var url = '<%=basePath%>task/addPage?startPos='+startPos+'&endPos='+endPos+'&agvId='+carId;
		layer.open({
		    type: 2,
		    title: false,
		    closeBtn: 1, //不显示关闭按钮
		    shade: [0],
		    area: ['540px', '380px'],
		    shift: 2,
		    content: [url, 'no'] //iframe的url，no代表不显示滚动条
		});
	}
	
	function userOper(record, rowIndex, colIndex, options){
		var html = "";
		if (record.userId>0 && record.user!=null){
			return record.user.userName;
		}else{
			return "";
		}
	}
	
	function logLevelOper(record, rowIndex, colIndex, options){
		var html = "";
		switch (record.logLevel){
		case "DEBUG":
			html = "调试";
			break;
		case "INFO":
			html = '<a style="color:green">消息</a>';
			break;
		case "WARN":
			html = '<a style="color:yellow">警告</a>';
			break;
		case "ERROR":
			html = '<a style="color:red">普通错误</a>';
			break;
		case "FATAL":
			html = '<a style="color:blown">严重错误</a>';
			break;
		}
		return html;
	}
</script>
<title></title>
</head>
<body onload="init();">
<%@ include file="/menu.jsp"%> 
	<div class="mycontainer" >
		<div class="pt15 pb15"  style="width: 100%">
				<div id="detailTop" class="mhdiv" style="display:none">
					<div style="width:70%">
						<table class="form_table">
						<tbody>
							<tr>
								<td style="width: 150px;"><i class="i_U2_28"></i>可用车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg"	value="3" /></td>
								<td style="width: 155px;"><i class="i_U2_30"></i>在线车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg"value="12" /></td>
								<td style="width: 155px;"><i class="i_U2_33"></i>充电车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg" value="6" /></td>
							</tr>
							<tr>
								<td><i class="i_U2_38"></i>当前任务数量</td>
								<td><input type="text" class="inputimg" value="5" /></td>
								<td><i class="i_U2_39"></i> 信号故障车辆数量</td>
								<td><input type="text" class="inputimg" value="1" /></td>
								<td><i class="i_U2_40"></i> 运行故障车辆数量</td>
								<td><input type="text" class="inputimg" value="0" /></td>
							</tr>
						</tbody>
						</table>
					</div>
					<div style="width:20%">
						<button type="button" class="btn btn-primary" onClick="window.open('<%=basePathM%>agv/agv_rt')">车辆监控</button>
						<button type="button" class="btn btn-primary" onClick="window.open('<%=basePathM%>task/task_rt')">任务监控</button>
					</div>
				</div>
				<div class="mhdiv">
					<div id="detailLeft" style="padding-top:20px">
						<table class="form_table">
						<tbody>
							<tr>
								<td style="width: 150px;"><i class="i_U2_28"></i>可用车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg"	value="3" /></td>
							</tr>
							<tr>
								<td style="width: 155px;"><i class="i_U2_30"></i>在线车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg"value="12" /></td></tr>
							<tr>
								<td style="width: 155px;"><i class="i_U2_33"></i>充电车辆数量</td>
								<td class="td-val"><input type="text" class="inputimg" value="6" /></td>
							</tr>
							<tr>
								<td><i class="i_U2_38"></i>当前任务数量</td>
								<td><input type="text" class="inputimg" value="5" /></td>
							</tr>
							<tr>
								<td><i class="i_U2_39"></i> 信号故障车辆数量</td>
								<td><input type="text" class="inputimg" value="1" /></td>
							</tr>
							<tr>
								<td><i class="i_U2_40"></i> 运行故障车辆数量</td>
								<td><input type="text" class="inputimg" value="0" /></td>
							</tr>
							<tr>
								<td colspan="2"><button type="button" class="btn btn-primary" style="width:100%" onClick="window.open('<%=basePathM%>agv/agv_rt')">车辆监控</button></td>
							</tr>
							<tr>
								<td colspan="2"><button type="button" class="btn btn-primary" style="width:100%" onClick="window.open('<%=basePathM%>task/task_rt')">任务监控</button></td>
							</tr>
						</tbody>
						</table>
					</div>
					<div style="text-align: center;">
						<canvas id="mapCanvas" width="800" height="400"></canvas>
					</div>
				</div>
		</div>
		<div id="table" class="mt10">
            <table id="logTable">
                <tr>
                    <th w_index="id" width="5%;">ID</th>
                    <th w_render="userOper" width="5%;">用户</th>
                    <th w_render="logLevelOper" width="5%;">日志级别</th>
                    <th w_index="msg" width="75%;">日志内容</th>
                    <th w_index="createDate" width="10%;">触发时间</th>
                </tr>
            </table>
        </div>
	</div>
</body>
</html>
