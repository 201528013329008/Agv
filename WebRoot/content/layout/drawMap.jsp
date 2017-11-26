<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />

<%@ include file="/header.jsp"%> 
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script src="<%=basePath%>js/me/map/validateMap.js"></script>
<script src="<%=basePath%>js/FileSaver.min.js"></script>
<script src="<%=basePath%>js/Blob.js"></script>
<script type="text/javascript">
var id = 0;
$(function(){
	$("#mapWidth").val('900');
	$("#mapHeight").val('600');
	
	initStage();
	
	$("#addRoadBtn").click(function(){
		var name = $("#roadName").val();
		var color = $("#roadColor_selector").val();
		var capacity = $("#roadCapacity").val();
		var type = $("#roadType_selector").val();
		var point_ids = eval("(["+$("#roadPointIds").val()+"])");
		
		if (invalid(name) || invalid(color) || invalid(capacity) || invalid(type) || invalid(point_ids)){
			alertMsg("非法数据");
		}else{
			road = {
				"id":++id,
				"name":name,
				"point_ids" : point_ids,
				"capacity":parseInt(capacity),
				"type":type,
				"color":color
			};
			map.roads.push(road);
			var re = validateMap(map);
			if (re.result){
				updateMap();
			}else{
				deleteById(map.roads,road.id);
				alertMsg(re.msg);
			}
		}
	});
	
	$("#addStationBtn").click(function(){
		var name = $("#stationName").val();
		var type = $("#stationType_selector").val();
		var x = $("#stationX").val();
		var y = $("#stationY").val();
		var road_id = $("#stationRoadId").val();
		var point_id = $("#stationPointId").val();
		var sn = $("#stationSn").val();
		
		if (type!="intersection"){
			if (invalid(name) || invalid(type) || (invalid(point_id) && (invalid(x) || invalid(y) || invalid(road_id))) || (type=="shelf") && invalid(sn)){
				alertMsg("非法数据");
			}else{
				if (invalid(point_id)){
					curId = ++id;
				}else{
					curId = point_id;
				}
				if (!invalid(point_id)){
					intersect = getById(map.intersections, point_id);
					if (intersect!=null){
						x = intersect.x;
						y = intersect.y;
					}else{
						alertMsg("交叉点Id非法");
					}
				}
				station = {
					"id":curId,
					"name":name,
					"wtype" : type,
					"x":x,
					"y":y,
					"road_id":road_id,
					"point_id":point_id,
					"sn":sn
				};
				map[type+"s"].push(station);
				updateMap();
			}
		}else{
			if (invalid(name) || invalid(type) || invalid(x) || invalid(y)){
				alertMsg("非法数据");
			}else{
				station = {
					"id":++id,
					"name":name,
					"wtype" : type,
					"x":x,
					"y":y,
				};
				map[type+"s"].push(station);
				updateMap();
			}
		}
	});
	
	$("#saveMapBtn").click(function(){
		var mapName = $("#mapName").val();
		if (invalid(mapName)){
			alertMsg("请输入地图名称");
		}else{
			var blob = new Blob([JSON.stringify(map)], {type: "text/plain;charset=utf-8"});
			saveAs(blob, mapName+".map");
		}
	});
	
	$("#modifyMapBtn").click(function(){
		var mapName = $("#mapName").val();
		var width = $("#mapWidth").val();
		var height = $("#mapHeight").val();
		var maxX = $('#mapMaxX').val();
		var maxY = $('#mapMaxY').val();
		var minX = $('#mapMinX').val();
		var minY = $('#mapMinY').val();
		var scale = $('#mapScale').val();
		if (invalid(mapName) || invalid(width) || invalid(height) || invalid(maxX) || invalid(maxY) || invalid(minX) || invalid(minY)){
			alertMsg("数据不合法");
		}else{
			$("#mapCanvas").attr("height",height+"px");
			$("#mapCanvas").attr("width",width+"px")
			map.width = width;
			map.height = height;
			
			s1 = width/(maxX-minX);
			s2 = height/(maxY-minY);
			if (s1>s2){
				s1 = s2;
			}
			s1 = s1*0.8;
			map.scale = s1;
			
			map.regX = (parseFloat(minX) + parseFloat(maxX))/2;
			map.regY = (parseFloat(minY) + parseFloat(maxY))/2;
		}
	});
	
	$('#stationRoadIdContainer').hide();
	$('#stationPointIdContainer').hide();
	$('#stationSnContainer').hide();
	$('#stationType_selector').change(function(){
		var type = $("#stationType_selector").val();
		if (type=="intersection"){
			$('#stationRoadIdContainer').hide();
			$('#stationPointIdContainer').hide();
			$('#stationSnContainer').hide();
		}else{
			$('#stationRoadIdContainer').show();
			$('#stationPointIdContainer').show();
			$('#stationSnContainer').show();
		}
	});
	
});

function onMapClick(target){
	layer.confirm('是否确认删除', {
	    btn: ['是','否'], //按钮
	    offset: [mouseWinPoint.y+'px', mouseWinPoint.x+'px']
	}, function(){
		switch(target.type){
		case "road":
			deleteById(map.roads,target.id);
			break;
		case "intersection":
			deleteById(map.intersections,target.id);
			break;
		case "stop":
			deleteById(map.stops,target.id);
			break;
		case "workstation":
			deleteById(map.workstations,target.id);
			break;
		}
	    layer.msg('删除成功', {icon: 1, time: 500});
	    updateMap();
	});
}

//刷新地图显示
function updateMap(){
	resetStage();
	addRoad();
	addIntersection();
	addStop();
	addWorkStation();
	addShelf();
	stage.update();
}

function invalid(param){
	if (param==undefined || param==null || param==""){
		return true;
	}else{
		return false;
	}
}



</script>
<title>布局管理</title>
</head>
<body>
<%@ include file="/menu.jsp" %>
	<div class="mycontainer">
	    <table style="width:100%;height:auto">
	    <tr>
			<td style="width:auto;vertical-align:top">
				<canvas id="mapCanvas" width="1000px" height="600px">
			    </canvas>
			</td>
			<%-- 右边--------------------------------------------------------------------------------- --%>
			<td style="width:400px">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 id="panelTitle" class="panel-title">地图参数</h3>
					</div>
					<div class="panel-body">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon5">地图名称</span> 
							<input
								id="mapName" name="mapName" type="text" class="form-control" placeholder="请输入地图名称"
								aria-describedby="basic-addon5" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">地图宽度</span> 
							<input
								id="mapWidth" type="number" class="form-control" placeholder="请输入地图宽度(px)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">地图高度</span> 
							<input
								id="mapHeight" type="number" class="form-control" placeholder="请输入地图高度(px)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">最大X</span> 
							<input
								id="mapMaxX" type="number" class="form-control" placeholder="请输入最大X坐标(mm)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">最大Y</span> 
							<input
								id="mapMaxY" type="number" class="form-control" placeholder="请输入最大Y坐标(mm)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">最小X</span> 
							<input
								id="mapMinX" type="number" class="form-control" placeholder="请输入最小X坐标(mm)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">最小Y</span> 
							<input
								id="mapMinY" type="number" class="form-control" placeholder="请输入最小Y坐标(mm)"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="btn-group btn-group-justified" role="group" aria-label="ok">
							<div class="btn-group" role="group">
								<input type="button" class="btn btn-success" id="modifyMapBtn" value="提交">
							</div>
							<div class="btn-group" role="group">
								<input type="button" class="btn btn-default" id="saveMapBtn" value="保存">
							</div>
						</div>
					</div>
				</div>	
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 id="panelTitle" class="panel-title">添加点位</h3>
					</div>
					<div class="panel-body">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon5">点位名称</span> 
							<input
								id="stationName" name="stationName" type="text" class="form-control" placeholder="请输入点位名称"
								aria-describedby="basic-addon5" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">点位类型</span> 
							<select
								id="stationType_selector" class="form-control">
								<option id="intersection" value="intersection">交叉点</option>
								<option id="stop" value="stop">停车点</option>
								<option id="workstation" value="workstation">工作点位</option>
								<option id="shelf" value="shelf">货位</option>
							</select>
						</div>
						<br>
						<div id="stationPointIdContainer" class="input-group">
							<span class="input-group-addon" id="basic-addon4">所在点位</span> 
							<input
								id="stationPointId" type="text" class="form-control" placeholder="id"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">&nbsp;&nbsp;X &nbsp;&nbsp;坐标</span> 
							<input
								id="stationX" type="number" class="form-control" placeholder="请输入X坐标"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">&nbsp;&nbsp;Y &nbsp;&nbsp;坐标</span> 
							<input
								id="stationY" type="number" class="form-control" placeholder="请输入Y坐标"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div id="stationRoadIdContainer" class="input-group">
							<span class="input-group-addon" id="basic-addon4">所在路径</span> 
							<input
								id="stationRoadId" type="text" class="form-control" placeholder="id"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div id="stationSnContainer" class="input-group">
							<span class="input-group-addon" id="basic-addon4">设施编码</span> 
							<input
								id="stationSn" type="text" class="form-control" placeholder="SN"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="btn-group btn-group-justified" role="group" aria-label="ok">
							<div class="btn-group" role="group">
								<input type="button" class="btn btn-success" id="addStationBtn" value="添加点位">
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 id="panelTitle" class="panel-title">添加路径</h3>
					</div>
					<div class="panel-body">
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon5">路径名称</span> 
							<input
								id="roadName" name="roadName" type="text" class="form-control" placeholder="请输入路径名称"
								aria-describedby="basic-addon5" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon1">路径颜色</span> 
							<select
								id="roadColor_selector" class="form-control">
								<option id="black" value="black">黑色</option>
								<option id="orange" value="orange">橘色</option>
								<option id="green" value="green">绿色</option>
							</select>
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon2">路径容量</span> 
							<input
								id="roadCapacity" type="number" class="form-control" placeholder="请输入路径容量"
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon3">路径类型</span> 
							<select
								id="roadType_selector" class="form-control">
								<option id="single-way" value="single-way">单行道</option>
								<option id="double-way" value="double-way">双行道</option>
							</select>
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon4">连接点位</span> 
							<input
								id="roadPointIds" type="text" class="form-control" placeholder="id1,id2,..."
								aria-describedby="basic-addon2" >
						</div>
						<br>
						<div class="btn-group btn-group-justified" role="group" aria-label="ok">
							<div class="btn-group" role="group">
								<input type="button" class="btn btn-success" id="addRoadBtn" value="添加路径">
							</div>
						</div>
					</div>
				</div>
			</td>
			<%-- 右边完--------------------------------------------------------------------------------- --%>
		<tr>
		</table>
	    	
	</div>
</body>
</html>