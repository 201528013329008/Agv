var stage;
var map = {};
var carMap = new Map();
var stationMap = new Map();
var intersectionMap = new Map();
var roadMap = new Map();

var layer_index = -1;

var P_intersection = new createjs.Bitmap(getRootPath()+"images/new/U2_54.png");
P_intersection.regX = 5;
P_intersection.regY = 5;

var P_workstation = new createjs.Bitmap(getRootPath()+"images/new/U2_50.png");
P_workstation.regX = 10;
P_workstation.regY = 10;

var P_stop = new createjs.Bitmap(getRootPath()+"images/new/U2_51.png");
P_stop.regX = 10;
P_stop.regY = 10;

var P_car = new createjs.Bitmap(getRootPath()+"images/new/U2_65.png");
P_car.regX = 24;
P_car.regY = 20;

var P_shelf = new createjs.Bitmap(getRootPath()+"images/new/U2_48.png");
P_shelf.regX = 10;
P_shelf.regY = 10;

var PList = new Array(P_workstation,P_stop,P_car,P_intersection,P_shelf);
for (i in PList){
	PList[i].cursor = "pointer";
}

function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName + "/");
}

function initStage(){
	createjs.MotionGuidePlugin.install(createjs.Tween);
	stage = new createjs.Stage("mapCanvas");
	stage.name = "mapStage";
	stage.enableMouseOver(10);
	stage.addEventListener("mouseover", handleMouse);
	stage.addEventListener("mouseout", handleMouse);
	stage.addEventListener("click", handleMouse);
	createjs.Ticker.setFPS(50);
	createjs.Ticker.addEventListener("tick", tick);
	
	map = {
		"scale" : 1,
		"regX"  : 500,
		"regY"  : 300,
		"width" : 1000,
		"height" : 600,
		"roads":new Array(),
		"workstations":new Array(),
		"stops":new Array(),
		"intersections":new Array(),
		"shelfs":new Array()
	};
}

function resetMap(){
	map = {
		"scale" : 1,
		"regX"  : 500,
		"regY"  : 300,
		"width" : 1000,
		"height" : 600,
		"roads":new Array(),
		"workstations":new Array(),
		"stops":new Array(),
		"intersections":new Array()
	};
	resetStage();
}

function resetStage(){
	if (stage!=null){
		stage.removeAllChildren();
	}	
}

function initMap(onSuccess,onFail){
	
	initStage();
	
	$.get(getRootPath()+"monitor/mapDatas", function(mapDatas,status){
		if ((mapDatas == "fail") || (!status)){
			stage.addChild(new createjs.Text("加载地图资源失败", "bold 86px Arial", "#777777")).set({x:100,y:500});
			if (!(onFail == 'undefined')){
				onFail();
			}
			
		}else{
			mapDatas = eval("("+mapDatas+")");
			mapDatas = mapDatas.replace(/[\u0000-\u0019]+/g,""); 
			map = JSON.parse(mapDatas);
			
	
			$("#mapCanvas").attr("height",map.height+"px");
			$("#mapCanvas").attr("width",map.width+"px");
			
			if (map.scale==undefined){
				map.scale = 1;
			}
			if (map.regX==undefined){
				map.regX = map.width/2;
			}
			if (map.regY==undefined){
				map.regY = map.height/2;
			}
			
			
			if (!(onSuccess == 'undefined')){
				onSuccess();
			}									
		}
    });

}

//添加路径
function addRoad(){

	updateIntersectionMap();
	var roads = map.roads;
	
	for (i in roads){
		var road = roads[i];
		var s = new createjs.Shape();
		var p1 = intersectionMap.get(road.point_ids[0]);
		var p2 = intersectionMap.get(road.point_ids[1]);
		s.graphics.setStrokeStyle(5, "round", "round").beginStroke(road.color).moveTo(SX(p1.x), SY(p1.y)).lineTo(SX(p2.x), SY(p2.y));
		
		for (j=2;j<road.point_ids.length;j++){
			p2 = intersectionMap.get(road.point_ids[j]);
			s.graphics.lineTo(SX(p2.x), SY(p2.y));
		}
		s.id = road.id;
		s.name = road.name;
		s.type = "road";
		s.cursor = "pointer";
		stage.addChild(s);
	}
}

//更新交叉点映射关系
function updateRoadMap(){
	var roads = map.roads;
	roadMap.clear();
	for (i in roads){
		var road = roads[i];
		roadMap.set(road.id,road);
	}
}

//更新道路映射关系
function updateIntersectionMap(){
	var stations = map.intersections;
	roadMap.clear();
	for (i in stations){
		var station = stations[i];
		intersectionMap.set(station.id,station);
	}
}

//添加交叉点
function addIntersection(){
	var stations = map.intersections;
	for (i in stations){
		var station = stations[i];
		var P_station;
		P_station = P_intersection.clone();
		P_station.type = "intersection";
		P_station.id = station.id;
		P_station.name = station.name;
		stationMap.set(station.id,P_station);
		stage.addChild(P_station).set({x: SX(station.x), y: SY(station.y)});
	}
}

//添加停车点
function addStop(){
	var stations = map.stops;
	for (i in stations){
		var station = stations[i];
		var P_station;
		P_station = P_stop.clone();
		P_station.type = "stop";
		P_station.id = station.id;
		P_station.name = station.name;
		stationMap.set(station.id,P_station);
		stage.addChild(P_station).set({x: SX(station.x), y: SY(station.y)});
	}
}

//添加工作点位
function addWorkStation(){
	var stations = map.workstations;
	for (i in stations){
		var station = stations[i];
		var P_station;
		P_station = P_workstation.clone();
		P_station.type = "workstation";
		P_station.id = station.id;
		P_station.name = station.name;
		stationMap.set(station.id, P_station);
		stage.addChild(P_station).set({x: SX(station.x), y: SY(station.y)});
	}
}

//添加货架
function addShelf(){
	var shelfs = map.shelfs;
	for (i in shelfs){
		var station = shelfs[i];
		var P_station;
		P_station = P_shelf.clone();
		P_station.type = "shelf";
		P_station.id = station.id;
		P_station.name = station.name;
		P_station.sn = station.sn;
		stationMap.set(station.id,P_station);
		stage.addChild(P_station).set({x: SX(station.x), y: SY(station.y)});
	}
}

function addCar(){
	$.get(getRootPath()+"agv/dataGrid",function(jqGrid,status){
		var jqGrid = eval("("+jqGrid+")");
		
      	if (jqGrid.success){
      		var datas = jqGrid.data;
      		for (i in datas){
      			var agv = datas[i];
      			var car = P_car.clone();
      			car.name = agv.name;
      			car.id = agv.id;
      			car.type = "car";
      			stage.addChild(car).set({x: SX(agv.x),y: SY(agv.y)});
      			carMap.set(car.id, car);
      		}
      	}else{
      		alert("获取车辆数据失败！");
      	}
		
    });
	
}

var mapCallbackCounterUpper = 50;
var mapCallbackCounter = 0;

function tick(evt){
	//console.log("msg:","tick");
	stage.update();
	if (mapCallbackCounter>=mapCallbackCounterUpper){
		mapCallbackCounter = 0;
		onMapTick();//回调函数
	}else{
		mapCallbackCounter++;
	}
}



function handleMouse(evt){
 	//console.log("msg:", "type=" + evt.type + " target=" + evt.target.name + " eventPhase=" + evt.eventPhase + " currentTarget=" + evt.currentTarget.name);
	if (evt.target.name != null){
		//悬停提示
		if (evt.type == "mouseover"){
			layer_index = layer.msg(evt.target.id+"-"+evt.target.name,{
				offset: [mouseWinPoint.y+'px', mouseWinPoint.x+'px']
			}); 
		}else if(evt.type == "mouseout" && layer_index != -1){
			layer.close(layer_index);
			layer_index = -1;
		}else if(evt.type == "click" && evt.target.type!=null){
			onMapClick(evt.target);//回调函数
		}
		
	}
	
}

function deleteById(arr,id){
	index = -1;
	for (i in arr){
		var element = arr[i];
		if (element.id!=null && element.id==id){
			index = i;
			break;
		}
	}
	if (index>=0){
		arr.splice(index,1);
	}
}

function getById(arr,id){
	index = -1;
	for (i in arr){
		var element = arr[i];
		if (element.id!=null && element.id==id){
			index = i;
			break;
		}
	}
	if (index>=0){
		return arr[index];
	}else{
		return null;
	}
}

//返回平移缩放后的坐标
function SX(x){
	return map.width-((x-map.regX)*map.scale+map.width/2);
}
//返回平移缩放后的坐标
function SY(y){
	return (y-map.regY)*map.scale+map.height/2;
}

//重载回调函数
function onMapClick(target){
	
}

function onMapTick(){
	
}